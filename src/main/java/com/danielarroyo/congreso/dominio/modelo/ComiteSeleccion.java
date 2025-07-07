package com.danielarroyo.congreso.dominio.modelo;

import java.time.LocalDate;

/**
 * DOMINIO - Entidad ComiteSeleccion
  @author Daniel Arroyo
 */

public class ComiteSeleccion {
    
    private Integer comiteId;
    private Integer congresistaId;
    private String especialidad; // Área de expertise del miembro
    private LocalDate fechaIngreso;
    private boolean activo;
    private String cargo; // presidente, secretario, vocal, etc.
    
    // Constructor para nuevos miembros
    public ComiteSeleccion(Integer congresistaId, String especialidad, String cargo) {
        this.congresistaId = congresistaId;
        this.especialidad = especialidad;
        this.fechaIngreso = LocalDate.now();
        this.activo = true;
        this.cargo = cargo;
    }
    
    // Constructor completo (desde BD)
    public ComiteSeleccion(Integer comiteId, Integer congresistaId, String especialidad,
                          LocalDate fechaIngreso, boolean activo, String cargo) {
        this.comiteId = comiteId;
        this.congresistaId = congresistaId;
        this.especialidad = especialidad;
        this.fechaIngreso = fechaIngreso;
        this.activo = activo;
        this.cargo = cargo;
    }
    
    // Lógica de dominio: verificar si es miembro activo
    public boolean esMiembroActivo() {
        return this.activo;
    }
    
    // Lógica de dominio: verificar si tiene cargo directivo
    public boolean tieneCargoDirectivo() {
        return "presidente".equalsIgnoreCase(this.cargo) || 
               "secretario".equalsIgnoreCase(this.cargo);
    }
    
    // Getters
    public Integer getComiteId() { return comiteId; }
    public Integer getCongresistaId() { return congresistaId; }
    public String getEspecialidad() { return especialidad; }
    public LocalDate getFechaIngreso() { return fechaIngreso; }
    public boolean isActivo() { return activo; }
    public String getCargo() { return cargo; }
    
    // Setters
    public void setActivo(boolean activo) { this.activo = activo; }
    public void setCargo(String cargo) { this.cargo = cargo; }
    
    @Override
    public String toString() {
        return String.format("ComiteSeleccion{id=%d, congresistaId=%d, cargo='%s', activo=%s}", 
                           comiteId, congresistaId, cargo, activo);
    }
}