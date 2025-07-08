package com.danielarroyo.congreso.infraestructura.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * INFRAESTRUCTURA - Conexión a MySQL con JDBC
 */

public class ConexionBD {
    
    // Configuración de BD - usar variables de entorno en producción
    private static final String URL = "jdbc:mysql://localhost:3306/gestion_congreso?zeroDateTimeBehavior=CONVERT_TO_NULL";
    private static final String USUARIO = "root";
    private static final String PASSWORD = "flee";
    
    /**
     * Obtener conexión a MySQL
     * @return Conexión activa o lanza excepción
     */
    public static Connection getConexion() {
        try {
            Connection conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD);
            return conexion;
        } catch (SQLException e) {
            throw new RuntimeException("Error al conectar con la base de datos: " + e.getMessage(), e);
        }
    }
    
    /**
     * Verificar si la conexión está disponible
     * @return true si puede conectar
     */
    public static boolean verificarConexion() {
        try (Connection conn = getConexion()) {
            return conn != null && !conn.isClosed();
        } catch (Exception e) {
            return false;
        }
    }

    public static Object getInstancia() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}