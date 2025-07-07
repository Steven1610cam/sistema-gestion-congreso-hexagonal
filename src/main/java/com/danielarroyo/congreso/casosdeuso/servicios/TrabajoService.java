package com.danielarroyo.congreso.casosdeuso.servicios;

import com.danielarroyo.congreso.casosdeuso.puertos.entrada.GestionarTrabajoUseCase;
import com.danielarroyo.congreso.casosdeuso.puertos.salida.TrabajoRepository;
import com.danielarroyo.congreso.dominio.modelo.Trabajo;
import com.danielarroyo.congreso.infraestructura.dto.TrabajoRequestDTO;
import com.danielarroyo.congreso.infraestructura.dto.TrabajoResponseDTO;
import com.danielarroyo.congreso.infraestructura.dto.ConsultaResultadoDTO;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * SERVICIO - Implementa casos de uso de trabajos
 * Conecta puertos IN con puertos OUT
 * @author Daniel Arroyo
 */
public class TrabajoService implements GestionarTrabajoUseCase {
    
    private final TrabajoRepository repository;
    
    // Inyección de dependencias por constructor
    public TrabajoService(TrabajoRepository repository) {
        this.repository = repository;
    }
    
    @Override
    public TrabajoResponseDTO registrarTrabajo(TrabajoRequestDTO request) {
        // Validaciones de negocio
        validarDatosTrabajo(request);
        
        // Convertir DTO a entidad de dominio
        Trabajo trabajo = new Trabajo(
            request.getTitulo(),
            request.getResumen(),
            request.getPalabrasClave(),
            request.getEstado() != null ? request.getEstado() : "recibido"
        );
        
        // Guardar usando repository
        Trabajo guardado = repository.guardar(trabajo);
        
        // Convertir entidad a DTO de respuesta
        return convertirAResponseDTO(guardado);
    }
    
    @Override
    public List<TrabajoResponseDTO> obtenerTodosTrabajos() {
        return repository.buscarTodos()
                .stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public TrabajoResponseDTO obtenerTrabajoPorId(Integer id) {
        return repository.buscarPorId(id)
                .map(this::convertirAResponseDTO)
                .orElse(null);
    }
    
    @Override
    public TrabajoResponseDTO actualizarTrabajo(Integer id, TrabajoRequestDTO request) {
        // Validaciones
        validarDatosTrabajo(request);
        
        // Buscar trabajo existente
        Trabajo existente = repository.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Trabajo no encontrado"));
        
        // Crear nueva instancia con datos actualizados
        Trabajo actualizado = new Trabajo(
            id,
            request.getTitulo(),
            request.getResumen(),
            request.getPalabrasClave(),
            existente.getFechaEnvio(),
            request.getEstado()
        );
        
        // Actualizar en repository
        Trabajo guardado = repository.actualizar(actualizado);
        return convertirAResponseDTO(guardado);
    }
    
    @Override
    public boolean eliminarTrabajo(Integer id) {
        return repository.eliminarPorId(id);
    }
    
    @Override
    public List<TrabajoResponseDTO> obtenerTrabajosPorAutor(Integer congresistaId) {
        return repository.buscarPorAutor(congresistaId)
                .stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<TrabajoResponseDTO> obtenerTrabajosPorEstado(String estado) {
        return repository.buscarPorEstado(estado)
                .stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<TrabajoResponseDTO> obtenerTrabajosSeleccionados() {
        return obtenerTrabajosPorEstado("aceptado");
    }
    
    @Override
    public List<TrabajoResponseDTO> obtenerTrabajosSinRevisar() {
        return repository.buscarSinRevisar()
                .stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<TrabajoResponseDTO> obtenerTrabajosPorPalabrasClave(String palabrasClave) {
        return repository.buscarPorPalabrasClave(palabrasClave)
                .stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<TrabajoResponseDTO> obtenerTrabajosPorInstitucion(String institucion) {
        return repository.buscarPorInstitucion(institucion)
                .stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<TrabajoResponseDTO> obtenerTrabajosConResumenLargo(int minimoCaracteres) {
        return repository.buscarConResumenLargo(minimoCaracteres)
                .stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<TrabajoResponseDTO> obtenerTrabajosOrdenadosPorTitulo() {
        return repository.buscarOrdenadosPorTitulo()
                .stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public Long contarTrabajosPorAutor(Integer congresistaId) {
        return repository.contarTrabajosPorAutor(congresistaId);
    }
    
    @Override
    public Long contarTotalTrabajos() {
        return repository.contarTotalTrabajos();
    }
    
    @Override
    public List<ConsultaResultadoDTO> obtenerAutoresConMasTrabajos() {
        List<Object[]> resultados = repository.obtenerAutoresConMasTrabajos();
        return resultados.stream()
                .map(fila -> {
                    ConsultaResultadoDTO dto = new ConsultaResultadoDTO();
                    dto.setCongresistaId((Integer) fila[0]);
                    dto.setNombreCongresista((String) fila[1]);
                    dto.setCantidad(((Integer) fila[2]).longValue());
                    dto.setDescripcion("Trabajos enviados");
                    return dto;
                })
                .collect(Collectors.toList());
    }
    
    @Override
    public List<ConsultaResultadoDTO> obtenerTrabajosPopulares() {
        List<Object[]> resultados = repository.obtenerTrabajosPopulares();
        return resultados.stream()
                .map(fila -> {
                    ConsultaResultadoDTO dto = new ConsultaResultadoDTO();
                    dto.setTrabajoId((Integer) fila[0]);
                    dto.setTituloTrabajo((String) fila[1]);
                    dto.setCantidad(((Integer) fila[2]).longValue());
                    dto.setDescripcion("Presentaciones programadas");
                    return dto;
                })
                .collect(Collectors.toList());
    }
    
    // ===== MÉTODOS PRIVADOS =====
    
    /**
     * Validaciones de reglas de negocio para trabajos
     */
    private void validarDatosTrabajo(TrabajoRequestDTO request) {
        if (request.getTitulo() == null || request.getTitulo().trim().isEmpty()) {
            throw new IllegalArgumentException("El título del trabajo es obligatorio");
        }
        
        if (request.getResumen() == null || request.getResumen().trim().isEmpty()) {
            throw new IllegalArgumentException("El resumen del trabajo es obligatorio");
        }
        
        if (request.getTitulo().length() > 200) {
            throw new IllegalArgumentException("El título no puede exceder 200 caracteres");
        }
        
        if (request.getResumen().length() > 2000) {
            throw new IllegalArgumentException("El resumen no puede exceder 2000 caracteres");
        }
    }
    
    /**
     * Mapper: Entidad → Response DTO
     */
    private TrabajoResponseDTO convertirAResponseDTO(Trabajo trabajo) {
        return new TrabajoResponseDTO(
            trabajo.getTrabajoId(),
            trabajo.getTitulo(),
            trabajo.getResumen(),
            trabajo.getPalabrasClave(),
            trabajo.getFechaEnvio(),
            trabajo.getEstado()
        );
    }
}