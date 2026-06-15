package com.gestionDonaton.gestion_necesidades_terreno.service;



import com.gestionDonaton.gestion_necesidades_terreno.dto.NecesidadRequestDTO;
import com.gestionDonaton.gestion_necesidades_terreno.dto.NecesidadResponseDTO;
import com.gestionDonaton.gestion_necesidades_terreno.entity.Necesidad;
import com.gestionDonaton.gestion_necesidades_terreno.repository.NecesidadRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NecesidadServiceTest {

    @Mock
    private NecesidadRepository necesidadRepository;

    @InjectMocks
    private NecesidadService necesidadService;

    private NecesidadRequestDTO requestDTO;
    private Necesidad necesidadGuardada;

    @BeforeEach
    void setUp() {
        // Preparamos datos de prueba antes de cada test
        requestDTO = new NecesidadRequestDTO();
        requestDTO.setRecursoNecesitado("Agua Potable");
        requestDTO.setCantidad(100);
        requestDTO.setUbicacionGeografica("Plaza Central, Comuna X");

        necesidadGuardada = Necesidad.builder()
                .id(1L)
                .recursoNecesitado("Agua Potable")
                .cantidad(100)
                .ubicacionGeografica("Plaza Central, Comuna X")
                .estado("PENDIENTE")
                .fechaReporte(LocalDateTime.now())
                .build();
    }

    @Test
    void testReportarNecesidad_Exito() {
        // Arrange (Preparación): Le decimos al mock qué devolver cuando intenten guardar
        when(necesidadRepository.save(any(Necesidad.class))).thenReturn(necesidadGuardada);

        // Act (Ejecución): Llamamos al método real de nuestro servicio
        NecesidadResponseDTO response = necesidadService.reportarNecesidad(requestDTO);

        // Assert (Verificación): Comprobamos que el resultado es el esperado
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Agua Potable", response.getRecursoNecesitado());
        assertEquals("PENDIENTE", response.getEstado());

        // Verificamos que el repositorio efectivamente fue llamado una vez
        verify(necesidadRepository, times(1)).save(any(Necesidad.class));
    }

    @Test
    void testListarTodas_Exito() {
        // Arrange
        List<Necesidad> listaSimulada = Arrays.asList(necesidadGuardada);
        when(necesidadRepository.findAll()).thenReturn(listaSimulada);

        // Act
        List<NecesidadResponseDTO> resultado = necesidadService.listarTodas();

        // Assert
        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        assertEquals("Agua Potable", resultado.get(0).getRecursoNecesitado());

        verify(necesidadRepository, times(1)).findAll();
    }
}