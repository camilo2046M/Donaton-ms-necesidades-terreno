package com.gestionDonaton.gestion_necesidades_terreno.service;

import com.gestionDonaton.gestion_necesidades_terreno.dto.NecesidadRequestDTO;
import com.gestionDonaton.gestion_necesidades_terreno.dto.NecesidadResponseDTO;
import com.gestionDonaton.gestion_necesidades_terreno.entity.Necesidad;
import com.gestionDonaton.gestion_necesidades_terreno.repository.NecesidadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NecesidadService {

    private final NecesidadRepository necesidadRepository;

    public NecesidadResponseDTO reportarNecesidad(NecesidadRequestDTO request) {
        // Mapeamos el DTO a la Entidad
        Necesidad nuevaNecesidad = Necesidad.builder()
                .recursoNecesitado(request.getRecursoNecesitado())
                .cantidad(request.getCantidad())
                .ubicacionGeografica(request.getUbicacionGeografica())
                .build();

        // Guardamos en BD utilizando el Repository Pattern
        Necesidad necesidadGuardada = necesidadRepository.save(nuevaNecesidad);

        // Retornamos la respuesta mapeada a DTO
        return mapToResponseDTO(necesidadGuardada);
    }

    public List<NecesidadResponseDTO> listarTodas() {
        return necesidadRepository.findAll().stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    // Método auxiliar para no repetir código
    private NecesidadResponseDTO mapToResponseDTO(Necesidad necesidad) {
        return NecesidadResponseDTO.builder()
                .id(necesidad.getId())
                .recursoNecesitado(necesidad.getRecursoNecesitado())
                .cantidad(necesidad.getCantidad())
                .ubicacionGeografica(necesidad.getUbicacionGeografica())
                .estado(necesidad.getEstado())
                .fechaReporte(necesidad.getFechaReporte())
                .build();
    }
}