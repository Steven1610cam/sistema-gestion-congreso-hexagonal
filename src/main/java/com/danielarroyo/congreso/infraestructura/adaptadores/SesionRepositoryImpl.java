package com.danielarroyo.congreso.infraestructura.adaptadores;

import com.danielarroyo.congreso.casosdeuso.puertos.salida.SesionRepository;
import com.danielarroyo.congreso.dominio.modelo.Sesion;
import com.danielarroyo.congreso.infraestructura.conexion.ConexionBD;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * ADAPTADOR - Implementación JDBC 
 * @author Daniel Arroyo
 */
public class SesionRepositoryImpl implements SesionRepository {
    
    @Override
    public Sesion guardar(Sesion sesion) {
        String sql = "INSERT INTO sesion (nombre_sesion, fecha, hora_inicio, hora_final, estado, sala_id) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            ps.setString(1, sesion.getNombreSesion());
            ps.setDate(2, Date.valueOf(sesion.getFecha()));
            ps.setTime(3, Time.valueOf(sesion.getHoraInicio()));
            ps.setTime(4, Time.valueOf(sesion.getHoraFinal()));
            ps.setString(5, sesion.getEstado());
            ps.setObject(6, sesion.getSalaId());
            
            ps.executeUpdate();
            
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return new Sesion(
                        rs.getInt(1),
                        sesion.getNombreSesion(),
                        sesion.getFecha(),
                        sesion.getHoraInicio(),
                        sesion.getHoraFinal(),
                        sesion.getEstado(),
                        sesion.getSalaId()
                    );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar sesión", e);
        }
        
