package com.pruebatecnica.gestorPolizas.controller;

import com.pruebatecnica.gestorPolizas.dto.PolizaDTO;
import com.pruebatecnica.gestorPolizas.dto.RiesgoDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.pruebatecnica.gestorPolizas.service.PolizaService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/polizas")
public class PolizaController {

    private PolizaService polizaService;

    public PolizaController(PolizaService polizaService) {
        this.polizaService = polizaService;
    }

    @PostMapping
    public ResponseEntity<PolizaDTO> createPoliza(@RequestBody PolizaDTO polizaDto) {
        PolizaDTO created = polizaService.addPoliza(polizaDto);
        return ResponseEntity
                .created(URI.create("/polizas/" + created.getId()))
                .body(created);
    }

    @GetMapping
    public List<PolizaDTO> getPolizas(@RequestParam String tipo, @RequestParam String estado) {
        return polizaService.getPolizasByTypeAndState(tipo, estado);
    }

    @GetMapping("/{id}/riesgos")
    public List<RiesgoDTO> getRiesgosByPoliza(@PathVariable Long id) {
        return polizaService.getRiesgoById(id);
    }

    @PostMapping("/{id}/renovar")
    public ResponseEntity<PolizaDTO> renewPoliza(@PathVariable Long id) {
        PolizaDTO poliza = polizaService.renewPolizaById(id);
        return ResponseEntity.ok(poliza);
    }


    @PostMapping("/{id}/cancelar")
    public ResponseEntity<PolizaDTO> cancelPoliza(@PathVariable Long id) {
        PolizaDTO poliza = polizaService.cancelPolizaById(id);
        return ResponseEntity.ok(poliza);
    }

    @PostMapping("/{id}/riesgos")
    public ResponseEntity<PolizaDTO> agregarRiesgo(@PathVariable Long id, @RequestBody RiesgoDTO riesgoDto) {
        PolizaDTO poliza = polizaService.addRiesgoToPoliza(id, riesgoDto);
        return ResponseEntity.ok(poliza);
    }

}
