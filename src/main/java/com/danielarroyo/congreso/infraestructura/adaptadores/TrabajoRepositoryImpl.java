package com.danielarroyo.congreso.infraestructura.adaptadores;

import com.danielarroyo.congreso.casosdeuso.puertos.salida.TrabajoRepository;
import com.danielarroyo.congreso.dominio.modelo.Trabajo;
import com.danielarroyo.congreso.infraestructura.conexion.ConexionBD;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * ADAPTADOR - Implementación JDBC para persistencia de trabajos
 * @author Daniel Arroyo
 */
public class TrabajoRepositoryImpl implements TrabajoRepository {
    
    @Override
    public Trabajo guardar(Trabajo trabajo) {
        String sql = "INSERT INTO trabajo (titulo, resumen, palabras_clave, fecha_envio, estado) " +
                    "VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            ps.setString(1, trabajo.getTitulo());
            ps.setString(2, trabajo.getResumen());
            ps.setString(3, trabajo.getPalabrasClave());
            ps.setDate(4, Date.valueOf(trabajo.getFechaEnvio()));
            ps.setString(5, trabajo.getEstado());
            
            ps.executeUpdate();
            
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return new Trabajo(
                        rs.getInt(1),
                        trabajo.getTitulo(),
                        trabajo.getResumen(),
                        trabajo.getPalabrasClave(),
                        trabajo.getFechaEnvio(),
                        trabajo.getEstado()
                    );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar trabajo", e);
        }
        
