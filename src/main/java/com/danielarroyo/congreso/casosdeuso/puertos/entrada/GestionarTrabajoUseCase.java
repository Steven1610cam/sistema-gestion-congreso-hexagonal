package com.danielarroyo.congreso.casosdeuso.puertos.entrada;

import com.danielarroyo.congreso.infraestructura.dto.TrabajoRequestDTO;
import com.danielarroyo.congreso.infraestructura.dto.TrabajoResponseDTO;
import com.danielarroyo.congreso.infraestructura.dto.ConsultaResultadoDTO;
import java.time.LocalDate;
import java.util.List;

/**
 * PUERTO DE ENTRADA - Casos de uso para gestionar trabajos
 * Define las operaciones disponibles para trabajos académicos
 * @author Daniel Arroyo
 */
public interface GestionarTrabajoUseCase {
    
    // ===== OPERACIONES CRUD =====
    TrabajoResponseDTO registrarTrabajo(TrabajoRequestDTO request);
    List<TrabajoResponseDTO> obtenerTodosTrabajos();
    TrabajoResponseDTO obtenerTrabajoPorId(Integer id);
    TrabajoResponseDTO actualizarTrabajo(Integer id, TrabajoRequestDTO request);
    boolean eliminarTrabajo(Integer id);
    
    // ===== CONSULTAS ESPECÍFICAS =====
    List<TrabajoResponseDTO> obtenerTrabajosPorAutor(Integer congresistaId);
    List<TrabajoResponseDTO> obtenerTrabajosPorEstado(String estado);
    List<TrabajoResponseDTO> obtenerTrabajosSeleccionados();
    List<TrabajoResponseDTO> obtenerTrabajosSinRevisar();
    List<TrabajoResponseDTO> obtenerTrabajosPorPalabrasClave(String palabrasClave);
    List<TrabajoResponseDTO> obtenerTrabajosPorInstitucion(String institucion);
    List<TrabajoResponseDTO> obtenerTrabajosConResumenLargo(int minimoCaracteres);
    List<TrabajoResponseDTO> obtenerTrabajosOrdenadosPorTitulo();
    
    // ===== CONSULTAS DE ESTADÍSTICAS =====
    Long contarTrabajosPorAutor(Integer congresistaId);
    Long contarTotalTrabajos();
    List<ConsultaResultadoDTO> obtenerAutoresConMasTrabajos();
    List<ConsultaResultadoDTO> obtenerTrabajosPopulares();
}