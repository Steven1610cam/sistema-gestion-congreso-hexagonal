package com.danielarroyo.congreso.infraestructura.adaptadores;

import com.danielarroyo.congreso.casosdeuso.puertos.salida.CongresistaRepository;
import com.danielarroyo.congreso.dominio.modelo.Congresista;
import com.danielarroyo.congreso.infraestructura.conexion.ConexionBD;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * ADAPTADOR - Implementa repository con JDBC
 * Puerto OUT: acceso a datos con MySQL
 * @author Daniel Arroyo
 */
public class CongresistaRepositoryImpl implements CongresistaRepository {
    
    @Override
    public Congresista guardar(Congresista congresista) {
        String sql = "INSERT INTO congresista (nombre, primer_apellido, institucion_afiliada, " +
                    "email, telefono_movil, fecha_registro, es_miembro_comite, es_organizador) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            ps.setString(1, congresista.getNombre());
            ps.setString(2, congresista.getPrimerApellido());
            ps.setString(3, congresista.getInstitucionAfiliada());
            ps.setString(4, congresista.getEmail());
            ps.setString(5, congresista.getTelefonoMovil());
            ps.setTimestamp(6, Timestamp.valueOf(congresista.getFechaRegistro()));
            ps.setBoolean(7, congresista.isEsMiembroComite());
            ps.setBoolean(8, congresista.isEsOrganizador());
            
            ps.executeUpdate();
            
            // Obtener ID generado
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return new Congresista(
                        rs.getInt(1),
                        congresista.getNombre(),
                        congresista.getPrimerApellido(),
                        congresista.getInstitucionAfiliada(),
                        congresista.getEmail(),
                        congresista.getTelefonoMovil(),
                        congresista.getFechaRegistro(),
                        congresista.isEsMiembroComite(),
                        congresista.isEsOrganizador()
                    );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar congresista", e);
        }
        
        return congresista;
    }
    
    @Override
    public Optional<Congresista> buscarPorId(Integer id) {
        String sql = "SELECT * FROM congresista WHERE congresista_id = ?";
        
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapearCongresista(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar congresista", e);
        }
        
        return Optional.empty();
    }
    
    @Override
    public List<Congresista> buscarTodos() {
        String sql = "SELECT * FROM congresista ORDER BY primer_apellido, nombre";
        List<Congresista> congresistas = new ArrayList<>();
        
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                congresistas.add(mapearCongresista(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar congresistas", e);
        }
        
        return congresistas;
    }
    
    @Override
    public Congresista actualizar(Congresista congresista) {
        String sql = "UPDATE congresista SET nombre=?, primer_apellido=?, institucion_afiliada=?, " +
                    "email=?, telefono_movil=?, es_miembro_comite=?, es_organizador=? " +
                    "WHERE congresista_id=?";
        
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, congresista.getNombre());
            ps.setString(2, congresista.getPrimerApellido());
            ps.setString(3, congresista.getInstitucionAfiliada());
            ps.setString(4, congresista.getEmail());
            ps.setString(5, congresista.getTelefonoMovil());
            ps.setBoolean(6, congresista.isEsMiembroComite());
            ps.setBoolean(7, congresista.isEsOrganizador());
            ps.setInt(8, congresista.getCongresistaId());
            
            ps.executeUpdate();
            return congresista;
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar congresista", e);
        }
    }
    
    @Override
    public boolean eliminarPorId(Integer id) {
        String sql = "DELETE FROM congresista WHERE congresista_id = ?";
        
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar congresista", e);
        }
    }
    
    @Override
    public Optional<Congresista> buscarPorEmail(String email) {
        String sql = "SELECT * FROM congresista WHERE email = ?";
        
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, email);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapearCongresista(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar por email", e);
        }
        
        return Optional.empty();
    }
    
    @Override
    public List<Congresista> buscarConTelefonoMovil() {
        String sql = "SELECT * FROM congresista WHERE telefono_movil IS NOT NULL AND telefono_movil != ''";
        List<Congresista> congresistas = new ArrayList<>();
        
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                congresistas.add(mapearCongresista(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar con teléfono", e);
        }
        
        return congresistas;
    }
    
    @Override
    public List<Congresista> buscarMiembrosComite() {
        String sql = "SELECT * FROM congresista WHERE es_miembro_comite = true";
        List<Congresista> congresistas = new ArrayList<>();
        
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                congresistas.add(mapearCongresista(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar miembros comité", e);
        }
        
        return congresistas;
    }
    
    @Override
    public List<Congresista> buscarOrganizadores() {
        String sql = "SELECT * FROM congresista WHERE es_organizador = true";
        List<Congresista> congresistas = new ArrayList<>();
        
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                congresistas.add(mapearCongresista(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar organizadores", e);
        }
        
        return congresistas;
    }
    
    // Mapper: ResultSet → Entidad
    private Congresista mapearCongresista(ResultSet rs) throws SQLException {
        return new Congresista(
            rs.getInt("congresista_id"),
            rs.getString("nombre"),
            rs.getString("primer_apellido"),
            rs.getString("institucion_afiliada"),
            rs.getString("email"),
            rs.getString("telefono_movil"),
            rs.getTimestamp("fecha_registro").toLocalDateTime(),
            rs.getBoolean("es_miembro_comite"),
            rs.getBoolean("es_organizador")
        );
    }
}