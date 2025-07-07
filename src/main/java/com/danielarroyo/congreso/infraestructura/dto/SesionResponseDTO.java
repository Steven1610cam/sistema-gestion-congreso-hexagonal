package com.danielarroyo.congreso.infraestructura.dto;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * DTO RESPONSE - Datos de salida para sesiones 
 * @author Daniel Arroyo
 */
public class SesionResponseDTO {
    
    private Integer sesionId;
    private String nombreSesion; // Corregido
    private LocalDate fecha;
    private LocalTime horaInicio;
    private LocalTime horaFinal; // Corregido
    private String estado; // Nuevo campo
    private Integer salaId;
    private String nombreSala;
    private String nombreChairman;
    private long duracionMinutos;
    
    // Constructor vacío
    public SesionResponseDTO() {}
    
    // Constructor completo corregido
    public SesionResponseDTO(Integer sesionId, String nombreSesion, LocalDate fecha,
                            LocalTime horaInicio, LocalTime horaFinal, String estado,
                            Integer salaId, String nombreSala, String nombreChairman) {
        this.sesionId = sesionId;
        this.nombreSesion = nombreSesion;
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.horaFinal = horaFinal;
        this.estado = estado;
        this.salaId = salaId;
        this.nombreSala = nombreSala;
        this.nombreChairman = nombreChairman;
        // Calcular duración
        if (horaInicio != null && horaFinal != null) {
            this.duracionMinutos = java.time.Duration.between(horaInicio, horaFinal).toMinutes();
        }
    }
    
    // Getters y Setters corregidos
    public Integer getSesionId() { return sesionId; }
    public void setSesionId(Integer sesionId) { this.sesionId = sesionId; }
    
    public String getNombreSesion() { return nombreSesion; }
    public void setNombreSesion(String nombreSesion) { this.nombreSesion = nombreSesion; }
    
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    
    public LocalTime getHoraInicio() { return horaInicio; }
    public void setHoraInicio(LocalTime horaInicio) { this.horaInicio = horaInicio; }
    
    public LocalTime getHoraFinal() { return horaFinal; }
    public void setHoraFinal(LocalTime horaFinal) { this.horaFinal = horaFinal; }
    
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    
    public Integer getSalaId() { return salaId; }
    public void setSalaId(Integer salaId) { this.salaId = salaId; }
    
    public String getNombreSala() { return nombreSala; }
    public void setNombreSala(String nombreSala) { this.nombreSala = nombreSala; }
    
    public String getNombreChairman() { return nombreChairman; }
    public void setNombreChairman(String nombreChairman) { this.nombreChairman = nombreChairman; }
    
    public long getDuracionMinutos() { return duracionMinutos; }
    public void setDuracionMinutos(long duracionMinutos) { this.duracionMinutos = duracionMinutos; }
}