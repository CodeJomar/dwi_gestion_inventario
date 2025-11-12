package com.multisupply.sgi.ordenes.controllers;

import com.multisupply.sgi.ordenes.entities.dtos.OrdenDTO;
import com.multisupply.sgi.ordenes.services.OrdenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ordenes")
@RequiredArgsConstructor
public class OrdenApiController {

    private final OrdenService ordenService;

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerOrden(@PathVariable String id) {
        try {
            OrdenDTO orden = ordenService.obtenerOrdenPorId(id);
            return ResponseEntity.ok(orden);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
}