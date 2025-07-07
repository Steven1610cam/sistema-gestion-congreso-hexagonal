package com.danielarroyo.congreso.casosdeuso.puertos.entrada;

import com.danielarroyo.congreso.infraestructura.dto.SesionRequestDTO;
import com.danielarroyo.congreso.infraestructura.dto.SesionResponseDTO;
import com.danielarroyo.congreso.infraestructura.dto.ConsultaResultadoDTO;
import java.time.LocalDate;
import java.util.List;

/**
 * PUERTO DE ENTRADA - Casos de uso para gestionar sesiones
 * @author Daniel Arroyo
 */
public interface GestionarSesionUseCase {
    
    // ===== OPERACIONES CRUD =====
    SesionResponseDTO registrarSesion(SesionRequestDTO request);
    List<SesionResponseDTO> obtenerTodasSesiones();
    SesionResponseDTO obtenerSesionPorId(Integer id);
    SesionResponseDTO actualizarSesion(Integer id, SesionRequestDTO request);
    boolean eliminarSesion(Integer id);
    
    // ===== CONSULTAS ESPECÍFICAS =====
    List<SesionResponseDTO> obtenerSesionesPorFecha(LocalDate fecha);
    List<SesionResponseDTO> obtenerSesionesPorSala(Integer salaId);
    List<SesionResponseDTO> obtenerSesionesSinChairman();
    List<SesionResponseDTO> obtenerSesionesPorChairman(Integer chairmanId);
    List<SesionResponseDTO> obtenerSesionesSinSalaAsignada();
    List<SesionResponseDTO> obtenerSesionesConMenosDeTresTrabajos();
    List<SesionResponseDTO> obtenerSesionesOrdenadasPorFechaHora();
    List<SesionResponseDTO> obtenerSesionesPorFechaYSala(LocalDate fecha, Integer salaId);
    
    // ===== CONSULTAS DE ESTADÍSTICAS =====
    List<ConsultaResultadoDTO> obtenerSesionesConMasTrabajos();
    List<ConsultaResultadoDTO> obtenerSesionesPorDia();
}