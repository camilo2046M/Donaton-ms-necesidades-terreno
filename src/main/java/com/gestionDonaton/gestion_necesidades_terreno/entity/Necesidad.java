package com.gestionDonaton.gestion_necesidades_terreno.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "necesidades")
public class Necesidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "recurso_necesitado", nullable = false)
    private String recursoNecesitado; // Ej: "Agua potable", "Mantas"

    @Column(nullable = false)
    private Integer cantidad;

    @Column(name = "ubicacion_geografica", nullable = false)
    private String ubicacionGeografica; // Dirección o coordenadas

    @Column(nullable = false)
    private String estado; // Ej: "PENDIENTE", "ATENDIDA"

    @Column(name = "fecha_reporte", updatable = false)
    private LocalDateTime fechaReporte;

    // Se ejecuta automáticamente antes de guardar en la BD por primera vez
    @PrePersist
    protected void onCreate() {
        this.fechaReporte = LocalDateTime.now();
        if (this.estado == null) {
            this.estado = "PENDIENTE";
        }
    }
}