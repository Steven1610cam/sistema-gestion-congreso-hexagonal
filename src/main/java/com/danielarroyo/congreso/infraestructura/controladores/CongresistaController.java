package com.danielarroyo.congreso.infraestructura.controladores;

import com.danielarroyo.congreso.casosdeuso.puertos.entrada.GestionarCongresistaUseCase;
import com.danielarroyo.congreso.casosdeuso.servicios.CongresistaService;
import com.danielarroyo.congreso.infraestructura.adaptadores.CongresistaRepositoryImpl;
import com.danielarroyo.congreso.infraestructura.dto.CongresistaRequestDTO;
import com.danielarroyo.congreso.infraestructura.dto.CongresistaResponseDTO;
import java.util.List;

/**
 * CONTROLADOR - Punto de entrada para operaciones de congresistas
 * Inyecta dependencias y coordina casos de uso
 * @author Daniel Arroyo
 */
public class CongresistaController {
    
    private final GestionarCongresistaUseCase gestionarCongresistaUseCase;
    
    // Inyección de dependencias manual
    public CongresistaController() {
        CongresistaRepositoryImpl repository = new CongresistaRepositoryImpl();
        this.gestionarCongresistaUseCase = new CongresistaService(repository);
    }
    
    /**
     * Registrar nuevo congresista
     */
    public CongresistaResponseDTO registrarCongresista(CongresistaRequestDTO request) {
        return gestionarCongresistaUseCase.registrarCongresista(request);
    }
    
    /**
     * Obtener todos los congresistas
     */
    public List<CongresistaResponseDTO> obtenerTodos() {
        return gestionarCongresistaUseCase.obtenerTodosCongresistas();
    }
    
    /**
     * Buscar congresista por ID
     */
    public CongresistaResponseDTO obtenerPorId(Integer id) {
        return gestionarCongresistaUseCase.obtenerCongresistaPorId(id);
    }
    
    /**
     * Actualizar congresista existente
     */
    public CongresistaResponseDTO actualizar(Integer id, CongresistaRequestDTO request) {
        return gestionarCongresistaUseCase.actualizarCongresista(id, request);
    }
    
    /**
     * Eliminar congresista
     */
    public boolean eliminar(Integer id) {
        return gestionarCongresistaUseCase.eliminarCongresista(id);
    }
    
    /**
     * Consulta: congresistas con teléfono móvil
     */
    public List<CongresistaResponseDTO> obtenerConTelefono() {
        return gestionarCongresistaUseCase.obtenerCongresistasConTelefono();
    }
    
    /**
     * Consulta: miembros del comité organizador
     */
    public List<CongresistaResponseDTO> obtenerMiembrosComite() {
        return gestionarCongresistaUseCase.obtenerMiembrosComiteOrganizador();
    }

    public List<CongresistaResponseDTO> listarTodos() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public CongresistaResponseDTO buscarPorId(Long idCongresistaSeleccionado) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void actualizar(Long idCongresistaSeleccionado, CongresistaRequestDTO dto) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void crear(CongresistaRequestDTO dto) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void eliminar(Long idCongresistaSeleccionado) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public List<CongresistaResponseDTO> buscar(String criterio) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}