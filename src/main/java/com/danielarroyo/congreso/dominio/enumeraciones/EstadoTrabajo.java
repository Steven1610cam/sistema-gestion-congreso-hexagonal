package com.danielarroyo.congreso.dominio.enumeraciones;

/**
 * DOMINIO - Estados posibles de un trabajo 
 * @author Daniel Arroyo
 */
public enum EstadoTrabajo {
    
    // Estados del proceso de revisión
    RECIBIDO("Recibido", "Trabajo enviado, pendiente de revisión"),
    EN_REVISION("En Revisión", "Siendo evaluado por el comité"),
    ACEPTADO("Aceptado", "Aprobado para presentación"),
    RECHAZADO("Rechazado", "No cumple criterios de calidad"),
    RETIRADO("Retirado", "Retirado por el autor");
    
    // Atributos de cada estado
    private final String nombre;
    private final String descripcion;
    
    /**
     * Constructor del enum
     */
    EstadoTrabajo(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }
    
    // Getters
    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }
    
    /**
     * Lógica de dominio: verificar si puede ser presentado
     */
    public boolean puedeSerPresentado() {
        return this == ACEPTADO;
    }
    
    /**
     * Lógica de dominio: verificar si está en proceso
     */
    public boolean estaEnProceso() {
        return this == RECIBIDO || this == EN_REVISION;
    }
    
    @Override
    public String toString() {
        return nombre;
    }
}