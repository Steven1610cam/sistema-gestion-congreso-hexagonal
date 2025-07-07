package com.danielarroyo.congreso.infraestructura.dto;

/**
 * DTO RESPONSE - Datos de salida para salas
 * @author Daniel Arroyo
 */
public class SalaResponseDTO {
    
    private Integer salaId;
    private String nombre;
    private Integer capacidad;
    private String ubicacion;
    private boolean esSalaGrande;
    private int sesionesAsignadas; // Cantidad de sesiones en esta sala
    
    // Constructor vacío
    public SalaResponseDTO() {}
    
    // Constructor completo
    public SalaResponseDTO(Integer salaId, String nombre, Integer capacidad, 
                          String ubicacion, int sesionesAsignadas) {
        this.salaId = salaId;
        this.nombre = nombre;
        this.capacidad = capacidad;
        this.ubicacion = ubicacion;
        this.sesionesAsignadas = sesionesAsignadas;
        this.esSalaGrande = capacidad != null && capacidad >= 100;
    }
    
    // Getters y Setters
    public Integer getSalaId() { return salaId; }
    public void setSalaId(Integer salaId) { this.salaId = salaId; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public Integer getCapacidad() { return capacidad; }
    public void setCapacidad(Integer capacidad) { this.capacidad = capacidad; }
    
    public String getUbicacion() { return ubicacion; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }
    
    public boolean isEsSalaGrande() { return esSalaGrande; }
    public void setEsSalaGrande(boolean esSalaGrande) { this.esSalaGrande = esSalaGrande; }
    
    public int getSesionesAsignadas() { return sesionesAsignadas; }
    public void setSesionesAsignadas(int sesionesAsignadas) { this.sesionesAsignadas = sesionesAsignadas; }
}