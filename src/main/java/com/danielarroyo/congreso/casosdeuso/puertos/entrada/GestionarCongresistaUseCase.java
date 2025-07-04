package com.danielarroyo.congreso.casosdeuso.puertos.entrada;

import com.danielarroyo.congreso.infraestructura.dto.CongresistaRequestDTO;
import com.danielarroyo.congreso.infraestructura.dto.CongresistaResponseDTO;
import java.util.List;

/**
 * PUERTO DE ENTRADA - Casos de uso para gestionar congresistas
 */

public interface GestionarCongresistaUseCase {
    
    /**
     * Caso de uso: Registrar nuevo congresista
     * @param request Datos del congresista a registrar
     * @return Congresista registrado con ID asignado
     */
    CongresistaResponseDTO registrarCongresista(CongresistaRequestDTO request);
    
    /**
     * Caso de uso: Obtener todos los congresistas
     * @return Lista de congresistas registrados
     */
    List<CongresistaResponseDTO> obtenerTodosCongresistas();
    
    /**
     * Caso de uso: Buscar congresista por ID
     * @param id Identificador del congresista
     * @return Congresista encontrado o null
     */
    CongresistaResponseDTO obtenerCongresistaPorId(Integer id);
    
    /**
     * Caso de uso: Actualizar datos de congresista
     * @param id ID del congresista a actualizar
     * @param request Nuevos datos
     * @return Congresista actualizado
     */
    CongresistaResponseDTO actualizarCongresista(Integer id, CongresistaRequestDTO request);
    
    /**
     * Caso de uso: Eliminar congresista
     * @param id ID del congresista a eliminar
     * @return true si se eliminó correctamente
     */
    boolean eliminarCongresista(Integer id);
    
    /**
     * Consulta específica: Congresistas con teléfono móvil
     * @return Lista de congresistas que pueden recibir SMS
     */
    List<CongresistaResponseDTO> obtenerCongresistasConTelefono();
    
    /**
     * Consulta específica: Miembros del comité organizador
     * @return Lista de organizadores del evento
     */
    List<CongresistaResponseDTO> obtenerMiembrosComiteOrganizador();
}