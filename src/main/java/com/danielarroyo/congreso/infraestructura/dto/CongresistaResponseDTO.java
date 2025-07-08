package com.danielarroyo.congreso.infraestructura.dto;

import java.time.LocalDateTime;

/**
 * DTO RESPONSE - Datos de salida para congresistas
 * Usado para transferir datos desde casos de uso hacia la UI
 * @author Daniel Arroyo
 */
public class CongresistaResponseDTO {
    
    // Datos completos incluyendo ID y fecha
    private Integer congresistaId;
    private String nombre;
    private String primerApellido;
    private String institucionAfiliada;
    private String email;
    private String telefonoMovil;
    private LocalDateTime fechaRegistro;
    private boolean esMiembroComite;
    private boolean esOrganizador;
    
    // Constructor vacío
    public CongresistaResponseDTO() {}
    
    // Constructor completo
    public CongresistaResponseDTO(Integer congresistaId, String nombre, String primerApellido,
                                 String institucionAfiliada, String email, String telefonoMovil,
                                 LocalDateTime fechaRegistro, boolean esMiembroComite, boolean esOrganizador) {
        this.congresistaId = congresistaId;
        this.nombre = nombre;
        this.primerApellido = primerApellido;
        this.institucionAfiliada = institucionAfiliada;
        this.email = email;
        this.telefonoMovil = telefonoMovil;
        this.fechaRegistro = fechaRegistro;
        this.esMiembroComite = esMiembroComite;
        this.esOrganizador = esOrganizador;
    }
    
    // Getters y Setters
    public Integer getCongresistaId() { return congresistaId; }
    public void setCongresistaId(Integer congresistaId) { this.congresistaId = congresistaId; }
    
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
    
    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDateTime fechaRegistro) { this.fechaRegistro = fechaRegistro; }
    
    public boolean isEsMiembroComite() { return esMiembroComite; }
    public void setEsMiembroComite(boolean esMiembroComite) { this.esMiembroComite = esMiembroComite; }
    
    public boolean isEsOrganizador() { return esOrganizador; }
    public void setEsOrganizador(boolean esOrganizador) { this.esOrganizador = esOrganizador; }

    public Object getId() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public Object getSegundoApellido() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public Object getTelefono() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public Object getPais() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public boolean isPresentaTrabajo() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public boolean isAsiste() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public Object getCiudad() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}