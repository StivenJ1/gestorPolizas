package com.pruebatecnica.gestorPolizas.service;

import com.pruebatecnica.gestorPolizas.Model.Poliza;
import com.pruebatecnica.gestorPolizas.Model.Riesgo;
import com.pruebatecnica.gestorPolizas.dto.PolizaDTO;
import com.pruebatecnica.gestorPolizas.dto.RiesgoDTO;
import com.pruebatecnica.gestorPolizas.implement.PolizaImpl;
import com.pruebatecnica.gestorPolizas.mapper.Mapper;
import com.pruebatecnica.gestorPolizas.port.CoreEventPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pruebatecnica.gestorPolizas.repository.PolizaRepository;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PolizaService implements PolizaImpl {

    @Autowired
    private PolizaRepository polizaRepository;
    private Mapper mapper;
    private final CoreEventPort coreClient;
    @Autowired
    private CoreEventPort coreEventPort;

    public PolizaService(
            PolizaRepository polizaRepository,
            Mapper mapper, CoreEventPort coreClient) {

        this.polizaRepository = polizaRepository;
        this.mapper = mapper;
        this.coreClient = coreClient;
    }

    @Override
    public PolizaDTO addPoliza(PolizaDTO polizaDto) {
        Poliza poliza = new Poliza();
        poliza.setTipo(polizaDto.getTipo());
        poliza.setEstado("ACTIVA");
        poliza.setMesesVigencia(polizaDto.getMesesVigencia());
        poliza.setCanonMensual(polizaDto.getCanonMensual());

        BigDecimal primaCalculada = poliza.getCanonMensual()
                .multiply(new BigDecimal(poliza.getMesesVigencia()));
        poliza.setPrima(primaCalculada);

        if (polizaDto.getRiesgos() != null && !polizaDto.getRiesgos().isEmpty()) {
            if ("INDIVIDUAL".equalsIgnoreCase(poliza.getTipo()) && polizaDto.getRiesgos().size() > 1) {
                throw new RuntimeException("Una póliza individual no puede tener más de un riesgo.");
            }

            for (RiesgoDTO rDto : polizaDto.getRiesgos()) {
                Riesgo nuevoRiesgo = new Riesgo();
                nuevoRiesgo.setDescripcion(rDto.getDescripcion());
                nuevoRiesgo.setEstado("ACTIVO");

                poliza.addRiesgo(nuevoRiesgo);
            }
        }

        Poliza saved = polizaRepository.save(poliza);
        notifyCore(saved.getId(), "CREACION");
        return mapper.toPolizaDto(saved);
    }

    @Override
    public List<PolizaDTO> getPolizasByTypeAndState(String tipo, String estado) {
        return polizaRepository.findByTipoAndEstado(tipo, estado).stream().map(mapper::toPolizaDto).toList();
    }

    @Override
    public List<RiesgoDTO> getRiesgoById(Long polizaId) {
        notifyCore(polizaId, "CONSULTA");
        return getPolizaOrThrow(polizaId)
                .getRiesgos()
                .stream()
                .map(mapper::toRiesgoDto)
                .toList();
    }

    @Override
    public PolizaDTO renewPolizaById(long polizaId) {

        Poliza poliza = polizaRepository.findById(polizaId)
                .orElseThrow(() -> new RuntimeException("Poliza no encontrada"));

        if ("CANCELADA".equalsIgnoreCase(poliza.getEstado())) {
            throw new RuntimeException("No se puede renovar una póliza cancelada");
        }

        BigDecimal ipcPorcentaje = new BigDecimal("5.56").divide(new BigDecimal("100"));
        BigDecimal factorAumento = BigDecimal.ONE.add(ipcPorcentaje);

        poliza.setCanonMensual(
                poliza.getCanonMensual().multiply(factorAumento).setScale(2, RoundingMode.HALF_UP)
        );

        poliza.setPrima(
                poliza.getPrima().multiply(factorAumento).setScale(2, RoundingMode.HALF_UP)
        );

        poliza.setEstado("RENOVADA");

        Poliza polizaGuardada = polizaRepository.save(poliza);
        notifyCore(polizaId, "ACTUALIZACION");
        return mapper.toPolizaDto(polizaGuardada);
    }

    @Override
    public PolizaDTO cancelPolizaById(long polizaId) {
        Poliza poliza = polizaRepository.findById(polizaId)
                .orElseThrow(() -> new RuntimeException("Poliza no encontrada"));
        if (poliza.getRiesgos() != null) {
            poliza.getRiesgos().forEach(riesgo -> riesgo.setEstado("CANCELADA"));
        }
        poliza.setEstado("CANCELADA");
        Poliza polizaGuardada =  polizaRepository.save(poliza);

        notifyCore(polizaId, "ACTUALIZACION");

        return mapper.toPolizaDto(polizaGuardada);
    }

    @Override
    public PolizaDTO addRiesgoToPoliza(long polizaId, RiesgoDTO riesgoDto) {

        Poliza poliza = polizaRepository.findById(polizaId)
                .orElseThrow(() -> new RuntimeException("Poliza no encontrada"));

        if ("INDIVIDUAL".equalsIgnoreCase(poliza.getTipo()) &&
                !poliza.getRiesgos().isEmpty()) {

            throw new RuntimeException("Una póliza individual solo puede tener un riesgo");
        }

        Riesgo riesgo = new Riesgo();
        riesgo.setDescripcion(riesgoDto.getDescripcion());
        riesgo.setEstado("ACTIVO");

        poliza.addRiesgo(riesgo);

        Poliza saved = polizaRepository.save(poliza);

        notifyCore(polizaId, "ACTUALIZACION");

        return mapper.toPolizaDto(saved);
    }

    private Poliza getPolizaOrThrow(Long id) {
        return polizaRepository.findById(id).orElseThrow(() -> new RuntimeException("Poliza no encontrada"));
    }

    private void notifyCore(long polizaId, String eventType){
        Map<String, Object> request = new HashMap<>();
        request.put("evento", eventType);
        request.put("polizaId", polizaId);

        coreEventPort.sendEvent(request);
    }
}
