package com.danielarroyo.congreso.dominio.modelo;

import java.time.LocalDateTime;

/**
 * DOMINIO - Entidad Congresista
 */

public class Congresista {
    
    // Atributos del dominio
    private Integer congresistaId;
    private String nombre;
    private String primerApellido;
    private String institucionAfiliada;
    private String email;
    private String telefonoMovil; // Opcional para SMS
    private LocalDateTime fechaRegistro;
    private boolean esMiembroComite;
    private boolean esOrganizador;
    
    /**
     * Constructor para nuevos congresistas
     */
    public Congresista(String nombre, String primerApellido, String institucionAfiliada,
                      String email, String telefonoMovil, boolean esMiembroComite, 
                      boolean esOrganizador) {
        
        // Validar datos obligatorios
        this.validarDatosObligatorios(nombre, primerApellido, institucionAfiliada, email);
        
        // Asignar valores normalizados
        this.nombre = nombre.trim();
        this.primerApellido = primerApellido.trim();
        this.institucionAfiliada = institucionAfiliada.trim();
        this.email = email.trim().toLowerCase();
        this.telefonoMovil = telefonoMovil != null ? telefonoMovil.trim() : null;
        this.fechaRegistro = LocalDateTime.now();
        this.esMiembroComite = esMiembroComite;
        this.esOrganizador = esOrganizador;
    }
    
    /**
     * Constructor para cargar desde BD
     */
    public Congresista(Integer congresistaId, String nombre, String primerApellido, 
                      String institucionAfiliada, String email, String telefonoMovil,
                      LocalDateTime fechaRegistro, boolean esMiembroComite, boolean esOrganizador) {
        
        this.validarDatosObligatorios(nombre, primerApellido, institucionAfiliada, email);
        
        this.congresistaId = congresistaId;
        this.nombre = nombre.trim();
        this.primerApellido = primerApellido.trim();
        this.institucionAfiliada = institucionAfiliada.trim();
        this.email = email.trim().toLowerCase();
        this.telefonoMovil = telefonoMovil != null ? telefonoMovil.trim() : null;
        this.fechaRegistro = fechaRegistro;
        this.esMiembroComite = esMiembroComite;
        this.esOrganizador = esOrganizador;
    }
    
    /**
     * Reglas de negocio: validar campos obligatorios
     */
    private void validarDatosObligatorios(String nombre, String primerApellido, 
                                        String institucionAfiliada, String email) {
        
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre es obligatorio");
        }
        if (primerApellido == null || primerApellido.trim().isEmpty()) {
            throw new IllegalArgumentException("El primer apellido es obligatorio");
        }
        if (institucionAfiliada == null || institucionAfiliada.trim().isEmpty()) {
            throw new IllegalArgumentException("La institución afiliada es obligatoria");
        }
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("El email es obligatorio");
        }
        if (!email.contains("@") || !email.contains(".")) {
            throw new IllegalArgumentException("El email debe tener un formato válido");
        }
    }
    
    /**
     * Lógica de dominio: puede recibir SMS si tiene teléfono
     */
    public boolean puedeRecibirSMS() {
        return this.telefonoMovil != null && !this.telefonoMovil.trim().isEmpty();
    }
    
    /**
     * Lógica de dominio: es del equipo si es miembro o organizador
     */
    public boolean esParteDelEquipoOrganizador() {
        return this.esMiembroComite || this.esOrganizador;
    }
    
    // GETTERS - Acceso controlado
    public Integer getCongresistaId() { return congresistaId; }
    public String getNombre() { return nombre; }
    public String getPrimerApellido() { return primerApellido; }
    public String getInstitucionAfiliada() { return institucionAfiliada; }
    public String getEmail() { return email; }
    public String getTelefonoMovil() { return telefonoMovil; }
    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
    public boolean isEsMiembroComite() { return esMiembroComite; }
    public boolean isEsOrganizador() { return esOrganizador; }
    
    // SETTERS - Solo para campos modificables
    public void setTelefonoMovil(String telefonoMovil) {
        this.telefonoMovil = telefonoMovil != null ? telefonoMovil.trim() : null;
    }
    
    public void setEsMiembroComite(boolean esMiembroComite) {
        this.esMiembroComite = esMiembroComite;
    }
    
    public void setEsOrganizador(boolean esOrganizador) {
        this.esOrganizador = esOrganizador;
    }
    
    @Override
    public String toString() {
        return String.format("Congresista{id=%d, nombre='%s %s', institucion='%s'}", 
                           congresistaId, nombre, primerApellido, institucionAfiliada);
    }
}