        return sesion;
    }
    
    @Override
    public Optional<Sesion> buscarPorId(Integer id) {
        String sql = "SELECT * FROM sesion WHERE sesion_id = ?";
        
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapearSesion(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar sesión", e);
        }
        
        return Optional.empty();
    }
    
    @Override
    public List<Sesion> buscarTodas() {
        String sql = "SELECT * FROM sesion ORDER BY fecha, hora_inicio";
        List<Sesion> sesiones = new ArrayList<>();
        
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                sesiones.add(mapearSesion(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar sesiones", e);
        }
        
        return sesiones;
    }
    
    @Override
    public Sesion actualizar(Sesion sesion) {
        String sql = "UPDATE sesion SET nombre_sesion=?, fecha=?, hora_inicio=?, hora_final=?, " +
                    "estado=?, sala_id=? WHERE sesion_id=?";
        
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, sesion.getNombreSesion());
            ps.setDate(2, Date.valueOf(sesion.getFecha()));
            ps.setTime(3, Time.valueOf(sesion.getHoraInicio()));
            ps.setTime(4, Time.valueOf(sesion.getHoraFinal()));
            ps.setString(5, sesion.getEstado());
            ps.setObject(6, sesion.getSalaId());
            ps.setInt(7, sesion.getSesionId());
            
            ps.executeUpdate();
            return sesion;
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar sesión", e);
        }
    }
    
    @Override
    public boolean eliminarPorId(Integer id) {
        String sql = "DELETE FROM sesion WHERE sesion_id = ?";
        
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar sesión", e);
        }
    }
    
    @Override
    public List<Sesion> buscarPorFecha(LocalDate fecha) {
        String sql = "SELECT * FROM sesion WHERE fecha = ? ORDER BY hora_inicio";
        List<Sesion> sesiones = new ArrayList<>();
        
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setDate(1, Date.valueOf(fecha));
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    sesiones.add(mapearSesion(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar sesiones por fecha", e);
        }
        
        return sesiones;
    }
    
    @Override
    public List<Sesion> buscarPorSala(Integer salaId) {
        String sql = "SELECT * FROM sesion WHERE sala_id = ? ORDER BY fecha, hora_inicio";
        List<Sesion> sesiones = new ArrayList<>();
        
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, salaId);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    sesiones.add(mapearSesion(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar sesiones por sala", e);
        }
        
        return sesiones;
    }
    
    @Override
    public List<Sesion> buscarSinChairman() {
        // CONSULTA CORREGIDA - Usar tabla chairman separada
        String sql = "SELECT s.* FROM sesion s " +
                    "LEFT JOIN chairman c ON s.sesion_id = c.sesion_id " +
                    "WHERE c.chairman_id IS NULL " +
                    "ORDER BY s.fecha, s.hora_inicio";
        List<Sesion> sesiones = new ArrayList<>();
        
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                sesiones.add(mapearSesion(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar sesiones sin chairman", e);
        }
        
        return sesiones;
    }
    
    @Override
    public List<Sesion> buscarPorChairman(Integer chairmanId) {
        // CONSULTA CORREGIDA - Usar tabla chairman
        String sql = "SELECT s.* FROM sesion s " +
                    "INNER JOIN chairman c ON s.sesion_id = c.sesion_id " +
                    "WHERE c.congresista_id = ? ORDER BY s.fecha, s.hora_inicio";
        List<Sesion> sesiones = new ArrayList<>();
        
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, chairmanId);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    sesiones.add(mapearSesion(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar sesiones por chairman", e);
        }
        
        return sesiones;
    }
    
    @Override
    public List<Sesion> buscarSinSalaAsignada() {
        String sql = "SELECT * FROM sesion WHERE sala_id IS NULL ORDER BY fecha, hora_inicio";
        List<Sesion> sesiones = new ArrayList<>();
        
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                sesiones.add(mapearSesion(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar sesiones sin sala", e);
        }
        
        return sesiones;
    }
    
    @Override
    public List<Sesion> buscarConMenosDeTresTrabajos() {
        String sql = "SELECT s.* FROM sesion s " +
                    "LEFT JOIN presentacion p ON s.sesion_id = p.sesion_id " +
                    "GROUP BY s.sesion_id " +
                    "HAVING COUNT(p.presentacion_id) < 3 " +
                    "ORDER BY s.fecha, s.hora_inicio";
        List<Sesion> sesiones = new ArrayList<>();
        
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                sesiones.add(mapearSesion(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar sesiones con menos de 3 trabajos", e);
        }
        
        return sesiones;
    }
    
    @Override
    public List<Sesion> buscarOrdenadasPorFechaHora() {
        return buscarTodas();
    }
    
    @Override
    public List<Sesion> buscarPorFechaYSala(LocalDate fecha, Integer salaId) {
        String sql = "SELECT * FROM sesion WHERE fecha = ? AND sala_id = ? ORDER BY hora_inicio";
        List<Sesion> sesiones = new ArrayList<>();
        
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setDate(1, Date.valueOf(fecha));
            ps.setInt(2, salaId);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    sesiones.add(mapearSesion(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar sesiones por fecha y sala", e);
        }
        
        return sesiones;
    }
    
    @Override
    public List<Object[]> obtenerSesionesConMasTrabajos() {
        String sql = "SELECT s.sesion_id, s.nombre_sesion, COUNT(p.presentacion_id) as cantidad_trabajos " +
                    "FROM sesion s " +
                    "LEFT JOIN presentacion p ON s.sesion_id = p.sesion_id " +
                    "GROUP BY s.sesion_id, s.nombre_sesion " +
                    "ORDER BY cantidad_trabajos DESC";
        List<Object[]> resultados = new ArrayList<>();
        
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Object[] fila = {
                    rs.getInt("sesion_id"),
                    rs.getString("nombre_sesion"),
                    rs.getInt("cantidad_trabajos")
                };
                resultados.add(fila);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener sesiones con más trabajos", e);
        }
        
        return resultados;
    }
    
    @Override
    public List<Object[]> obtenerSesionesPorDia() {
        String sql = "SELECT fecha, COUNT(*) as cantidad_sesiones " +
                    "FROM sesion " +
                    "GROUP BY fecha " +
                    "ORDER BY fecha";
        List<Object[]> resultados = new ArrayList<>();
        
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Object[] fila = {
                    rs.getDate("fecha").toLocalDate(),
                    rs.getInt("cantidad_sesiones")
                };
                resultados.add(fila);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener sesiones por día", e);
        }
        
        return resultados;
    }
    
    // Mapper corregido: ResultSet → Entidad
    private Sesion mapearSesion(ResultSet rs) throws SQLException {
        return new Sesion(
            rs.getInt("sesion_id"),
            rs.getString("nombre_sesion"),
            rs.getDate("fecha").toLocalDate(),
            rs.getTime("hora_inicio").toLocalTime(),
            rs.getTime("hora_final").toLocalTime(),
            rs.getString("estado"),
            (Integer) rs.getObject("sala_id")
        );
    }
}