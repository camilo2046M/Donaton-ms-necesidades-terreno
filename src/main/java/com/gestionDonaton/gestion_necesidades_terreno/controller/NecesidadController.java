package com.gestionDonaton.gestion_necesidades_terreno.controller;


import com.gestionDonaton.gestion_necesidades_terreno.dto.NecesidadRequestDTO;
import com.gestionDonaton.gestion_necesidades_terreno.dto.NecesidadResponseDTO;
import com.gestionDonaton.gestion_necesidades_terreno.service.NecesidadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/necesidades")
@RequiredArgsConstructor
public class NecesidadController {

    private final NecesidadService necesidadService;

    @PostMapping
    public ResponseEntity<NecesidadResponseDTO> reportarNecesidad(@RequestBody NecesidadRequestDTO request) {
        NecesidadResponseDTO response = necesidadService.reportarNecesidad(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<NecesidadResponseDTO>> listarNecesidades() {
        List<NecesidadResponseDTO> response = necesidadService.listarTodas();
        return ResponseEntity.ok(response);
    }
}
