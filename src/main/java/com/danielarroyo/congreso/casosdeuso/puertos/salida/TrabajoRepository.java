package com.danielarroyo.congreso.casosdeuso.puertos.salida;

import com.danielarroyo.congreso.dominio.modelo.Trabajo;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * PUERTO DE SALIDA - Contrato para persistencia de trabajos
 * Repository para operaciones CRUD y consultas específicas
 * @author Daniel Arroyo
 */
public interface TrabajoRepository {
    
    // Operaciones CRUD básicas
    Trabajo guardar(Trabajo trabajo);
    Optional<Trabajo> buscarPorId(Integer id);
    List<Trabajo> buscarTodos();
    Trabajo actualizar(Trabajo trabajo);
    boolean eliminarPorId(Integer id);
    
    
    List<Trabajo> buscarPorAutor(Integer congresistaId);
    List<Trabajo> buscarPorEstado(String estado);
    List<Trabajo> buscarPorPalabrasClave(String palabrasClave);
    List<Trabajo> buscarPorInstitucion(String institucion);
    List<Trabajo> buscarSeleccionados();
    List<Trabajo> buscarSinRevisar();
    List<Trabajo> buscarPorFechaEnvio(LocalDate fechaDesde, LocalDate fechaHasta);
    List<Trabajo> buscarConResumenLargo(int minimoCaracteres);
    List<Trabajo> buscarOrdenadosPorTitulo();
    
    // Consultas de estadísticas
    Long contarTrabajosPorAutor(Integer congresistaId);
    Long contarTotalTrabajos();
    List<Object[]> obtenerAutoresConMasTrabajos(); // [congresistaId, nombreCompleto, cantidadTrabajos]
    List<Object[]> obtenerTrabajosPopulares(); // [trabajoId, titulo, cantidadPresentaciones]
}