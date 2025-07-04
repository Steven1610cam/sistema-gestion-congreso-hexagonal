package com.danielarroyo.congreso.dominio.enumeraciones;

/**
 * DOMINIO - Tipos de miembros en el congreso
 * Define los roles que puede tener un congresista
 * @author Daniel Arroyo
 */
public enum TipoMiembro {
    
    // Tipos de participación
    ASISTENTE("Asistente", "Participante regular del congreso"),
    PONENTE("Ponente", "Presenta trabajos en sesiones"),
    MIEMBRO_COMITE("Miembro Comité", "Revisa y evalúa trabajos"),
    ORGANIZADOR("Organizador", "Organiza y coordina el evento"),
    CHAIRMAN("Chairman", "Modera sesiones del congreso");
    
    // Atributos del tipo
    private final String nombre;
    private final String descripcion;
    
    /**
     * Constructor del enum
     */
    TipoMiembro(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }
    
    // Getters
    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }
    
    /**
     * Lógica de dominio: verificar si tiene privilegios administrativos
     */
    public boolean tienePrivilegiosAdmin() {
        return this == ORGANIZADOR || this == MIEMBRO_COMITE;
    }
    
    /**
     * Lógica de dominio: verificar si puede evaluar trabajos
     */
    public boolean puedeEvaluarTrabajos() {
        return this == MIEMBRO_COMITE;
    }
    
    @Override
    public String toString() {
        return nombre;
    }
}