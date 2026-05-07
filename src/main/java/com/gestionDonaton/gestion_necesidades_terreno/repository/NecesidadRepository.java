package com.gestionDonaton.gestion_necesidades_terreno.repository;

import com.gestionDonaton.gestion_necesidades_terreno.entity.Necesidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NecesidadRepository extends JpaRepository<Necesidad, Long> {

    // Spring Data JPA crea la query automáticamente con solo nombrar bien el método
    List<Necesidad> findByEstado(String estado);
}