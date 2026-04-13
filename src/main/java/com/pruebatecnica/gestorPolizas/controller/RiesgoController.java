package com.pruebatecnica.gestorPolizas.controller;

import com.pruebatecnica.gestorPolizas.dto.RiesgoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.pruebatecnica.gestorPolizas.service.RiesgoService;

@RestController
@RequestMapping("/riesgos")
public class RiesgoController {

    @Autowired
    private final RiesgoService riesgoService;

    public RiesgoController(RiesgoService riesgoService) {
        this.riesgoService = riesgoService;
    }

    @PostMapping("/{id}/cancelar")
    public ResponseEntity<RiesgoDTO> cancelRiesgo(@PathVariable Long id) {
        RiesgoDTO riesgo = riesgoService.cancelRiesgoById(id);
        return ResponseEntity.ok(riesgo);
    }
}
