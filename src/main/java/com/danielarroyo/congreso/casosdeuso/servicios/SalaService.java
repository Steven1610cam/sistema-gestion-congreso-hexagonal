package com.danielarroyo.congreso.casosdeuso.servicios;

import com.danielarroyo.congreso.casosdeuso.puertos.entrada.GestionarSalaUseCase;
import com.danielarroyo.congreso.casosdeuso.puertos.salida.SalaRepository;
import com.danielarroyo.congreso.dominio.modelo.Sala;
import com.danielarroyo.congreso.infraestructura.dto.SalaRequestDTO;
import com.danielarroyo.congreso.infraestructura.dto.SalaResponseDTO;
import com.danielarroyo.congreso.infraestructura.dto.ConsultaResultadoDTO;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * SERVICIO - Implementa casos de uso de salas
 * @author Daniel Arroyo
 */
public class SalaService implements GestionarSalaUseCase {
    
    private final SalaRepository repository;
    
    public SalaService(SalaRepository repository) {
        this.repository = repository;
    }
    
    @Override
    public SalaResponseDTO registrarSala(SalaRequestDTO request) {
        // Validaciones de negocio
        validarDatosSala(request);
        
        // Convertir DTO a entidad de dominio
        Sala sala = new Sala(
            request.getNombre(),
            request.getCapacidad(),
            request.getUbicacion(),
            request.getEquipamiento()
        );
        
        // Guardar usando repository
        Sala guardada = repository.guardar(sala);
        
        // Convertir entidad a DTO de respuesta
        return convertirAResponseDTO(guardada);
    }
    
    @Override
    public List<SalaResponseDTO> obtenerTodasSalas() {
        return repository.buscarTodas()
                .stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public SalaResponseDTO obtenerSalaPorId(Integer id) {
        return repository.buscarPorId(id)
                .map(this::convertirAResponseDTO)
                .orElse(null);
    }
    
    @Override
    public SalaResponseDTO actualizarSala(Integer id, SalaRequestDTO request) {
        // Validaciones
        validarDatosSala(request);
        
        // Buscar sala existente
        Sala existente = repository.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Sala no encontrada"));
        
        // Crear nueva instancia con datos actualizados
        Sala actualizada = new Sala(
            id,
            request.getNombre(),
            request.getCapacidad(),
            request.getUbicacion(),
            request.getEquipamiento()
        );
        
        // Actualizar en repository
        Sala guardada = repository.actualizar(actualizada);
        return convertirAResponseDTO(guardada);
    }
    
    @Override
    public boolean eliminarSala(Integer id) {
        return repository.eliminarPorId(id);
    }
    
    @Override
    public List<SalaResponseDTO> obtenerSalasPorCapacidadMinima(Integer capacidadMinima) {
        return repository.buscarPorCapacidadMinima(capacidadMinima)
                .stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<SalaResponseDTO> obtenerSalasDisponibles(LocalDate fecha, LocalTime horaInicio, LocalTime horaFin) {
        return repository.buscarDisponibles(fecha, horaInicio, horaFin)
                .stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<SalaResponseDTO> obtenerSalasGrandes() {
        return repository.buscarGrandes()
                .stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<ConsultaResultadoDTO> obtenerSalasConSesiones() {
        List<Object[]> resultados = repository.obtenerSalasConSesiones();
        return resultados.stream()
                .map(fila -> {
                    ConsultaResultadoDTO dto = new ConsultaResultadoDTO();
                    dto.setSalaId((Integer) fila[0]);
                    dto.setNombreSala((String) fila[1]);
                    dto.setCantidad(((Integer) fila[2]).longValue());
                    dto.setDescripcion("Sesiones asignadas");
                    return dto;
                })
                .collect(Collectors.toList());
    }
    
    // ===== MÉTODOS PRIVADOS =====
    
    /**
     * Validaciones de reglas de negocio para salas
     */
    private void validarDatosSala(SalaRequestDTO request) {
        if (request.getNombre() == null || request.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la sala es obligatorio");
        }
        
        if (request.getCapacidad() == null || request.getCapacidad() <= 0) {
            throw new IllegalArgumentException("La capacidad debe ser un número positivo");
        }
        
        if (request.getUbicacion() == null || request.getUbicacion().trim().isEmpty()) {
            throw new IllegalArgumentException("La ubicación de la sala es obligatoria");
        }
        
        if (request.getCapacidad() > 1000) {
            throw new IllegalArgumentException("La capacidad no puede exceder 1000 personas");
        }
    }
    
    /**
     * Mapper: Entidad → Response DTO
     */
    private SalaResponseDTO convertirAResponseDTO(Sala sala) {
        return new SalaResponseDTO(
            sala.getSalaId(),
            sala.getNombreSala(),
            sala.getCapacidad(),
            sala.getUbicacion(),
            0 // sesionesAsignadas - se puede calcular con consulta si se necesita
        );
    }
}