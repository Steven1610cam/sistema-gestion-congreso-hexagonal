package com.danielarroyo.congreso.dominio.modelo;

/**
 * DOMINIO - Entidad Chairman (Moderador de sesión)
  @author Daniel Arroyo
 */

public class Chairman {
    
    private Integer chairmanId;
    private Integer congresistaId;
    private Integer sesionId;
    private String observaciones; // Notas especiales sobre la moderación
    
    // Constructor para nuevos chairman
    public Chairman(Integer congresistaId, Integer sesionId, String observaciones) {
        this.congresistaId = congresistaId;
        this.sesionId = sesionId;
        this.observaciones = observaciones;
    }
    
    // Constructor completo (desde BD)
    public Chairman(Integer chairmanId, Integer congresistaId, Integer sesionId, 
                   String observaciones) {
        this.chairmanId = chairmanId;
        this.congresistaId = congresistaId;
        this.sesionId = sesionId;
        this.observaciones = observaciones;
    }
    
    // Lógica de dominio: verificar si tiene observaciones especiales
    public boolean tieneObservaciones() {
        return this.observaciones != null && !this.observaciones.trim().isEmpty();
    }
    
    // Getters
    public Integer getChairmanId() { return chairmanId; }
    public Integer getCongresistaId() { return congresistaId; }
    public Integer getSesionId() { return sesionId; }
    public String getObservaciones() { return observaciones; }
    
    // Setters
    public void setObservaciones(String observaciones) { 
        this.observaciones = observaciones; 
    }
    
    @Override
    public String toString() {
        return String.format("Chairman{id=%d, congresistaId=%d, sesionId=%d}", 
                           chairmanId, congresistaId, sesionId);
    }
}