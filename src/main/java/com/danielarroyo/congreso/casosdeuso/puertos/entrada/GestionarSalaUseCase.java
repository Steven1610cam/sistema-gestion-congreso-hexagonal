package com.danielarroyo.congreso.casosdeuso.puertos.entrada;

import com.danielarroyo.congreso.infraestructura.dto.SalaRequestDTO;
import com.danielarroyo.congreso.infraestructura.dto.SalaResponseDTO;
import com.danielarroyo.congreso.infraestructura.dto.ConsultaResultadoDTO;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * PUERTO DE ENTRADA - Casos de uso para gestionar salas
 * @author Daniel Arroyo
 */
public interface GestionarSalaUseCase {
    
    // ===== OPERACIONES CRUD =====
    SalaResponseDTO registrarSala(SalaRequestDTO request);
    List<SalaResponseDTO> obtenerTodasSalas();
    SalaResponseDTO obtenerSalaPorId(Integer id);
    SalaResponseDTO actualizarSala(Integer id, SalaRequestDTO request);
    boolean eliminarSala(Integer id);
    
    // ===== CONSULTAS ESPECÍFICAS =====
    List<SalaResponseDTO> obtenerSalasPorCapacidadMinima(Integer capacidadMinima);
    List<SalaResponseDTO> obtenerSalasDisponibles(LocalDate fecha, LocalTime horaInicio, LocalTime horaFin);
    List<SalaResponseDTO> obtenerSalasGrandes();
    
    // ===== CONSULTAS DE ESTADÍSTICAS =====
    List<ConsultaResultadoDTO> obtenerSalasConSesiones();
}