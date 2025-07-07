package com.danielarroyo.congreso.casosdeuso.puertos.salida;

import com.danielarroyo.congreso.dominio.modelo.Sesion;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * PUERTO DE SALIDA - Contrato para persistencia de sesiones
 * @author Daniel Arroyo
 */
public interface SesionRepository {
    
    // Operaciones CRUD básicas
    Sesion guardar(Sesion sesion);
    Optional<Sesion> buscarPorId(Integer id);
    List<Sesion> buscarTodas();
    Sesion actualizar(Sesion sesion);
    boolean eliminarPorId(Integer id);
    
    // Consultas específicas
    List<Sesion> buscarPorFecha(LocalDate fecha);
    List<Sesion> buscarPorSala(Integer salaId);
    List<Sesion> buscarSinChairman();
    List<Sesion> buscarPorChairman(Integer chairmanId);
    List<Sesion> buscarSinSalaAsignada();
    List<Sesion> buscarConMenosDeTresTrabajos();
    List<Sesion> buscarOrdenadasPorFechaHora();
    List<Sesion> buscarPorFechaYSala(LocalDate fecha, Integer salaId);
    
    // Consultas de estadísticas
    List<Object[]> obtenerSesionesConMasTrabajos(); // [sesionId, titulo, cantidadTrabajos]
    List<Object[]> obtenerSesionesPorDia(); // [fecha, cantidadSesiones]
}