package com.danielarroyo.congreso.casosdeuso.servicios;

import com.danielarroyo.congreso.casosdeuso.puertos.entrada.GestionarCongresistaUseCase;
import com.danielarroyo.congreso.casosdeuso.puertos.salida.CongresistaRepository;
import com.danielarroyo.congreso.dominio.modelo.Congresista;
import com.danielarroyo.congreso.infraestructura.dto.CongresistaRequestDTO;
import com.danielarroyo.congreso.infraestructura.dto.CongresistaResponseDTO;
import java.util.List;
import java.util.stream.Collectors;

/**
 * SERVICIO - Implementa casos de uso de congresistas
 * Conecta puertos IN con puertos OUT
 */

public class CongresistaService implements GestionarCongresistaUseCase {
    
    private final CongresistaRepository repository;
    
    // Inyección de dependencias por constructor
    public CongresistaService(CongresistaRepository repository) {
        this.repository = repository;
    }
    
    @Override
    public CongresistaResponseDTO registrarCongresista(CongresistaRequestDTO request) {
        // Convertir DTO a entidad de dominio
        Congresista congresista = new Congresista(
            request.getNombre(),
            request.getPrimerApellido(),
            request.getInstitucionAfiliada(),
            request.getEmail(),
            request.getTelefonoMovil(),
            request.isEsMiembroComite(),
            request.isEsOrganizador()
        );
        
        // Guardar usando repository
        Congresista guardado = repository.guardar(congresista);
        
        // Convertir entidad a DTO de respuesta
        return convertirAResponseDTO(guardado);
    }
    
    @Override
    public List<CongresistaResponseDTO> obtenerTodosCongresistas() {
        return repository.buscarTodos()
                .stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public CongresistaResponseDTO obtenerCongresistaPorId(Integer id) {
        return repository.buscarPorId(id)
                .map(this::convertirAResponseDTO)
                .orElse(null);
    }
    
    @Override
    public CongresistaResponseDTO actualizarCongresista(Integer id, CongresistaRequestDTO request) {
        // Buscar congresista existente
        Congresista existente = repository.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Congresista no encontrado"));
        
        // Crear nueva instancia con datos actualizados
        Congresista actualizado = new Congresista(
            id,
            request.getNombre(),
            request.getPrimerApellido(),
            request.getInstitucionAfiliada(),
            request.getEmail(),
            request.getTelefonoMovil(),
            existente.getFechaRegistro(),
            request.isEsMiembroComite(),
            request.isEsOrganizador()
        );
        
        // Actualizar en repository
        Congresista guardado = repository.actualizar(actualizado);
        return convertirAResponseDTO(guardado);
    }
    
    @Override
    public boolean eliminarCongresista(Integer id) {
        return repository.eliminarPorId(id);
    }
    
    @Override
    public List<CongresistaResponseDTO> obtenerCongresistasConTelefono() {
        return repository.buscarConTelefonoMovil()
                .stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<CongresistaResponseDTO> obtenerMiembrosComiteOrganizador() {
        return repository.buscarMiembrosComite()
                .stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }
    
    // Mapper: Entidad → Response DTO
    private CongresistaResponseDTO convertirAResponseDTO(Congresista congresista) {
        return new CongresistaResponseDTO(
            congresista.getCongresistaId(),
            congresista.getNombre(),
            congresista.getPrimerApellido(),
            congresista.getInstitucionAfiliada(),
            congresista.getEmail(),
            congresista.getTelefonoMovil(),
            congresista.getFechaRegistro(),
            congresista.isEsMiembroComite(),
            congresista.isEsOrganizador()
        );
    }
}