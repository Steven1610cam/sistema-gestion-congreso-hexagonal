package com.danielarroyo.congreso.dominio.modelo;

import java.time.LocalDate;

/**
 * DOMINIO - Entidad Trabajo (Paper académico)
  @author Daniel Arroyo
 */

public class Trabajo {
    
    private Integer trabajoId;
    private String titulo;
    private String resumen;
    private String palabrasClave;
    private LocalDate fechaEnvio;
    private String estado; // recibido, en_revision, aceptado, rechazado, retirado
    
    // Constructor para nuevos trabajos
    public Trabajo(String titulo, String resumen, String palabrasClave, String estado) {
        this.titulo = titulo;
        this.resumen = resumen;
        this.palabrasClave = palabrasClave;
        this.fechaEnvio = LocalDate.now();
        this.estado = estado;
    }
    
    // Constructor para trabajos existentes (desde BD)
    public Trabajo(Integer trabajoId, String titulo, String resumen, String palabrasClave,
                   LocalDate fechaEnvio, String estado) {
        this.trabajoId = trabajoId;
        this.titulo = titulo;
        this.resumen = resumen;
        this.palabrasClave = palabrasClave;
        this.fechaEnvio = fechaEnvio;
        this.estado = estado;
    }
    
    // Lógica de dominio: verificar si puede ser presentado
    public boolean puedeSerPresentado() {
        return "aceptado".equalsIgnoreCase(this.estado);
    }
    
    // Lógica de dominio: verificar si está en proceso de revisión
    public boolean estaEnRevision() {
        return "en_revision".equalsIgnoreCase(this.estado) || 
               "recibido".equalsIgnoreCase(this.estado);
    }
    
    // Lógica de dominio: contar palabras del resumen
    public int contarPalabrasResumen() {
        if (resumen == null || resumen.trim().isEmpty()) {
            return 0;
        }
        return resumen.trim().split("\\s+").length;
    }
    
    // Getters
    public Integer getTrabajoId() { return trabajoId; }
    public String getTitulo() { return titulo; }
    public String getResumen() { return resumen; }
    public String getPalabrasClave() { return palabrasClave; }
    public LocalDate getFechaEnvio() { return fechaEnvio; }
    public String getEstado() { return estado; }
    
    // Setters para campos modificables
    public void setEstado(String estado) { this.estado = estado; }
    public void setPalabrasClave(String palabrasClave) { this.palabrasClave = palabrasClave; }
    
    @Override
    public String toString() {
        return String.format("Trabajo{id=%d, titulo='%s', estado='%s'}", 
                           trabajoId, titulo, estado);
    }
}

