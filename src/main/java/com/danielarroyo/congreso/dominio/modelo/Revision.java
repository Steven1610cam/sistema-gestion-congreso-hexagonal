package com.danielarroyo.congreso.dominio.modelo;

import java.time.LocalDate;

/**
 * DOMINIO - Entidad Revision (Evaluación de trabajos)
 * Representa las evaluaciones del comité de selección
 * @author Daniel Arroyo
 */
public class Revision {
    
    private Integer revisionId;
    private Integer trabajoId;
    private Integer congresistaId; // Miembro del comité que revisa
    private Integer calificacion; // Escala 1-10
    private String comentarios;
    private LocalDate fechaRevision;
    private String decision; // aceptar, rechazar, revisar_nuevamente
    
    // Constructor para nuevas revisiones
    public Revision(Integer trabajoId, Integer congresistaId, Integer calificacion,
                   String comentarios, String decision) {
        this.trabajoId = trabajoId;
        this.congresistaId = congresistaId;
        this.calificacion = calificacion;
        this.comentarios = comentarios;
        this.fechaRevision = LocalDate.now();
        this.decision = decision;
    }
    
    // Constructor completo (desde BD)
    public Revision(Integer revisionId, Integer trabajoId, Integer congresistaId,
                   Integer calificacion, String comentarios, LocalDate fechaRevision,
                   String decision) {
        this.revisionId = revisionId;
        this.trabajoId = trabajoId;
        this.congresistaId = congresistaId;
        this.calificacion = calificacion;
        this.comentarios = comentarios;
        this.fechaRevision = fechaRevision;
        this.decision = decision;
    }
    
    // Lógica de dominio: verificar si es calificación alta
    public boolean esCalificacionAlta() {
        return this.calificacion != null && this.calificacion >= 8;
    }
    
    // Lógica de dominio: verificar si recomienda aceptación
    public boolean recomiendaAceptacion() {
        return "aceptar".equalsIgnoreCase(this.decision);
    }
    
    // Lógica de dominio: verificar si tiene comentarios
    public boolean tieneComentarios() {
        return this.comentarios != null && !this.comentarios.trim().isEmpty();
    }
    
    // Getters
    public Integer getRevisionId() { return revisionId; }
    public Integer getTrabajoId() { return trabajoId; }
    public Integer getCongresistaId() { return congresistaId; }
    public Integer getCalificacion() { return calificacion; }
    public String getComentarios() { return comentarios; }
    public LocalDate getFechaRevision() { return fechaRevision; }
    public String getDecision() { return decision; }
    
    // Setters
    public void setCalificacion(Integer calificacion) { this.calificacion = calificacion; }
    public void setComentarios(String comentarios) { this.comentarios = comentarios; }
    public void setDecision(String decision) { this.decision = decision; }
    
    @Override
    public String toString() {
        return String.format("Revision{id=%d, trabajoId=%d, calificacion=%d, decision='%s'}", 
                           revisionId, trabajoId, calificacion, decision);
    }
}