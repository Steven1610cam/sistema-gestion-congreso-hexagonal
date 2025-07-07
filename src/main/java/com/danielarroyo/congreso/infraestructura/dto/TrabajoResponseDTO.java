package com.danielarroyo.congreso.infraestructura.dto;

import java.time.LocalDate;

/**
 * DTO RESPONSE - Datos de salida para trabajos académicos
 * Usado para mostrar trabajos en la UI
 * @author Daniel Arroyo
 */
public class TrabajoResponseDTO {
    
    private Integer trabajoId;
    private String titulo;
    private String resumen;
    private String palabrasClave;
    private LocalDate fechaEnvio;
    private String estado;
    private int cantidadPalabrasResumen;
    
    // Constructor vacío
    public TrabajoResponseDTO() {}
    
    // Constructor completo
    public TrabajoResponseDTO(Integer trabajoId, String titulo, String resumen,
                             String palabrasClave, LocalDate fechaEnvio, String estado) {
        this.trabajoId = trabajoId;
        this.titulo = titulo;
        this.resumen = resumen;
        this.palabrasClave = palabrasClave;
        this.fechaEnvio = fechaEnvio;
        this.estado = estado;
        // Calcular palabras del resumen
        this.cantidadPalabrasResumen = (resumen != null && !resumen.trim().isEmpty()) 
            ? resumen.trim().split("\\s+").length : 0;
    }
    
    // Getters y Setters
    public Integer getTrabajoId() { return trabajoId; }
    public void setTrabajoId(Integer trabajoId) { this.trabajoId = trabajoId; }
    
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    
    public String getResumen() { return resumen; }
    public void setResumen(String resumen) { this.resumen = resumen; }
    
    public String getPalabrasClave() { return palabrasClave; }
    public void setPalabrasClave(String palabrasClave) { this.palabrasClave = palabrasClave; }
    
    public LocalDate getFechaEnvio() { return fechaEnvio; }
    public void setFechaEnvio(LocalDate fechaEnvio) { this.fechaEnvio = fechaEnvio; }
    
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    
    public int getCantidadPalabrasResumen() { return cantidadPalabrasResumen; }
    public void setCantidadPalabrasResumen(int cantidadPalabrasResumen) { 
        this.cantidadPalabrasResumen = cantidadPalabrasResumen; 
    }
}