package com.danielarroyo.congreso.dominio.modelo;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * DOMINIO - Entidad Sesion del evento
  @author Daniel Arroyo
 */

public class Sesion {
    
    private Integer sesionId;
    private String nombreSesion; 
    private LocalDate fecha;
    private LocalTime horaInicio;
    private LocalTime horaFinal; 
    private String estado; 
    private Integer salaId;
    
    // Constructor para nuevas sesiones
    public Sesion(String nombreSesion, LocalDate fecha, LocalTime horaInicio, 
                  LocalTime horaFinal, String estado, Integer salaId) {
        this.nombreSesion = nombreSesion;
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.horaFinal = horaFinal;
        this.estado = estado;
        this.salaId = salaId;
    }
    
    // Constructor completo (desde BD)
    public Sesion(Integer sesionId, String nombreSesion, LocalDate fecha, 
                  LocalTime horaInicio, LocalTime horaFinal, String estado, Integer salaId) {
        this.sesionId = sesionId;
        this.nombreSesion = nombreSesion;
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.horaFinal = horaFinal;
        this.estado = estado;
        this.salaId = salaId;
    }
    
    // Lógica de dominio: verificar si tiene chairman asignado (consulta en BD)
    public boolean puedeAsignarChairman() {
        return this.salaId != null && "planificada".equals(this.estado);
    }
    
    // Lógica de dominio: calcular duración en minutos
    public long getDuracionEnMinutos() {
        if (horaInicio != null && horaFinal != null) {
            return java.time.Duration.between(horaInicio, horaFinal).toMinutes();
        }
        return 0;
    }
    
    // Lógica de dominio: verificar si está programada para hoy
    public boolean esHoy() {
        return LocalDate.now().equals(this.fecha);
    }
    
    // Lógica de dominio: verificar si está en curso
    public boolean estaEnCurso() {
        return "en_curso".equals(this.estado);
    }
    
    // Getters
    public Integer getSesionId() { return sesionId; }
    public String getNombreSesion() { return nombreSesion; }
    public LocalDate getFecha() { return fecha; }
    public LocalTime getHoraInicio() { return horaInicio; }
    public LocalTime getHoraFinal() { return horaFinal; }
    public String getEstado() { return estado; }
    public Integer getSalaId() { return salaId; }
    
    // Setters
    public void setEstado(String estado) { this.estado = estado; }
    public void setSalaId(Integer salaId) { this.salaId = salaId; }
    
    @Override
    public String toString() {
        return String.format("Sesion{id=%d, nombre='%s', fecha=%s, estado='%s'}", 
                           sesionId, nombreSesion, fecha, estado);
    }
}