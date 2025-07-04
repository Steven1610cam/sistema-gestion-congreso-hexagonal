package com.danielarroyo.congreso.casosdeuso.puertos.salida;

import com.danielarroyo.congreso.dominio.modelo.Congresista;
import java.util.List;
import java.util.Optional;

/**
 * PUERTO DE SALIDA - Contrato para persistencia de congresistas
 */

public interface CongresistaRepository {
    
    /**
     * Guardar nuevo congresista en BD
     * @param congresista Entidad a persistir
     * @return Congresista con ID asignado
     */
    Congresista guardar(Congresista congresista);
    
    /**
     * Buscar congresista por ID
     * @param id Identificador único
     * @return Optional con congresista o vacío
     */
    Optional<Congresista> buscarPorId(Integer id);
    
    /**
     * Obtener todos los congresistas
     * @return Lista completa de congresistas
     */
    List<Congresista> buscarTodos();
    
    /**
     * Actualizar congresista existente
     * @param congresista Entidad con datos actualizados
     * @return Congresista actualizado
     */
    Congresista actualizar(Congresista congresista);
    
    /**
     * Eliminar congresista por ID
     * @param id Identificador del congresista
     * @return true si se eliminó correctamente
     */
    boolean eliminarPorId(Integer id);
    
    /**
     * Buscar por email (único)
     * @param email Correo electrónico
     * @return Optional con congresista o vacío
     */
    Optional<Congresista> buscarPorEmail(String email);
    
    /**
     * Consulta específica: congresistas con teléfono
     * @return Lista de congresistas que pueden recibir SMS
     */
    List<Congresista> buscarConTelefonoMovil();
    
    /**
     * Consulta específica: miembros del comité
     * @return Lista de miembros del comité organizador
     */
    List<Congresista> buscarMiembrosComite();
    
    /**
     * Consulta específica: organizadores del evento
     * @return Lista de organizadores
     */
    List<Congresista> buscarOrganizadores();
}