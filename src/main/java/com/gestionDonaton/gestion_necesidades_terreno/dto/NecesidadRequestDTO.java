package com.gestionDonaton.gestion_necesidades_terreno.dto;

import lombok.Data;

@Data
public class NecesidadRequestDTO {
    private String recursoNecesitado;
    private Integer cantidad;
    private String ubicacionGeografica;
}