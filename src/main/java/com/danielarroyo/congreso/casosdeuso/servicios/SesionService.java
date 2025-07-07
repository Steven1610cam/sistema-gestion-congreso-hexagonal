package com.danielarroyo.congreso.casosdeuso.servicios;

import com.danielarroyo.congreso.casosdeuso.puertos.entrada.GestionarSesionUseCase;
import com.danielarroyo.congreso.casosdeuso.puertos.salida.SesionRepository;
import com.danielarroyo.congreso.dominio.modelo.Sesion;
import com.danielarroyo.congreso.infraestructura.dto.SesionRequestDTO;
import com.danielarroyo.congreso.infraestructura.dto.SesionResponseDTO;
import com.danielarroyo.congreso.infraestructura.dto.ConsultaResultadoDTO;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * SERVICIO - Implementa casos de uso de sesiones
 * @author Daniel Arroyo
 */
public class SesionService implements GestionarSesionUseCase {
    
    private final SesionRepository repository;
    
    public SesionService(SesionRepository repository) {
        this.repository = repository;
    }
    
    @Override
    public SesionResponseDTO registrarSesion(SesionRequestDTO request) {
        // Validaciones de negocio
        validarDatosSesion(request);
        
        // Convertir DTO a entidad de dominio
        Sesion sesion = new Sesion(
            request.getNombreSesion(),
            request.getFecha(),
            request.getHoraInicio(),
            request.getHoraFinal(),
            request.getEstado() != null ? request.getEstado() : "planificada",
            request.getSalaId()
        );
        
        // Guardar usando repository
        Sesion guardada = repository.guardar(sesion);
        
        // Convertir entidad a DTO de respuesta
        return convertirAResponseDTO(guardada);
    }
    
    @Override
    public List<SesionResponseDTO> obtenerTodasSesiones() {
        return repository.buscarTodas()
                .stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public SesionResponseDTO obtenerSesionPorId(Integer id) {
        return repository.buscarPorId(id)
                .map(this::convertirAResponseDTO)
                .orElse(null);
    }
    
    @Override
    public SesionResponseDTO actualizarSesion(Integer id, SesionRequestDTO request) {
        // Validaciones
        validarDatosSesion(request);
        
        // Buscar sesión existente
        Sesion existente = repository.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Sesión no encontrada"));
        
        // Crear nueva instancia con datos actualizados
        Sesion actualizada = new Sesion(
            id,
            request.getNombreSesion(),
            request.getFecha(),
            request.getHoraInicio(),
            request.getHoraFinal(),
            request.getEstado(),
            request.getSalaId()
        );
        
        // Actualizar en repository
        Sesion guardada = repository.actualizar(actualizada);
        return convertirAResponseDTO(guardada);
    }
    
    @Override
    public boolean eliminarSesion(Integer id) {
        return repository.eliminarPorId(id);
    }
    
    @Override
    public List<SesionResponseDTO> obtenerSesionesPorFecha(LocalDate fecha) {
        return repository.buscarPorFecha(fecha)
                .stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<SesionResponseDTO> obtenerSesionesPorSala(Integer salaId) {
        return repository.buscarPorSala(salaId)
                .stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<SesionResponseDTO> obtenerSesionesSinChairman() {
        return repository.buscarSinChairman()
                .stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<SesionResponseDTO> obtenerSesionesPorChairman(Integer chairmanId) {
        return repository.buscarPorChairman(chairmanId)
                .stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<SesionResponseDTO> obtenerSesionesSinSalaAsignada() {
        return repository.buscarSinSalaAsignada()
                .stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<SesionResponseDTO> obtenerSesionesConMenosDeTresTrabajos() {
        return repository.buscarConMenosDeTresTrabajos()
                .stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<SesionResponseDTO> obtenerSesionesOrdenadasPorFechaHora() {
        return repository.buscarOrdenadasPorFechaHora()
                .stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<SesionResponseDTO> obtenerSesionesPorFechaYSala(LocalDate fecha, Integer salaId) {
        return repository.buscarPorFechaYSala(fecha, salaId)
                .stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<ConsultaResultadoDTO> obtenerSesionesConMasTrabajos() {
        List<Object[]> resultados = repository.obtenerSesionesConMasTrabajos();
        return resultados.stream()
                .map(fila -> {
                    ConsultaResultadoDTO dto = new ConsultaResultadoDTO();
                    dto.setSesionId((Integer) fila[0]);
                    dto.setTituloSesion((String) fila[1]);
                    dto.setCantidad(((Integer) fila[2]).longValue());
                    dto.setDescripcion("Trabajos programados");
                    return dto;
                })
                .collect(Collectors.toList());
    }
    
    @Override
    public List<ConsultaResultadoDTO> obtenerSesionesPorDia() {
        List<Object[]> resultados = repository.obtenerSesionesPorDia();
        return resultados.stream()
                .map(fila -> {
                    ConsultaResultadoDTO dto = new ConsultaResultadoDTO();
                    dto.setFechaSesion((LocalDate) fila[0]);
                    dto.setCantidad(((Integer) fila[1]).longValue());
                    dto.setDescripcion("Sesiones programadas");
                    return dto;
                })
                .collect(Collectors.toList());
    }
    
    // ===== MÉTODOS PRIVADOS =====
    
    /**
     * Validaciones de reglas de negocio para sesiones
     */
    private void validarDatosSesion(SesionRequestDTO request) {
        if (request.getNombreSesion() == null || request.getNombreSesion().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la sesión es obligatorio");
        }
        
        if (request.getFecha() == null) {
            throw new IllegalArgumentException("La fecha de la sesión es obligatoria");
        }
        
        if (request.getHoraInicio() == null) {
            throw new IllegalArgumentException("La hora de inicio es obligatoria");
        }
        
        if (request.getHoraFinal() == null) {
            throw new IllegalArgumentException("La hora final es obligatoria");
        }
        
        if (request.getHoraInicio().isAfter(request.getHoraFinal())) {
            throw new IllegalArgumentException("La hora de inicio debe ser anterior a la hora final");
        }
        
        // Validar que la fecha no sea en el pasado
        if (request.getFecha().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de la sesión no puede ser en el pasado");
        }
    }
    
    /**
     * Mapper: Entidad → Response DTO
     */
    private SesionResponseDTO convertirAResponseDTO(Sesion sesion) {
        return new SesionResponseDTO(
            sesion.getSesionId(),
            sesion.getNombreSesion(),
            sesion.getFecha(),
            sesion.getHoraInicio(),
            sesion.getHoraFinal(),
            sesion.getEstado(),
            sesion.getSalaId(),
            null, // nombreSala - se puede obtener con JOIN si se necesita
            null  // nombreChairman - se puede obtener con JOIN si se necesita
        );
    }
}