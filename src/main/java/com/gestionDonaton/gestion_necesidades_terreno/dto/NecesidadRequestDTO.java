package com.gestionDonaton.gestion_necesidades_terreno.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NecesidadRequestDTO {
    private String recursoNecesitado;
    private Integer cantidad;
    private String ubicacionGeografica;
}