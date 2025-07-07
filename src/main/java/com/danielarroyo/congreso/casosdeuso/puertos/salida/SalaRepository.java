package com.danielarroyo.congreso.casosdeuso.puertos.salida;

import com.danielarroyo.congreso.dominio.modelo.Sala;
import java.util.List;
import java.util.Optional;

/**
 * PUERTO DE SALIDA - Contrato para persistencia de salas
 * @author Daniel Arroyo
 */
public interface SalaRepository {
    
    // Operaciones CRUD básicas
    Sala guardar(Sala sala);
    Optional<Sala> buscarPorId(Integer id);
    List<Sala> buscarTodas();
    Sala actualizar(Sala sala);
    boolean eliminarPorId(Integer id);
    
    // Consultas específicas
    List<Sala> buscarPorCapacidadMinima(Integer capacidadMinima);
    List<Sala> buscarDisponibles(java.time.LocalDate fecha, java.time.LocalTime horaInicio, java.time.LocalTime horaFin);
    List<Sala> buscarGrandes(); // Capacidad >= 100
    
    // Consultas con estadísticas
    List<Object[]> obtenerSalasConSesiones(); // [salaId, nombre, cantidadSesiones]
}