        return trabajo;
    }
    
    @Override
    public Optional<Trabajo> buscarPorId(Integer id) {
        String sql = "SELECT * FROM trabajo WHERE trabajo_id = ?";
        
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapearTrabajo(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar trabajo", e);
        }
        
        return Optional.empty();
    }
    
    @Override
    public List<Trabajo> buscarTodos() {
        String sql = "SELECT * FROM trabajo ORDER BY fecha_envio DESC";
        List<Trabajo> trabajos = new ArrayList<>();
        
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                trabajos.add(mapearTrabajo(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar trabajos", e);
        }
        
        return trabajos;
    }
    
    @Override
    public Trabajo actualizar(Trabajo trabajo) {
        String sql = "UPDATE trabajo SET titulo=?, resumen=?, palabras_clave=?, estado=? " +
                    "WHERE trabajo_id=?";
        
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, trabajo.getTitulo());
            ps.setString(2, trabajo.getResumen());
            ps.setString(3, trabajo.getPalabrasClave());
            ps.setString(4, trabajo.getEstado());
            ps.setInt(5, trabajo.getTrabajoId());
            
            ps.executeUpdate();
            return trabajo;
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar trabajo", e);
        }
    }
    
    @Override
    public boolean eliminarPorId(Integer id) {
        String sql = "DELETE FROM trabajo WHERE trabajo_id = ?";
        
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar trabajo", e);
        }
    }
    
    @Override
    public List<Trabajo> buscarPorAutor(Integer congresistaId) {
        String sql = "SELECT t.* FROM trabajo t " +
                    "INNER JOIN autor a ON t.trabajo_id = a.trabajo_id " +
                    "WHERE a.congresista_id = ?";
        List<Trabajo> trabajos = new ArrayList<>();
        
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, congresistaId);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    trabajos.add(mapearTrabajo(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar trabajos por autor", e);
        }
        
        return trabajos;
    }
    
    @Override
    public List<Trabajo> buscarPorEstado(String estado) {
        String sql = "SELECT * FROM trabajo WHERE estado = ?";
        List<Trabajo> trabajos = new ArrayList<>();
        
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, estado);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    trabajos.add(mapearTrabajo(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar trabajos por estado", e);
        }
        
        return trabajos;
    }
    
    @Override
    public List<Trabajo> buscarPorPalabrasClave(String palabrasClave) {
        String sql = "SELECT * FROM trabajo WHERE titulo LIKE ? OR resumen LIKE ? OR palabras_clave LIKE ?";
        List<Trabajo> trabajos = new ArrayList<>();
        String patron = "%" + palabrasClave + "%";
        
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, patron);
            ps.setString(2, patron);
            ps.setString(3, patron);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    trabajos.add(mapearTrabajo(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar trabajos por palabras clave", e);
        }
        
        return trabajos;
    }
    
    @Override
    public List<Trabajo> buscarPorInstitucion(String institucion) {
        String sql = "SELECT DISTINCT t.* FROM trabajo t " +
                    "INNER JOIN autor a ON t.trabajo_id = a.trabajo_id " +
                    "INNER JOIN congresista c ON a.congresista_id = c.congresista_id " +
                    "WHERE c.institucion_afiliada LIKE ?";
        List<Trabajo> trabajos = new ArrayList<>();
        
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, "%" + institucion + "%");
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    trabajos.add(mapearTrabajo(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar trabajos por institución", e);
        }
        
        return trabajos;
    }
    
    @Override
    public List<Trabajo> buscarSeleccionados() {
        return buscarPorEstado("aceptado");
    }
    
    @Override
    public List<Trabajo> buscarSinRevisar() {
        String sql = "SELECT t.* FROM trabajo t " +
                    "LEFT JOIN revision r ON t.trabajo_id = r.trabajo_id " +
                    "WHERE r.revision_id IS NULL";
        List<Trabajo> trabajos = new ArrayList<>();
        
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                trabajos.add(mapearTrabajo(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar trabajos sin revisar", e);
        }
        
        return trabajos;
    }
    
    @Override
    public List<Trabajo> buscarPorFechaEnvio(LocalDate fechaDesde, LocalDate fechaHasta) {
        String sql = "SELECT * FROM trabajo WHERE fecha_envio BETWEEN ? AND ?";
        List<Trabajo> trabajos = new ArrayList<>();
        
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setDate(1, Date.valueOf(fechaDesde));
            ps.setDate(2, Date.valueOf(fechaHasta));
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    trabajos.add(mapearTrabajo(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar trabajos por fecha", e);
        }
        
        return trabajos;
    }
    
    @Override
    public List<Trabajo> buscarConResumenLargo(int minimoCaracteres) {
        String sql = "SELECT * FROM trabajo WHERE LENGTH(resumen) > ?";
        List<Trabajo> trabajos = new ArrayList<>();
        
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, minimoCaracteres);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    trabajos.add(mapearTrabajo(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar trabajos con resumen largo", e);
        }
        
        return trabajos;
    }
    
    @Override
    public List<Trabajo> buscarOrdenadosPorTitulo() {
        String sql = "SELECT * FROM trabajo ORDER BY titulo ASC";
        List<Trabajo> trabajos = new ArrayList<>();
        
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                trabajos.add(mapearTrabajo(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar trabajos ordenados", e);
        }
        
        return trabajos;
    }
    
    @Override
    public Long contarTrabajosPorAutor(Integer congresistaId) {
        String sql = "SELECT COUNT(*) FROM autor WHERE congresista_id = ?";
        
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, congresistaId);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getLong(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al contar trabajos por autor", e);
        }
        
        return 0L;
    }
    
    @Override
    public Long contarTotalTrabajos() {
        String sql = "SELECT COUNT(*) FROM trabajo";
        
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            if (rs.next()) {
                return rs.getLong(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al contar total de trabajos", e);
        }
        
        return 0L;
    }
    
    @Override
    public List<Object[]> obtenerAutoresConMasTrabajos() {
        String sql = "SELECT a.congresista_id, CONCAT(c.nombre, ' ', c.primer_apellido) as nombre_completo, " +
                    "COUNT(a.trabajo_id) as cantidad_trabajos " +
                    "FROM autor a " +
                    "INNER JOIN congresista c ON a.congresista_id = c.congresista_id " +
                    "GROUP BY a.congresista_id, nombre_completo " +
                    "HAVING cantidad_trabajos > 1 " +
                    "ORDER BY cantidad_trabajos DESC";
        List<Object[]> resultados = new ArrayList<>();
        
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Object[] fila = {
                    rs.getInt("congresista_id"),
                    rs.getString("nombre_completo"),
                    rs.getInt("cantidad_trabajos")
                };
                resultados.add(fila);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener autores con más trabajos", e);
        }
        
        return resultados;
    }
    
    @Override
    public List<Object[]> obtenerTrabajosPopulares() {
        String sql = "SELECT t.trabajo_id, t.titulo, COUNT(p.presentacion_id) as cantidad_presentaciones " +
                    "FROM trabajo t " +
                    "LEFT JOIN presentacion p ON t.trabajo_id = p.trabajo_id " +
                    "GROUP BY t.trabajo_id, t.titulo " +
                    "ORDER BY cantidad_presentaciones DESC";
        List<Object[]> resultados = new ArrayList<>();
        
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Object[] fila = {
                    rs.getInt("trabajo_id"),
                    rs.getString("titulo"),
                    rs.getInt("cantidad_presentaciones")
                };
                resultados.add(fila);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener trabajos populares", e);
        }
        
        return resultados;
    }
    
    // Mapper: ResultSet → Entidad
    private Trabajo mapearTrabajo(ResultSet rs) throws SQLException {
        return new Trabajo(
            rs.getInt("trabajo_id"),
            rs.getString("titulo"),
            rs.getString("resumen"),
            rs.getString("palabras_clave"),
            rs.getDate("fecha_envio").toLocalDate(),
            rs.getString("estado")
        );
    }
}
