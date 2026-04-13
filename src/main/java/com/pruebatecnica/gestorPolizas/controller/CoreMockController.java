package com.pruebatecnica.gestorPolizas.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/core-mock")
public class CoreMockController {

    @PostMapping("/evento")
    public ResponseEntity<Void> enviarEvento(@RequestBody Map<String, Object> body) {
        System.out.println("===============================================================");
        System.out.println("=                  Evento enviado al CORE:                    =");
        System.out.println("=           Evento: " + body.get("evento") + "                                  =");
        System.out.println("=           PolizaId: " + body.get("polizaId") + "                                       =");
        System.out.println("===============================================================");

        return ResponseEntity.ok().build();
    }
}
