package com.gestionDonaton.gestion_necesidades_terreno.controller;

import com.gestionDonaton.gestion_necesidades_terreno.dto.NecesidadRequestDTO;
import com.gestionDonaton.gestion_necesidades_terreno.dto.NecesidadResponseDTO;
import com.gestionDonaton.gestion_necesidades_terreno.service.NecesidadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

        import java.util.List;

@RestController
@RequestMapping("/api/v1/necesidades")
@RequiredArgsConstructor
@Tag(name = "Gestión de Necesidades", description = "Endpoints para el reporte, registro y consulta de carencias o necesidades prioritarias")
public class NecesidadController {

    private final NecesidadService necesidadService;

    @PostMapping
    @Operation(
            summary = "Reportar una nueva necesidad",
            description = "Registra una carencia o requerimiento específico en el sistema a partir de los datos provistos en el DTO de entrada."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Necesidad reportada y registrada exitosamente"),
            @ApiResponse(responseCode = "400", description = "El cuerpo de la solicitud contiene datos inválidos o mal formateados")
    })
    public ResponseEntity<NecesidadResponseDTO> reportarNecesidad(@RequestBody NecesidadRequestDTO request) {
        NecesidadResponseDTO response = necesidadService.reportarNecesidad(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(
            summary = "Listar todas las necesidades",
            description = "Recupera un listado completo con todas las necesidades que han sido reportadas en el sistema."
    )
    @ApiResponse(responseCode = "200", description = "Listado de necesidades obtenido exitosamente")
    public ResponseEntity<List<NecesidadResponseDTO>> listarNecesidades() {
        List<NecesidadResponseDTO> response = necesidadService.listarTodas();
        return ResponseEntity.ok(response);
    }

    // === AGREGADO PARA EL BFF: Endpoint para atender la necesidad ===
    @PatchMapping("/{id}/atender")
    @Operation(
            summary = "Marcar una necesidad como atendida",
            description = "Modifica parcialmente el estado de una necesidad en terreno a 'ATENDIDA' usando su ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Necesidad marcada como atendida correctamente"),
            @ApiResponse(responseCode = "404", description = "No se encontró ninguna necesidad con el ID proporcionado")
    })
    public ResponseEntity<NecesidadResponseDTO> atenderNecesidad(
            @Parameter(description = "ID único de la necesidad a modificar", required = true)
            @PathVariable Long id) {
        NecesidadResponseDTO response = necesidadService.atenderNecesidad(id);
        return ResponseEntity.ok(response);
    }
}