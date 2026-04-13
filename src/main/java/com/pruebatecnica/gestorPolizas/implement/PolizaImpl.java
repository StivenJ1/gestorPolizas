package com.pruebatecnica.gestorPolizas.implement;

import com.pruebatecnica.gestorPolizas.dto.PolizaDTO;
import com.pruebatecnica.gestorPolizas.dto.RiesgoDTO;

import java.util.List;

public interface PolizaImpl {

    PolizaDTO addPoliza(PolizaDTO poliza);
    List<PolizaDTO> getPolizasByTypeAndState(String tipo, String estado);
    List<RiesgoDTO> getRiesgoById(Long polizaId);
    PolizaDTO renewPolizaById(long polizaId);
    PolizaDTO cancelPolizaById(long polizaId);
    PolizaDTO addRiesgoToPoliza(long id, RiesgoDTO riesgoDto);
}
