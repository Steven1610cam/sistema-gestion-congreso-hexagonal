package com.danielarroyo.congreso.dominio.modelo;

/**
 * DOMINIO - Entidad Presentacion (Relación Trabajo-Sesión)
  @author Daniel Arroyo
 */

public class Presentacion {
    
    private Integer presentacionId;
    private Integer trabajoId;
    private Integer sesionId;
    private Integer ordenPresentacion; // Orden dentro de la sesión
    private Integer duracionMinutos; // Tiempo asignado para la presentación
    
    // Constructor para nuevas presentaciones
    public Presentacion(Integer trabajoId, Integer sesionId, Integer ordenPresentacion, 
                       Integer duracionMinutos) {
        this.trabajoId = trabajoId;
        this.sesionId = sesionId;
        this.ordenPresentacion = ordenPresentacion;
        this.duracionMinutos = duracionMinutos;
    }
    
    // Constructor completo (desde BD)
    public Presentacion(Integer presentacionId, Integer trabajoId, Integer sesionId,
                       Integer ordenPresentacion, Integer duracionMinutos) {
        this.presentacionId = presentacionId;
        this.trabajoId = trabajoId;
        this.sesionId = sesionId;
        this.ordenPresentacion = ordenPresentacion;
        this.duracionMinutos = duracionMinutos;
    }
    
    // Lógica de dominio: verificar si es presentación larga (más de 30 min)
    public boolean esPresentacionLarga() {
        return this.duracionMinutos != null && this.duracionMinutos > 30;
    }
    
    // Lógica de dominio: verificar si es la primera presentación
    public boolean esPrimeraPresentacion() {
        return this.ordenPresentacion != null && this.ordenPresentacion == 1;
    }
    
    // Getters
    public Integer getPresentacionId() { return presentacionId; }
    public Integer getTrabajoId() { return trabajoId; }
    public Integer getSesionId() { return sesionId; }
    public Integer getOrdenPresentacion() { return ordenPresentacion; }
    public Integer getDuracionMinutos() { return duracionMinutos; }
    
    // Setters
    public void setOrdenPresentacion(Integer ordenPresentacion) { 
        this.ordenPresentacion = ordenPresentacion; 
    }
    public void setDuracionMinutos(Integer duracionMinutos) { 
        this.duracionMinutos = duracionMinutos; 
    }
    
    @Override
    public String toString() {
        return String.format("Presentacion{id=%d, trabajoId=%d, sesionId=%d, orden=%d}", 
                           presentacionId, trabajoId, sesionId, ordenPresentacion);
    }
}