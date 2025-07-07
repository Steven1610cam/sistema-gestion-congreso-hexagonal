package com.danielarroyo.congreso.dominio.modelo;

/**
 * DOMINIO - Entidad Autor (Relación Congresista-Trabajo)
  @author Daniel Arroyo
 */

public class Autor {
    
    private Integer autorId;
    private Integer congresistaId;
    private Integer trabajoId;
    private boolean autorPrincipal; // Para distinguir autor principal de coautores
    
    // Constructor para nuevos autores
    public Autor(Integer congresistaId, Integer trabajoId, boolean autorPrincipal) {
        this.congresistaId = congresistaId;
        this.trabajoId = trabajoId;
        this.autorPrincipal = autorPrincipal;
    }
    
    // Constructor completo (desde BD)
    public Autor(Integer autorId, Integer congresistaId, Integer trabajoId, boolean autorPrincipal) {
        this.autorId = autorId;
        this.congresistaId = congresistaId;
        this.trabajoId = trabajoId;
        this.autorPrincipal = autorPrincipal;
    }
    
    // Lógica de dominio: verificar si es autor principal
    public boolean esAutorPrincipal() {
        return this.autorPrincipal;
    }
    
    // Getters
    public Integer getAutorId() { return autorId; }
    public Integer getCongresistaId() { return congresistaId; }
    public Integer getTrabajoId() { return trabajoId; }
    public boolean isAutorPrincipal() { return autorPrincipal; }
    
    // Setters
    public void setAutorPrincipal(boolean autorPrincipal) { 
        this.autorPrincipal = autorPrincipal; 
    }
    
    @Override
    public String toString() {
        return String.format("Autor{id=%d, congresistaId=%d, trabajoId=%d, principal=%s}", 
                           autorId, congresistaId, trabajoId, autorPrincipal);
    }
}