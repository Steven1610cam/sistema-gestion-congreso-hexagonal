package com.danielarroyo.congreso.dominio.modelo;

/**
 * DOMINIO - Entidad Sala
  @author Daniel Arroyo
 */
public class Sala {
    
    private Integer salaId;
    private String nombreSala; 
    private Integer capacidad;
    private String ubicacion;
    private String equipamiento; 
    
    // Constructor para nuevas salas
    public Sala(String nombreSala, Integer capacidad, String ubicacion, String equipamiento) {
        this.nombreSala = nombreSala;
        this.capacidad = capacidad;
        this.ubicacion = ubicacion;
        this.equipamiento = equipamiento;
    }
    
    // Constructor completo (desde BD)
    public Sala(Integer salaId, String nombreSala, Integer capacidad, 
                String ubicacion, String equipamiento) {
        this.salaId = salaId;
        this.nombreSala = nombreSala;
        this.capacidad = capacidad;
        this.ubicacion = ubicacion;
        this.equipamiento = equipamiento;
    }
    
    // Lógica de dominio: verificar si es sala grande
    public boolean esSalaGrande() {
        return this.capacidad != null && this.capacidad >= 100;
    }
    
    // Lógica de dominio: verificar si tiene equipamiento específico
    public boolean tieneEquipamiento(String tipoEquipamiento) {
        return this.equipamiento != null && 
               this.equipamiento.toLowerCase().contains(tipoEquipamiento.toLowerCase());
    }
    
    // Lógica de dominio: verificar si tiene proyector
    public boolean tieneProyector() {
        return tieneEquipamiento("proyector");
    }
    
    // Getters
    public Integer getSalaId() { return salaId; }
    public String getNombreSala() { return nombreSala; }
    public Integer getCapacidad() { return capacidad; }
    public String getUbicacion() { return ubicacion; }
    public String getEquipamiento() { return equipamiento; }
    
    // Setters
    public void setEquipamiento(String equipamiento) { this.equipamiento = equipamiento; }
    
    @Override
    public String toString() {
        return String.format("Sala{id=%d, nombre='%s', capacidad=%d, ubicacion='%s'}", 
                           salaId, nombreSala, capacidad, ubicacion);
    }
}