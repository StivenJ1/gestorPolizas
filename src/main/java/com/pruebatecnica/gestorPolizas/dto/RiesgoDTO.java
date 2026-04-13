package com.pruebatecnica.gestorPolizas.dto;

import com.pruebatecnica.gestorPolizas.Model.Poliza;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RiesgoDTO {

    private Long id;
    private String descripcion;
    private String estado;

}
