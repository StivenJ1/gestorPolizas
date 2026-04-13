package com.pruebatecnica.gestorPolizas.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Poliza {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String tipo;
    private String estado;
    private Integer mesesVigencia;
    private BigDecimal canonMensual;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private BigDecimal prima;

    @OneToMany(mappedBy = "poliza", cascade = CascadeType.ALL)
    private List<Riesgo> riesgos;

    public void addRiesgo(Riesgo riesgo) {
        if (riesgos == null) {
            riesgos = new ArrayList<>();
        }
        riesgos.add(riesgo);
        riesgo.setPoliza(this);
    }
}
