package com.pruebatecnica.gestorPolizas.service;

import com.pruebatecnica.gestorPolizas.Model.Riesgo;
import com.pruebatecnica.gestorPolizas.dto.RiesgoDTO;
import com.pruebatecnica.gestorPolizas.implement.RiesgoImpl;
import com.pruebatecnica.gestorPolizas.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pruebatecnica.gestorPolizas.repository.RiesgoRepository;

@Service
public class RiesgoService implements RiesgoImpl {

    @Autowired
    private RiesgoRepository riesgoRepository;
    private Mapper mapper;

    public RiesgoService(
            RiesgoRepository riesgoRepository,
            Mapper mapper) {

        this.riesgoRepository = riesgoRepository;
        this.mapper = mapper;
    }


    @Override
    public RiesgoDTO cancelRiesgoById(long riesgoId) {
        Riesgo riesgo = riesgoRepository.findById(riesgoId)
                .orElseThrow(() -> new RuntimeException("Riesgo no encontrado"));
        if (riesgo.getEstado().equalsIgnoreCase("CANCELADA")) {
            throw new RuntimeException("El riesgo ya está cancelado");
        }
        riesgo.setEstado("CANCELADA");
        Riesgo riesgoGuardado = riesgoRepository.save(riesgo);
        return mapper.toRiesgoDto(riesgoGuardado);
    }
}
