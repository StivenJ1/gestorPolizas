package com.pruebatecnica.gestorPolizas.mapper;

import com.pruebatecnica.gestorPolizas.Model.Poliza;
import com.pruebatecnica.gestorPolizas.Model.Riesgo;
import com.pruebatecnica.gestorPolizas.dto.PolizaDTO;
import com.pruebatecnica.gestorPolizas.dto.RiesgoDTO;
import org.springframework.stereotype.Component;

@Component
public class Mapper {

    public PolizaDTO toPolizaDto(Poliza poliza) {
        PolizaDTO dto = new PolizaDTO();
        dto.setId(poliza.getId());
        dto.setTipo(poliza.getTipo());
        dto.setEstado(poliza.getEstado());
        dto.setCanonMensual(poliza.getCanonMensual());
        dto.setPrima(poliza.getPrima());
        dto.setMesesVigencia(poliza.getMesesVigencia());

        if (poliza.getRiesgos() != null) {
            dto.setRiesgos(
                    poliza.getRiesgos().stream()
                            .map(this::toRiesgoDto)
                            .toList()
            );
        }
        return dto;
    }

    public RiesgoDTO toRiesgoDto(Riesgo riesgo) {
        RiesgoDTO dto = new RiesgoDTO();
        dto.setId(riesgo.getId());
        dto.setDescripcion(riesgo.getDescripcion());
        dto.setEstado(riesgo.getEstado());
        return dto;
    }
}
