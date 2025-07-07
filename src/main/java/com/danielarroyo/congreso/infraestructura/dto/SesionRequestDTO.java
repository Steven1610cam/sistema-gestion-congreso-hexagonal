package com.danielarroyo.congreso.infraestructura.dto;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * DTO REQUEST - Datos de entrada para sesiones
 * @author Daniel Arroyo
 */
public class SesionRequestDTO {
    
    private String titulo;
    private String nombreSesion;
    private LocalDate fecha;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private String estado;
    private Integer salaId;
    private Integer chairmanId;
    
    // Constructor vacío
    public SesionRequestDTO() {}
    
    // Constructor completo
    public SesionRequestDTO(String nombreSesion, LocalDate fecha, LocalTime horaInicio,
                           LocalTime horaFin, String estado, Integer salaId, Integer chairmanId) {
        this.nombreSesion = nombreSesion;
        this.titulo = nombreSesion; // Para compatibilidad
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.estado = estado;
        this.salaId = salaId;
        this.chairmanId = chairmanId;
    }
    
    // Getters y Setters
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { 
        this.titulo = titulo; 
        this.nombreSesion = titulo; // Mantener sincronizado
    }
    
    public String getNombreSesion() { 
        return nombreSesion; 
    }
    public void setNombreSesion(String nombreSesion) { 
        this.nombreSesion = nombreSesion; 
        this.titulo = nombreSesion; // Mantener sincronizado
    }
    
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    
    public LocalTime getHoraInicio() { return horaInicio; }
    public void setHoraInicio(LocalTime horaInicio) { this.horaInicio = horaInicio; }
    
    public LocalTime getHoraFin() { return horaFin; }
    public void setHoraFin(LocalTime horaFin) { this.horaFin = horaFin; }
    
    // Método adicional para compatibilidad con SesionService
    public LocalTime getHoraFinal() { 
        return horaFin; 
    }
    
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    
    public Integer getSalaId() { return salaId; }
    public void setSalaId(Integer salaId) { this.salaId = salaId; }
    
    public Integer getChairmanId() { return chairmanId; }
    public void setChairmanId(Integer chairmanId) { this.chairmanId = chairmanId; }
}