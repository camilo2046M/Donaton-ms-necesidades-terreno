package com.gestionDonaton.gestion_necesidades_terreno.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gestionDonaton.gestion_necesidades_terreno.dto.NecesidadRequestDTO;
import com.gestionDonaton.gestion_necesidades_terreno.dto.NecesidadResponseDTO;
import com.gestionDonaton.gestion_necesidades_terreno.service.NecesidadService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(NecesidadController.class)
class NecesidadControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // 1. Inicializa el ObjectMapper aquí directamente para que NUNCA sea null
    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());

    @MockitoBean
    private NecesidadService necesidadService;

    @Test
    void reportarNecesidad_DeberiaRetornar201Created() throws Exception {
        // Arrange
        NecesidadRequestDTO requestDTO = NecesidadRequestDTO.builder()
                .recursoNecesitado("Medicamentos")
                .cantidad(100)
                .ubicacionGeografica("Sector Sur")
                .build();

        NecesidadResponseDTO responseDTO = NecesidadResponseDTO.builder()
                .id(1L)
                .recursoNecesitado("Medicamentos")
                .cantidad(100)
                .ubicacionGeografica("Sector Sur")
                .estado("PENDIENTE")
                .build();

        when(necesidadService.reportarNecesidad(any(NecesidadRequestDTO.class))).thenReturn(responseDTO);

        // Act & Assert
        mockMvc.perform(post("/api/v1/necesidades")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.recursoNecesitado").value("Medicamentos"))
                .andExpect(jsonPath("$.cantidad").value(100))
                .andExpect(jsonPath("$.estado").value("PENDIENTE"));
    }

    @Test
    void listarNecesidades_DeberiaRetornarListaDeNecesidades() throws Exception {
        // Arrange
        NecesidadResponseDTO necesidad1 = NecesidadResponseDTO.builder()
                .id(1L).recursoNecesitado("Agua").cantidad(50).build();
        NecesidadResponseDTO necesidad2 = NecesidadResponseDTO.builder()
                .id(2L).recursoNecesitado("Ropa").cantidad(200).build();

        List<NecesidadResponseDTO> listaCompleta = Arrays.asList(necesidad1, necesidad2);

        when(necesidadService.listarTodas()).thenReturn(listaCompleta);

        // Act & Assert
        mockMvc.perform(get("/api/v1/necesidades"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].recursoNecesitado").value("Agua"))
                .andExpect(jsonPath("$[1].recursoNecesitado").value("Ropa"));
    }
}