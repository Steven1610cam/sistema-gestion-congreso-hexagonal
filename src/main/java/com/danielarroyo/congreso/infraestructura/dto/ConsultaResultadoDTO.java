package com.danielarroyo.congreso.infraestructura.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

/**
 * DTO ESPECIAL - Resultado de consultas complejas
 * DTO genérico para las 43 consultas del sistema
 * @author Daniel Arroyo
 */
public class ConsultaResultadoDTO {
    
    // Campos para congresistas
    private Integer congresistaId;
    private String nombreCongresista;
    private String apellidoCongresista;
    private String institucionCongresista;
    private String emailCongresista;
    private String telefonoCongresista;
    
    // Campos para trabajos
    private Integer trabajoId;
    private String tituloTrabajo;
    private String resumenTrabajo;
    private String estadoTrabajo;
    private LocalDate fechaEnvioTrabajo;
    
    // Campos para sesiones
    private Integer sesionId;
    private String tituloSesion;
    private LocalDate fechaSesion;
    private LocalTime horaInicioSesion;
    private LocalTime horaFinSesion;
    
    // Campos para salas
    private Integer salaId;
    private String nombreSala;
    private Integer capacidadSala;
    
    // Campos para estadísticas/conteos
    private Long cantidad;
    private String descripcion;
    private Map<String, Object> datosExtra;
    
    // Constructor vacío
    public ConsultaResultadoDTO() {}
    
    // Constructor para consultas de congresistas
    public ConsultaResultadoDTO(Integer congresistaId, String nombreCongresista, 
                               String apellidoCongresista, String institucionCongresista,
                               String emailCongresista, String telefonoCongresista) {
        this.congresistaId = congresistaId;
        this.nombreCongresista = nombreCongresista;
        this.apellidoCongresista = apellidoCongresista;
        this.institucionCongresista = institucionCongresista;
        this.emailCongresista = emailCongresista;
        this.telefonoCongresista = telefonoCongresista;
    }
    
    // Constructor para consultas de trabajos
    public ConsultaResultadoDTO(Integer trabajoId, String tituloTrabajo, String estadoTrabajo,
                               LocalDate fechaEnvioTrabajo, String nombreAutor) {
        this.trabajoId = trabajoId;
        this.tituloTrabajo = tituloTrabajo;
        this.estadoTrabajo = estadoTrabajo;
        this.fechaEnvioTrabajo = fechaEnvioTrabajo;
        this.nombreCongresista = nombreAutor;
    }
    
    // Constructor para consultas de estadísticas
    public ConsultaResultadoDTO(String descripcion, Long cantidad) {
        this.descripcion = descripcion;
        this.cantidad = cantidad;
    }
    
    // Getters y Setters (todos los campos)
    public Integer getCongresistaId() { return congresistaId; }
    public void setCongresistaId(Integer congresistaId) { this.congresistaId = congresistaId; }
    
    public String getNombreCongresista() { return nombreCongresista; }
    public void setNombreCongresista(String nombreCongresista) { this.nombreCongresista = nombreCongresista; }
    
    public String getApellidoCongresista() { return apellidoCongresista; }
    public void setApellidoCongresista(String apellidoCongresista) { this.apellidoCongresista = apellidoCongresista; }
    
    public String getInstitucionCongresista() { return institucionCongresista; }
    public void setInstitucionCongresista(String institucionCongresista) { this.institucionCongresista = institucionCongresista; }
    
    public String getEmailCongresista() { return emailCongresista; }
    public void setEmailCongresista(String emailCongresista) { this.emailCongresista = emailCongresista; }
    
    public String getTelefonoCongresista() { return telefonoCongresista; }
    public void setTelefonoCongresista(String telefonoCongresista) { this.telefonoCongresista = telefonoCongresista; }
    
    public Integer getTrabajoId() { return trabajoId; }
    public void setTrabajoId(Integer trabajoId) { this.trabajoId = trabajoId; }
    
    public String getTituloTrabajo() { return tituloTrabajo; }
    public void setTituloTrabajo(String tituloTrabajo) { this.tituloTrabajo = tituloTrabajo; }
    
    public String getResumenTrabajo() { return resumenTrabajo; }
    public void setResumenTrabajo(String resumenTrabajo) { this.resumenTrabajo = resumenTrabajo; }
    
    public String getEstadoTrabajo() { return estadoTrabajo; }
    public void setEstadoTrabajo(String estadoTrabajo) { this.estadoTrabajo = estadoTrabajo; }
    
    public LocalDate getFechaEnvioTrabajo() { return fechaEnvioTrabajo; }
    public void setFechaEnvioTrabajo(LocalDate fechaEnvioTrabajo) { this.fechaEnvioTrabajo = fechaEnvioTrabajo; }
    
    public Integer getSesionId() { return sesionId; }
    public void setSesionId(Integer sesionId) { this.sesionId = sesionId; }
    
    public String getTituloSesion() { return tituloSesion; }
    public void setTituloSesion(String tituloSesion) { this.tituloSesion = tituloSesion; }
    
    public LocalDate getFechaSesion() { return fechaSesion; }
    public void setFechaSesion(LocalDate fechaSesion) { this.fechaSesion = fechaSesion; }
    
    public LocalTime getHoraInicioSesion() { return horaInicioSesion; }
    public void setHoraInicioSesion(LocalTime horaInicioSesion) { this.horaInicioSesion = horaInicioSesion; }
    
    public LocalTime getHoraFinSesion() { return horaFinSesion; }
    public void setHoraFinSesion(LocalTime horaFinSesion) { this.horaFinSesion = horaFinSesion; }
    
    public Integer getSalaId() { return salaId; }
    public void setSalaId(Integer salaId) { this.salaId = salaId; }
    
    public String getNombreSala() { return nombreSala; }
    public void setNombreSala(String nombreSala) { this.nombreSala = nombreSala; }
    
    public Integer getCapacidadSala() { return capacidadSala; }
    public void setCapacidadSala(Integer capacidadSala) { this.capacidadSala = capacidadSala; }
    
    public Long getCantidad() { return cantidad; }
    public void setCantidad(Long cantidad) { this.cantidad = cantidad; }
    
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    
    public Map<String, Object> getDatosExtra() { return datosExtra; }
    public void setDatosExtra(Map<String, Object> datosExtra) { this.datosExtra = datosExtra; }
}