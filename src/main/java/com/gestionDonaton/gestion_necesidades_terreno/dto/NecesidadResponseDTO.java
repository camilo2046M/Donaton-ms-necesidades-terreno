package com.gestionDonaton.gestion_necesidades_terreno.dto;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class NecesidadResponseDTO {
    private Long id;
    private String recursoNecesitado;
    private Integer cantidad;
    private String ubicacionGeografica;
    private String estado;
    private LocalDateTime fechaReporte;
}