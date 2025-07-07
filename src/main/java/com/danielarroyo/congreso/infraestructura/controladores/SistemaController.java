package com.danielarroyo.congreso.infraestructura.controladores;

import com.danielarroyo.congreso.casosdeuso.puertos.entrada.*;
import com.danielarroyo.congreso.casosdeuso.servicios.*;
import com.danielarroyo.congreso.infraestructura.adaptadores.*;

/**
 * CONTROLADOR PRINCIPAL - Coordinador de todos los casos de uso
 * Inyecta dependencias y coordina servicios
 * @author Daniel Arroyo
 */
public class SistemaController {
    
    // Casos de uso (puertos de entrada)
    private final GestionarCongresistaUseCase gestionarCongresistaUseCase;
    private final GestionarTrabajoUseCase gestionarTrabajoUseCase;
    private final GestionarSesionUseCase gestionarSesionUseCase;
    private final GestionarSalaUseCase gestionarSalaUseCase;
    
    // Constructor con inyección de dependencias manual
    public SistemaController() {
        // Crear repositories (adaptadores)
        CongresistaRepositoryImpl congresistaRepo = new CongresistaRepositoryImpl();
        TrabajoRepositoryImpl trabajoRepo = new TrabajoRepositoryImpl();
        SesionRepositoryImpl sesionRepo = new SesionRepositoryImpl();
        SalaRepositoryImpl salaRepo = new SalaRepositoryImpl();
        
        // Crear servicios (casos de uso)
        this.gestionarCongresistaUseCase = new CongresistaService(congresistaRepo);
        this.gestionarTrabajoUseCase = new TrabajoService(trabajoRepo);
        this.gestionarSesionUseCase = new SesionService(sesionRepo);
        this.gestionarSalaUseCase = new SalaService(salaRepo);
    }
    
    // Getters para acceder a los casos de uso
    public GestionarCongresistaUseCase getCongresistas() {
        return gestionarCongresistaUseCase;
    }
    
    public GestionarTrabajoUseCase getTrabajos() {
        return gestionarTrabajoUseCase;
    }
    
    public GestionarSesionUseCase getSesiones() {
        return gestionarSesionUseCase;
    }
    
    public GestionarSalaUseCase getSalas() {
        return gestionarSalaUseCase;
    }
}