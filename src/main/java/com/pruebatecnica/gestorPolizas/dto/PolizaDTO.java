package com.pruebatecnica.gestorPolizas.dto;

import com.pruebatecnica.gestorPolizas.Model.Riesgo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PolizaDTO {
    private Long id;
    private String tipo;
    private String estado;
    private Integer mesesVigencia;
    private BigDecimal canonMensual;
    private BigDecimal prima;
    private List<RiesgoDTO> riesgos;
}
