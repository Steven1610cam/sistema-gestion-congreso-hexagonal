package com.danielarroyo.congreso.infraestructura.dto;

/**
 * DTO REQUEST - Datos de entrada para trabajos
 * Usado para crear/actualizar trabajos desde la UI
 * @author Daniel Arroyo
 */
public class TrabajoRequestDTO {
    
    private String titulo;
    private String resumen;
    private String palabrasClave;
    private String estado;
    
    // Constructor vacío
    public TrabajoRequestDTO() {}
    
    // Constructor completo
    public TrabajoRequestDTO(String titulo, String resumen, String palabrasClave, String estado) {
        this.titulo = titulo;
        this.resumen = resumen;
        this.palabrasClave = palabrasClave;
        this.estado = estado;
    }
    
    // Getters y Setters
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    
    public String getResumen() { return resumen; }
    public void setResumen(String resumen) { this.resumen = resumen; }
    
    public String getPalabrasClave() { return palabrasClave; }
    public void setPalabrasClave(String palabrasClave) { this.palabrasClave = palabrasClave; }
    
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}