package com.danielarroyo.congreso.infraestructura.adaptadores;

import com.danielarroyo.congreso.casosdeuso.puertos.salida.SalaRepository;
import com.danielarroyo.congreso.dominio.modelo.Sala;
import com.danielarroyo.congreso.infraestructura.conexion.ConexionBD;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * ADAPTADOR - Implementación JDBC 
 * @author Daniel Arroyo
 */
public class SalaRepositoryImpl implements SalaRepository {
    
    @Override
    public Sala guardar(Sala sala) {
        String sql = "INSERT INTO sala (nombre_sala, capacidad, ubicacion, equipamiento) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            ps.setString(1, sala.getNombreSala());
            ps.setInt(2, sala.getCapacidad());
            ps.setString(3, sala.getUbicacion());
            ps.setString(4, sala.getEquipamiento());
            
            ps.executeUpdate();
            
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return new Sala(
                        rs.getInt(1),
                        sala.getNombreSala(),
                        sala.getCapacidad(),
                        sala.getUbicacion(),
                        sala.getEquipamiento()
                    );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar sala", e);
        }
        
        return sala;
    }
    
    @Override
    public Optional<Sala> buscarPorId(Integer id) {
        String sql = "SELECT * FROM sala WHERE sala_id = ?";
        
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapearSala(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar sala", e);
        }
        
        return Optional.empty();
    }
    
    @Override
    public List<Sala> buscarTodas() {
        String sql = "SELECT * FROM sala ORDER BY nombre_sala";
        List<Sala> salas = new ArrayList<>();
        
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                salas.add(mapearSala(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar salas", e);
        }
        
        return salas;
    }
    
    @Override
    public Sala actualizar(Sala sala) {
        String sql = "UPDATE sala SET nombre_sala=?, capacidad=?, ubicacion=?, equipamiento=? WHERE sala_id=?";
        
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, sala.getNombreSala());
            ps.setInt(2, sala.getCapacidad());
            ps.setString(3, sala.getUbicacion());
            ps.setString(4, sala.getEquipamiento());
            ps.setInt(5, sala.getSalaId());
            
            ps.executeUpdate();
            return sala;
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar sala", e);
        }
    }
    
    @Override
    public boolean eliminarPorId(Integer id) {
        String sql = "DELETE FROM sala WHERE sala_id = ?";
        
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar sala", e);
        }
    }
    
    @Override
    public List<Sala> buscarPorCapacidadMinima(Integer capacidadMinima) {
        String sql = "SELECT * FROM sala WHERE capacidad >= ? ORDER BY capacidad DESC";
        List<Sala> salas = new ArrayList<>();
        
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, capacidadMinima);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    salas.add(mapearSala(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar salas por capacidad", e);
        }
        
        return salas;
    }
    
    @Override
    public List<Sala> buscarDisponibles(LocalDate fecha, LocalTime horaInicio, LocalTime horaFin) {
        String sql = "SELECT s.* FROM sala s " +
                    "WHERE s.sala_id NOT IN (" +
                    "    SELECT DISTINCT sala_id FROM sesion " +
                    "    WHERE fecha = ? AND sala_id IS NOT NULL " +
                    "    AND ((hora_inicio <= ? AND hora_final > ?) OR " +
                    "         (hora_inicio < ? AND hora_final >= ?) OR " +
                    "         (hora_inicio >= ? AND hora_final <= ?))" +
                    ") ORDER BY s.nombre_sala";
        List<Sala> salas = new ArrayList<>();
        
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setDate(1, Date.valueOf(fecha));
            ps.setTime(2, Time.valueOf(horaInicio));
            ps.setTime(3, Time.valueOf(horaInicio));
            ps.setTime(4, Time.valueOf(horaFin));
            ps.setTime(5, Time.valueOf(horaFin));
            ps.setTime(6, Time.valueOf(horaInicio));
            ps.setTime(7, Time.valueOf(horaFin));
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    salas.add(mapearSala(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar salas disponibles", e);
        }
        
        return salas;
    }
    
    @Override
    public List<Sala> buscarGrandes() {
        return buscarPorCapacidadMinima(100);
    }
    
    @Override
    public List<Object[]> obtenerSalasConSesiones() {
        String sql = "SELECT s.sala_id, s.nombre_sala, COUNT(ses.sesion_id) as cantidad_sesiones " +
                    "FROM sala s " +
                    "LEFT JOIN sesion ses ON s.sala_id = ses.sala_id " +
                    "GROUP BY s.sala_id, s.nombre_sala " +
                    "ORDER BY cantidad_sesiones DESC";
        List<Object[]> resultados = new ArrayList<>();
        
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Object[] fila = {
                    rs.getInt("sala_id"),
                    rs.getString("nombre_sala"),
                    rs.getInt("cantidad_sesiones")
                };
                resultados.add(fila);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener salas con sesiones", e);
        }
        
        return resultados;
    }
    
    // Mapper corregido: ResultSet → Entidad
    private Sala mapearSala(ResultSet rs) throws SQLException {
        return new Sala(
            rs.getInt("sala_id"),
            rs.getString("nombre_sala"),
            rs.getInt("capacidad"),
            rs.getString("ubicacion"),
            rs.getString("equipamiento")
        );
    }
}