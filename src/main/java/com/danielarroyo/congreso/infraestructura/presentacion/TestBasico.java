package com.danielarroyo.congreso.infraestructura.presentacion;

import com.danielarroyo.congreso.infraestructura.adaptadores.*;
import com.danielarroyo.congreso.infraestructura.conexion.ConexionBD;
import com.danielarroyo.congreso.dominio.modelo.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * PRUEBA BÁSICA - Verificar que toda la arquitectura funciona
 * Test rápido de repositories y conexión
 * @author Daniel Arroyo
 */
public class TestBasico {
    
    public static void main(String[] args) {
        System.out.println("=== PRUEBA BÁSICA DEL SISTEMA ===\n");
        
        // 1. Probar conexión
        probarConexion();
        
        // 2. Probar CongresistaRepository (ya funciona)
        probarCongresistas();
        
        // 3. Probar TrabajoRepository
        probarTrabajos();
        
        // 4. Probar SesionRepository
        probarSesiones();
        
        // 5. Probar SalaRepository
        probarSalas();
        
        System.out.println("\n=== PRUEBA COMPLETADA ===");
    }
    
    private static void probarConexion() {
        System.out.println("🔍 1. PROBANDO CONEXIÓN A BD...");
        try {
            boolean conectado = ConexionBD.verificarConexion();
            if (conectado) {
                System.out.println("✅ Conexión exitosa a la base de datos");
            } else {
                System.out.println("❌ Error de conexión");
                return;
            }
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
            return;
        }
        System.out.println();
    }
    
    private static void probarCongresistas() {
        System.out.println("👥 2. PROBANDO CONGRESISTAS...");
        try {
            CongresistaRepositoryImpl repo = new CongresistaRepositoryImpl();
            
            // Contar congresistas existentes
            List<com.danielarroyo.congreso.dominio.modelo.Congresista> todos = repo.buscarTodos();
            System.out.println("📊 Total de congresistas: " + todos.size());
            
            // Mostrar algunos ejemplos
            if (!todos.isEmpty()) {
                System.out.println("📋 Primeros 3 congresistas:");
                for (int i = 0; i < Math.min(3, todos.size()); i++) {
                    var c = todos.get(i);
                    System.out.println("   - " + c.getNombre() + " " + c.getPrimerApellido() + 
                                     " (" + c.getInstitucionAfiliada() + ")");
                }
            }
            
            // Probar consulta específica
            List<com.danielarroyo.congreso.dominio.modelo.Congresista> conTelefono = repo.buscarConTelefonoMovil();
            System.out.println("📱 Congresistas con teléfono: " + conTelefono.size());
            
            System.out.println("✅ CongresistaRepository funcionando");
            
        } catch (Exception e) {
            System.out.println("❌ Error en CongresistaRepository: " + e.getMessage());
        }
        System.out.println();
    }
    
    private static void probarTrabajos() {
        System.out.println("📋 3. PROBANDO TRABAJOS...");
        try {
            TrabajoRepositoryImpl repo = new TrabajoRepositoryImpl();
            
            // Contar trabajos existentes
            List<Trabajo> todos = repo.buscarTodos();
            System.out.println("📊 Total de trabajos: " + todos.size());
            
            // Mostrar algunos ejemplos
            if (!todos.isEmpty()) {
                System.out.println("📄 Primeros 3 trabajos:");
                for (int i = 0; i < Math.min(3, todos.size()); i++) {
                    var t = todos.get(i);
                    System.out.println("   - " + t.getTitulo() + " (Estado: " + t.getEstado() + ")");
                }
            } else {
                System.out.println("ℹ️  No hay trabajos en la BD (normal si es BD nueva)");
            }
            
            // Probar creación de trabajo (opcional)
            // Trabajo nuevo = new Trabajo("Prueba Test", "Resumen de prueba", "test,prueba", "recibido");
            // repo.guardar(nuevo);
            // System.out.println("✅ Trabajo de prueba guardado");
            
            System.out.println("✅ TrabajoRepository funcionando");
            
        } catch (Exception e) {
            System.out.println("❌ Error en TrabajoRepository: " + e.getMessage());
        }
        System.out.println();
    }
    
    private static void probarSesiones() {
    System.out.println("📅 4. PROBANDO SESIONES...");
    try {
        SesionRepositoryImpl repo = new SesionRepositoryImpl();
        
        // Contar sesiones existentes
        List<Sesion> todas = repo.buscarTodas();
        System.out.println("📊 Total de sesiones: " + todas.size());
        
        // Mostrar algunos ejemplos
        if (!todas.isEmpty()) {
            System.out.println("🗓️ Primeras 3 sesiones:");
            for (int i = 0; i < Math.min(3, todas.size()); i++) {
                var s = todas.get(i);
                System.out.println("   - " + s.getNombreSesion() + " (" + s.getFecha() + 
                                 " " + s.getHoraInicio() + " - " + s.getHoraFinal() + 
                                 ", Estado: " + s.getEstado() + ")");
            }
        } else {
            System.out.println("ℹ️  No hay sesiones en la BD (normal si es BD nueva)");
        }
        
        // Probar consultas específicas
        List<Sesion> sinChairman = repo.buscarSinChairman();
        System.out.println("👤 Sesiones sin chairman: " + sinChairman.size());
        
        List<Sesion> sinSala = repo.buscarSinSalaAsignada();
        System.out.println("🏛️ Sesiones sin sala: " + sinSala.size());
        
        System.out.println("✅ SesionRepository funcionando");
        
    } catch (Exception e) {
        System.out.println("❌ Error en SesionRepository: " + e.getMessage());
        e.printStackTrace();
    }
    System.out.println();
}

private static void probarSalas() {
    System.out.println("🏛️ 5. PROBANDO SALAS...");
    try {
        SalaRepositoryImpl repo = new SalaRepositoryImpl();
        
        // Contar salas existentes
        List<Sala> todas = repo.buscarTodas();
        System.out.println("📊 Total de salas: " + todas.size());
        
        // Mostrar algunos ejemplos
        if (!todas.isEmpty()) {
            System.out.println("🏢 Primeras 3 salas:");
            for (int i = 0; i < Math.min(3, todas.size()); i++) {
                var s = todas.get(i);
                System.out.println("   - " + s.getNombreSala() + " (Capacidad: " + 
                                 s.getCapacidad() + ", Ubicación: " + s.getUbicacion() + 
                                 ", Equipamiento: " + s.getEquipamiento() + ")");
            }
        } else {
            System.out.println("ℹ️  No hay salas en la BD (normal si es BD nueva)");
        }
        
        // Probar consultas específicas
        List<Sala> grandes = repo.buscarGrandes();
        System.out.println("🏟️ Salas grandes (>100): " + grandes.size());
        
        System.out.println("✅ SalaRepository funcionando");
        
    } catch (Exception e) {
        System.out.println("❌ Error en SalaRepository: " + e.getMessage());
        e.printStackTrace();
    }
    System.out.println();
}   
}