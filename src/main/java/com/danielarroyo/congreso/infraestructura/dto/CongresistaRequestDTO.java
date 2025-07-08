package com.danielarroyo.congreso.infraestructura.dto;

/**
 * DTO REQUEST - Datos de entrada para congresistas
 */

public class CongresistaRequestDTO {
    
    // Datos de entrada del formulario
    private String nombre;
    private String primerApellido;
    private String institucionAfiliada;
    private String email;
    private String telefonoMovil;
    private boolean esMiembroComite;
    private boolean esOrganizador;
    
    // Constructor vacío para frameworks
    public CongresistaRequestDTO() {}
    
    // Constructor completo
    public CongresistaRequestDTO(String nombre, String primerApellido, String institucionAfiliada,
                                String email, String telefonoMovil, boolean esMiembroComite, 
                                boolean esOrganizador) {
        this.nombre = nombre;
        this.primerApellido = primerApellido;
        this.institucionAfiliada = institucionAfiliada;
        this.email = email;
        this.telefonoMovil = telefonoMovil;
        this.esMiembroComite = esMiembroComite;
        this.esOrganizador = esOrganizador;
    }
    
    // Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getPrimerApellido() { return primerApellido; }
    public void setPrimerApellido(String primerApellido) { this.primerApellido = primerApellido; }
    
    public String getInstitucionAfiliada() { return institucionAfiliada; }
    public void setInstitucionAfiliada(String institucionAfiliada) { this.institucionAfiliada = institucionAfiliada; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getTelefonoMovil() { return telefonoMovil; }
    public void setTelefonoMovil(String telefonoMovil) { this.telefonoMovil = telefonoMovil; }
    
    public boolean isEsMiembroComite() { return esMiembroComite; }
    public void setEsMiembroComite(boolean esMiembroComite) { this.esMiembroComite = esMiembroComite; }
    
    public boolean isEsOrganizador() { return esOrganizador; }
    public void setEsOrganizador(boolean esOrganizador) { this.esOrganizador = esOrganizador; }

    public void setPresentaTrabajo(boolean selected) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void setAsiste(boolean selected) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}