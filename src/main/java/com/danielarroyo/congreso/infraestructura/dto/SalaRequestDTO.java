package com.danielarroyo.congreso.infraestructura.dto;

/**
 * DTO REQUEST - Datos de entrada para salas
 * @author Daniel Arroyo
 */
public class SalaRequestDTO {
    
    private String nombre;
    private Integer capacidad;
    private String ubicacion;
    private String equipamiento;
    
    // Constructor vacío
    public SalaRequestDTO() {}
    
    // Constructor sin equipamiento (para compatibilidad)
    public SalaRequestDTO(String nombre, Integer capacidad, String ubicacion) {
        this.nombre = nombre;
        this.capacidad = capacidad;
        this.ubicacion = ubicacion;
        this.equipamiento = ""; // Valor por defecto
    }
    
    // Constructor completo
    public SalaRequestDTO(String nombre, Integer capacidad, String ubicacion, String equipamiento) {
        this.nombre = nombre;
        this.capacidad = capacidad;
        this.ubicacion = ubicacion;
        this.equipamiento = equipamiento;
    }
    
    // Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public Integer getCapacidad() { return capacidad; }
    public void setCapacidad(Integer capacidad) { this.capacidad = capacidad; }
    
    public String getUbicacion() { return ubicacion; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }
    
    public String getEquipamiento() { return equipamiento; }
    public void setEquipamiento(String equipamiento) { this.equipamiento = equipamiento; }
}