package com.gestionDonaton.gestion_necesidades_terreno.service;

import com.gestionDonaton.gestion_necesidades_terreno.dto.NecesidadRequestDTO;
import com.gestionDonaton.gestion_necesidades_terreno.dto.NecesidadResponseDTO;
import com.gestionDonaton.gestion_necesidades_terreno.entity.Necesidad;
import com.gestionDonaton.gestion_necesidades_terreno.repository.NecesidadRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NecesidadServiceTest {

    @Mock
    private NecesidadRepository necesidadRepository;

    @InjectMocks
    private NecesidadService necesidadService;

    @Test
    void reportarNecesidad_DeberiaGuardarCorrectamenteYRetornarDTO() {
        // Arrange
        NecesidadRequestDTO request = NecesidadRequestDTO.builder()
                .recursoNecesitado("Agua Mineral")
                .cantidad(500)
                .ubicacionGeografica("Zona Norte - Campamento 1")
                .build();

        Necesidad mockNecesidadGuardada = Necesidad.builder()
                .id(1L)
                .recursoNecesitado("Agua Mineral")
                .cantidad(500)
                .ubicacionGeografica("Zona Norte - Campamento 1")
                .estado("PENDIENTE")
                .fechaReporte(LocalDateTime.now())
                .build();

        // Al simular una interfaz (Repository), Mockito no tendrá problemas en Java 26
        when(necesidadRepository.save(any(Necesidad.class))).thenReturn(mockNecesidadGuardada);

        // Act
        NecesidadResponseDTO response = necesidadService.reportarNecesidad(request);

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getRecursoNecesitado()).isEqualTo("Agua Mineral");
        assertThat(response.getCantidad()).isEqualTo(500);
        assertThat(response.getEstado()).isEqualTo("PENDIENTE");

        verify(necesidadRepository, times(1)).save(any(Necesidad.class));
    }

    @Test
    void listarTodas_DeberiaRetornarListaDeNecesidadesMapeadas() {
        // Arrange
        Necesidad n1 = Necesidad.builder().id(1L).recursoNecesitado("Mantas").cantidad(100).build();
        Necesidad n2 = Necesidad.builder().id(2L).recursoNecesitado("Carpas").cantidad(20).build();

        when(necesidadRepository.findAll()).thenReturn(Arrays.asList(n1, n2));

        // Act
        List<NecesidadResponseDTO> resultado = necesidadService.listarTodas();

        // Assert
        assertThat(resultado).isNotNull().hasSize(2);
        assertThat(resultado.get(0).getRecursoNecesitado()).isEqualTo("Mantas");
        assertThat(resultado.get(1).getRecursoNecesitado()).isEqualTo("Carpas");

        verify(necesidadRepository, times(1)).findAll();
    }
}