package com.danielarroyo.congreso.infraestructura.adaptadores;

import com.danielarroyo.congreso.casosdeuso.puertos.salida.ConsultasAvanzadasRepository;
import com.danielarroyo.congreso.infraestructura.dto.ConsultaResultadoDTO;
import com.danielarroyo.congreso.infraestructura.conexion.ConexionBD;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * ADAPTADOR - Implementación de consultas avanzadas con JDBC
 * Maneja las 43 consultas específicas del sistema
 * @author Daniel Arroyo
 */
public class ConsultasAvanzadasRepositoryImpl implements ConsultasAvanzadasRepository {
    
    // ===== CONSULTAS SOBRE CONGRESISTAS =====
    
    @Override
    public List<ConsultaResultadoDTO> obtenerPotencialesAsistentes() {
        String sql = "SELECT congresista_id, nombre, primer_apellido, institucion_afiliada, " +
                    "email, telefono_movil FROM congresista ORDER BY primer_apellido, nombre";
        return ejecutarConsultaCongresistas(sql);
    }
    
    public List<ConsultaResultadoDTO> obtenerCongresistasConTelefono() {
        String sql = "SELECT congresista_id, nombre, primer_apellido, institucion_afiliada, " +
                    "email, telefono_movil FROM congresista " +
                    "WHERE telefono_movil IS NOT NULL AND telefono_movil != '' " +
                    "ORDER BY primer_apellido, nombre";
        return ejecutarConsultaCongresistas(sql);
    }
    
    @Override
    public List<ConsultaResultadoDTO> obtenerAsistentesSinRegistro() {
        String sql = "SELECT DISTINCT c.congresista_id, c.nombre, c.primer_apellido, " +
                    "c.institucion_afiliada, c.email, c.telefono_movil " +
                    "FROM congresista c " +
                    "LEFT JOIN autor a ON c.congresista_id = a.congresista_id " +
                    "WHERE a.autor_id IS NULL";
        return ejecutarConsultaCongresistas(sql);
    }
    
    @Override
    public List<ConsultaResultadoDTO> obtenerAsistentesConRegistroSinTrabajos() {
        String sql = "SELECT c.congresista_id, c.nombre, c.primer_apellido, " +
                    "c.institucion_afiliada, c.email, c.telefono_movil " +
                    "FROM congresista c " +
                    "WHERE c.congresista_id NOT IN (" +
                    "   SELECT DISTINCT a.congresista_id FROM autor a" +
                    ") ORDER BY c.primer_apellido";
        return ejecutarConsultaCongresistas(sql);
    }
    
    @Override
    public List<ConsultaResultadoDTO> obtenerAsistentesSinEmail() {
        String sql = "SELECT congresista_id, nombre, primer_apellido, institucion_afiliada, " +
                    "email, telefono_movil FROM congresista " +
                    "WHERE email IS NULL OR email = '' ORDER BY primer_apellido";
        return ejecutarConsultaCongresistas(sql);
    }
    
    @Override
    public List<ConsultaResultadoDTO> obtenerCongresistasOrdenadosPorApellido() {
        String sql = "SELECT congresista_id, nombre, primer_apellido, institucion_afiliada, " +
                    "email, telefono_movil FROM congresista " +
                    "ORDER BY primer_apellido DESC, nombre DESC";
        return ejecutarConsultaCongresistas(sql);
    }
    
    @Override
    public List<ConsultaResultadoDTO> obtenerCongresistasConNombreComun() {
        String sql = "SELECT c1.congresista_id, c1.nombre, c1.primer_apellido, " +
                    "c1.institucion_afiliada, c1.email, c1.telefono_movil " +
                    "FROM congresista c1 " +
                    "WHERE c1.nombre IN (" +
                    "   SELECT c2.nombre FROM congresista c2 " +
                    "   GROUP BY c2.nombre HAVING COUNT(*) > 1" +
                    ") ORDER BY c1.nombre, c1.primer_apellido";
        return ejecutarConsultaCongresistas(sql);
    }
    
    @Override
    public List<ConsultaResultadoDTO> obtenerCongresistasQueEmpiezanConLetra(String letra) {
        String sql = "SELECT congresista_id, nombre, primer_apellido, institucion_afiliada, " +
                    "email, telefono_movil FROM congresista " +
                    "WHERE nombre LIKE ? ORDER BY nombre, primer_apellido";
        
        List<ConsultaResultadoDTO> resultados = new ArrayList<>();
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, letra + "%");
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    resultados.add(mapearCongresista(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error en consulta por letra", e);
        }
        return resultados;
    }
    
    @Override
    public List<ConsultaResultadoDTO> obtenerCongresistasRegistradosDespuesDeFecha(LocalDate fecha) {
        String sql = "SELECT congresista_id, nombre, primer_apellido, institucion_afiliada, " +
                    "email, telefono_movil FROM congresista " +
                    "WHERE fecha_registro > ? ORDER BY fecha_registro DESC";
        
        List<ConsultaResultadoDTO> resultados = new ArrayList<>();
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setDate(1, Date.valueOf(fecha));
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    resultados.add(mapearCongresista(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error en consulta por fecha", e);
        }
        return resultados;
    }
    
    @Override
    public List<ConsultaResultadoDTO> obtenerAsistentesQueEsChairman() {
        String sql = "SELECT DISTINCT c.congresista_id, c.nombre, c.primer_apellido, " +
                    "c.institucion_afiliada, c.email, c.telefono_movil " +
                    "FROM congresista c " +
                    "INNER JOIN chairman ch ON c.congresista_id = ch.congresista_id " +
                    "ORDER BY c.primer_apellido, c.nombre";
        return ejecutarConsultaCongresistas(sql);
    }
    
    public List<ConsultaResultadoDTO> obtenerAsistentesSinRegistroQueEnviaronTrabajos() {
        String sql = "SELECT DISTINCT c.congresista_id, c.nombre, c.primer_apellido, " +
                    "c.institucion_afiliada, c.email, c.telefono_movil " +
                    "FROM congresista c " +
                    "INNER JOIN autor a ON c.congresista_id = a.congresista_id " +
                    "WHERE c.fecha_registro IS NULL OR c.estado_registro = 'pendiente' " +
                    "ORDER BY c.primer_apellido";
        return ejecutarConsultaCongresistas(sql);
    }
    
    // ===== CONSULTAS SOBRE TRABAJOS =====
    
    public List<ConsultaResultadoDTO> obtenerTrabajosEnviados() {
        String sql = "SELECT t.trabajo_id, t.titulo, t.resumen, t.estado, t.fecha_envio, " +
                    "CONCAT(c.nombre, ' ', c.primer_apellido) as autor_principal " +
                    "FROM trabajo t " +
                    "INNER JOIN autor a ON t.trabajo_id = a.trabajo_id AND a.autor_principal = true " +
                    "INNER JOIN congresista c ON a.congresista_id = c.congresista_id " +
                    "ORDER BY t.fecha_envio DESC";
        return ejecutarConsultaTrabajos(sql);
    }
    
    public List<ConsultaResultadoDTO> obtenerTituloResumenAutoresPorTrabajo() {
        String sql = "SELECT t.trabajo_id, t.titulo, t.resumen, " +
                    "GROUP_CONCAT(CONCAT(c.nombre, ' ', c.primer_apellido) SEPARATOR ', ') as autores " +
                    "FROM trabajo t " +
                    "INNER JOIN autor a ON t.trabajo_id = a.trabajo_id " +
                    "INNER JOIN congresista c ON a.congresista_id = c.congresista_id " +
                    "GROUP BY t.trabajo_id, t.titulo, t.resumen " +
                    "ORDER BY t.titulo";
        
        List<ConsultaResultadoDTO> resultados = new ArrayList<>();
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                ConsultaResultadoDTO dto = new ConsultaResultadoDTO();
                dto.setTrabajoId(rs.getInt("trabajo_id"));
                dto.setTituloTrabajo(rs.getString("titulo"));
                dto.setResumenTrabajo(rs.getString("resumen"));
                dto.setNombreCongresista(rs.getString("autores"));
                resultados.add(dto);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error en consulta título resumen autores", e);
        }
        return resultados;
    }
    
    @Override
    public List<ConsultaResultadoDTO> obtenerTrabajosPorAutorEspecifico(String nombreAutor) {
        String sql = "SELECT t.trabajo_id, t.titulo, t.estado, t.fecha_envio, " +
                    "CONCAT(c.nombre, ' ', c.primer_apellido) as autor " +
                    "FROM trabajo t " +
                    "INNER JOIN autor a ON t.trabajo_id = a.trabajo_id " +
                    "INNER JOIN congresista c ON a.congresista_id = c.congresista_id " +
                    "WHERE CONCAT(c.nombre, ' ', c.primer_apellido) LIKE ? " +
                    "ORDER BY t.fecha_envio DESC";
        
        List<ConsultaResultadoDTO> resultados = new ArrayList<>();
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, "%" + nombreAutor + "%");
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ConsultaResultadoDTO dto = new ConsultaResultadoDTO();
                    dto.setTrabajoId(rs.getInt("trabajo_id"));
                    dto.setTituloTrabajo(rs.getString("titulo"));
                    dto.setEstadoTrabajo(rs.getString("estado"));
                    dto.setFechaEnvioTrabajo(rs.getDate("fecha_envio").toLocalDate());
                    dto.setNombreCongresista(rs.getString("autor"));
                    resultados.add(dto);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error en consulta trabajos por autor", e);
        }
        return resultados;
    }
    
    @Override
    public List<ConsultaResultadoDTO> obtenerTrabajosSeleccionadosParaPresentacion() {
        String sql = "SELECT t.trabajo_id, t.titulo, t.estado, t.fecha_envio, " +
                    "CONCAT(c.nombre, ' ', c.primer_apellido) as autor_principal " +
                    "FROM trabajo t " +
                    "INNER JOIN autor a ON t.trabajo_id = a.trabajo_id AND a.autor_principal = true " +
                    "INNER JOIN congresista c ON a.congresista_id = c.congresista_id " +
                    "WHERE t.estado = 'aceptado' " +
                    "ORDER BY t.titulo";
        return ejecutarConsultaTrabajos(sql);
    }
    
    @Override
    public List<ConsultaResultadoDTO> obtenerTrabajosSeleccionadosPorSesion(Integer sesionId) {
        String sql = "SELECT t.trabajo_id, t.titulo, t.estado, t.fecha_envio, " +
                    "s.nombre_sesion, p.orden_presentacion " +
                    "FROM trabajo t " +
                    "INNER JOIN presentacion p ON t.trabajo_id = p.trabajo_id " +
                    "INNER JOIN sesion s ON p.sesion_id = s.sesion_id " +
                    "WHERE s.sesion_id = ? " +
                    "ORDER BY p.orden_presentacion";
        
        List<ConsultaResultadoDTO> resultados = new ArrayList<>();
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, sesionId);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ConsultaResultadoDTO dto = new ConsultaResultadoDTO();
                    dto.setTrabajoId(rs.getInt("trabajo_id"));
                    dto.setTituloTrabajo(rs.getString("titulo"));
                    dto.setEstadoTrabajo(rs.getString("estado"));
                    dto.setTituloSesion(rs.getString("nombre_sesion"));
                    dto.setCantidad((long) rs.getInt("orden_presentacion"));
                    resultados.add(dto);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error en consulta trabajos por sesión", e);
        }
        return resultados;
    }
    
    @Override
    public List<ConsultaResultadoDTO> obtenerTrabajosSinRevisarPorComite() {
        String sql = "SELECT t.trabajo_id, t.titulo, t.estado, t.fecha_envio, " +
                    "CONCAT(c.nombre, ' ', c.primer_apellido) as autor " +
                    "FROM trabajo t " +
                    "INNER JOIN autor a ON t.trabajo_id = a.trabajo_id AND a.autor_principal = true " +
                    "INNER JOIN congresista c ON a.congresista_id = c.congresista_id " +
                    "LEFT JOIN revision r ON t.trabajo_id = r.trabajo_id " +
                    "WHERE r.revision_id IS NULL " +
                    "ORDER BY t.fecha_envio";
        return ejecutarConsultaTrabajos(sql);
    }
    
    @Override
    public List<ConsultaResultadoDTO> obtenerTrabajosSeleccionadosPorInstitucion(String institucion) {
        String sql = "SELECT t.trabajo_id, t.titulo, t.estado, t.fecha_envio, " +
                    "c.institucion_afiliada, CONCAT(c.nombre, ' ', c.primer_apellido) as autor " +
                    "FROM trabajo t " +
                    "INNER JOIN autor a ON t.trabajo_id = a.trabajo_id " +
                    "INNER JOIN congresista c ON a.congresista_id = c.congresista_id " +
                    "WHERE t.estado = 'aceptado' AND c.institucion_afiliada LIKE ? " +
                    "ORDER BY t.titulo";
        
        List<ConsultaResultadoDTO> resultados = new ArrayList<>();
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, "%" + institucion + "%");
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ConsultaResultadoDTO dto = new ConsultaResultadoDTO();
                    dto.setTrabajoId(rs.getInt("trabajo_id"));
                    dto.setTituloTrabajo(rs.getString("titulo"));
                    dto.setEstadoTrabajo(rs.getString("estado"));
                    dto.setInstitucionCongresista(rs.getString("institucion_afiliada"));
                    dto.setNombreCongresista(rs.getString("autor"));
                    resultados.add(dto);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error en consulta trabajos por institución", e);
        }
        return resultados;
    }
    
    @Override
    public List<ConsultaResultadoDTO> obtenerTrabajosConPalabrasClave(String palabrasClave) {
        String sql = "SELECT t.trabajo_id, t.titulo, t.resumen, t.palabras_clave, " +
                    "CONCAT(c.nombre, ' ', c.primer_apellido) as autor " +
                    "FROM trabajo t " +
                    "INNER JOIN autor a ON t.trabajo_id = a.trabajo_id AND a.autor_principal = true " +
                    "INNER JOIN congresista c ON a.congresista_id = c.congresista_id " +
                    "WHERE t.titulo LIKE ? OR t.resumen LIKE ? OR t.palabras_clave LIKE ? " +
                    "ORDER BY t.titulo";
        
        List<ConsultaResultadoDTO> resultados = new ArrayList<>();
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            String patron = "%" + palabrasClave + "%";
            ps.setString(1, patron);
            ps.setString(2, patron);
            ps.setString(3, patron);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ConsultaResultadoDTO dto = new ConsultaResultadoDTO();
                    dto.setTrabajoId(rs.getInt("trabajo_id"));
                    dto.setTituloTrabajo(rs.getString("titulo"));
                    dto.setResumenTrabajo(rs.getString("resumen"));
                    dto.setNombreCongresista(rs.getString("autor"));
                    resultados.add(dto);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error en consulta trabajos por palabras clave", e);
        }
        return resultados;
    }
    
    @Override
    public List<ConsultaResultadoDTO> obtenerTrabajosDeAutoresConTelefono() {
        String sql = "SELECT DISTINCT t.trabajo_id, t.titulo, t.estado, " +
                    "CONCAT(c.nombre, ' ', c.primer_apellido) as autor, c.telefono_movil " +
                    "FROM trabajo t " +
                    "INNER JOIN autor a ON t.trabajo_id = a.trabajo_id " +
                    "INNER JOIN congresista c ON a.congresista_id = c.congresista_id " +
                    "WHERE c.telefono_movil IS NOT NULL AND c.telefono_movil != '' " +
                    "ORDER BY t.titulo";
        return ejecutarConsultaTrabajos(sql);
    }
    
    @Override
    public List<ConsultaResultadoDTO> obtenerTrabajosDeAutoresPorInstitucion(String institucion) {
        return obtenerTrabajosSeleccionadosPorInstitucion(institucion);
    }
    
    @Override
    public List<ConsultaResultadoDTO> obtenerTrabajosConResumenLargo(int minimoCaracteres) {
        String sql = "SELECT t.trabajo_id, t.titulo, t.resumen, " +
                    "LENGTH(t.resumen) as longitud_resumen, " +
                    "CONCAT(c.nombre, ' ', c.primer_apellido) as autor " +
                    "FROM trabajo t " +
                    "INNER JOIN autor a ON t.trabajo_id = a.trabajo_id AND a.autor_principal = true " +
                    "INNER JOIN congresista c ON a.congresista_id = c.congresista_id " +
                    "WHERE LENGTH(t.resumen) > ? " +
                    "ORDER BY LENGTH(t.resumen) DESC";
        
        List<ConsultaResultadoDTO> resultados = new ArrayList<>();
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, minimoCaracteres);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ConsultaResultadoDTO dto = new ConsultaResultadoDTO();
                    dto.setTrabajoId(rs.getInt("trabajo_id"));
                    dto.setTituloTrabajo(rs.getString("titulo"));
                    dto.setNombreCongresista(rs.getString("autor"));
                    dto.setCantidad((long) rs.getInt("longitud_resumen"));
                    dto.setDescripcion("Caracteres en resumen");
                    resultados.add(dto);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error en consulta trabajos con resumen largo", e);
        }
        return resultados;
    }
    
    @Override
    public List<ConsultaResultadoDTO> obtenerTrabajosOrdenadosPorTitulo() {
        String sql = "SELECT t.trabajo_id, t.titulo, t.estado, t.fecha_envio, " +
                    "CONCAT(c.nombre, ' ', c.primer_apellido) as autor " +
                    "FROM trabajo t " +
                    "INNER JOIN autor a ON t.trabajo_id = a.trabajo_id AND a.autor_principal = true " +
                    "INNER JOIN congresista c ON a.congresista_id = c.congresista_id " +
                    "ORDER BY t.titulo ASC";
        return ejecutarConsultaTrabajos(sql);
    }
    
    // ===== CONSULTAS SOBRE SESIONES =====
    
    @Override
    public List<ConsultaResultadoDTO> obtenerInformacionSesiones() {
        String sql = "SELECT s.sesion_id, s.nombre_sesion, s.fecha, s.hora_inicio, s.hora_final, " +
                    "s.estado, sa.nombre_sala, " +
                    "CONCAT(c.nombre, ' ', c.primer_apellido) as chairman " +
                    "FROM sesion s " +
                    "LEFT JOIN sala sa ON s.sala_id = sa.sala_id " +
                    "LEFT JOIN chairman ch ON s.sesion_id = ch.sesion_id " +
                    "LEFT JOIN congresista c ON ch.congresista_id = c.congresista_id " +
                    "ORDER BY s.fecha, s.hora_inicio";
        return ejecutarConsultaSesiones(sql);
    }
    
    @Override
    public List<ConsultaResultadoDTO> obtenerSalasDesignadasPorSesion() {
        String sql = "SELECT s.sesion_id, s.nombre_sesion, sa.sala_id, sa.nombre_sala, " +
                    "sa.capacidad, sa.ubicacion " +
                    "FROM sesion s " +
                    "INNER JOIN sala sa ON s.sala_id = sa.sala_id " +
                    "ORDER BY s.fecha, s.hora_inicio";
        return ejecutarConsultaSesiones(sql);
    }
    
    @Override
    public List<ConsultaResultadoDTO> obtenerSesionesPorDiaEspecifico(LocalDate fecha) {
        String sql = "SELECT s.sesion_id, s.nombre_sesion, s.fecha, s.hora_inicio, s.hora_final, " +
                    "s.estado, sa.nombre_sala " +
                    "FROM sesion s " +
                    "LEFT JOIN sala sa ON s.sala_id = sa.sala_id " +
                    "WHERE s.fecha = ? " +
                    "ORDER BY s.hora_inicio";
        
        List<ConsultaResultadoDTO> resultados = new ArrayList<>();
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setDate(1, Date.valueOf(fecha));
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ConsultaResultadoDTO dto = new ConsultaResultadoDTO();
                    dto.setSesionId(rs.getInt("sesion_id"));
                    dto.setTituloSesion(rs.getString("nombre_sesion"));
                    dto.setFechaSesion(rs.getDate("fecha").toLocalDate());
                    dto.setHoraInicioSesion(rs.getTime("hora_inicio").toLocalTime());
                    dto.setHoraFinSesion(rs.getTime("hora_final").toLocalTime());
                    dto.setNombreSala(rs.getString("nombre_sala"));
                    resultados.add(dto);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error en consulta sesiones por día", e);
        }
        return resultados;
    }
    
    public List<ConsultaResultadoDTO> obtenerSesionesPorDiaYSala(LocalDate fecha, String nombreSala) {
        String sql = "SELECT s.sesion_id, s.nombre_sesion, s.fecha, s.hora_inicio, s.hora_final, " +
                    "sa.nombre_sala " +
                    "FROM sesion s " +
                    "INNER JOIN sala sa ON s.sala_id = sa.sala_id " +
                    "WHERE s.fecha = ? AND sa.nombre_sala LIKE ? " +
                    "ORDER BY s.hora_inicio";
        
        List<ConsultaResultadoDTO> resultados = new ArrayList<>();
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setDate(1, Date.valueOf(fecha));
            ps.setString(2, "%" + nombreSala + "%");
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ConsultaResultadoDTO dto = new ConsultaResultadoDTO();
                    dto.setSesionId(rs.getInt("sesion_id"));
                    dto.setTituloSesion(rs.getString("nombre_sesion"));
                    dto.setFechaSesion(rs.getDate("fecha").toLocalDate());
                    dto.setHoraInicioSesion(rs.getTime("hora_inicio").toLocalTime());
                    dto.setHoraFinSesion(rs.getTime("hora_final").toLocalTime());
                    dto.setNombreSala(rs.getString("nombre_sala"));
                    resultados.add(dto);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error en consulta sesiones por día y sala", e);
        }
        return resultados;
    }
    
    @Override
    public List<ConsultaResultadoDTO> obtenerSesionesSinChairman() {
        String sql = "SELECT s.sesion_id, s.nombre_sesion, s.fecha, s.hora_inicio, s.hora_final " +
                    "FROM sesion s " +
                    "LEFT JOIN chairman ch ON s.sesion_id = ch.sesion_id " +
                    "WHERE ch.chairman_id IS NULL " +
                    "ORDER BY s.fecha, s.hora_inicio";
        return ejecutarConsultaSesiones(sql);
    }
    
    @Override
    public List<ConsultaResultadoDTO> obtenerSesionesSinSalaAsignada() {
        String sql = "SELECT s.sesion_id, s.nombre_sesion, s.fecha, s.hora_inicio, s.hora_final, s.estado " +
                    "FROM sesion s " +
                    "WHERE s.sala_id IS NULL " +
                    "ORDER BY s.fecha, s.hora_inicio";
        return ejecutarConsultaSesiones(sql);
    }
    
    @Override
    public List<ConsultaResultadoDTO> obtenerSesionesConMenosDeTresTrabajos() {
        String sql = "SELECT s.sesion_id, s.nombre_sesion, COUNT(p.presentacion_id) as cantidad_trabajos " +
                    "FROM sesion s " +
                    "LEFT JOIN presentacion p ON s.sesion_id = p.sesion_id " +
                    "GROUP BY s.sesion_id, s.nombre_sesion " +
                    "HAVING COUNT(p.presentacion_id) < 3 " +
                    "ORDER BY cantidad_trabajos, s.nombre_sesion";
        
        List<ConsultaResultadoDTO> resultados = new ArrayList<>();
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                ConsultaResultadoDTO dto = new ConsultaResultadoDTO();
                dto.setSesionId(rs.getInt("sesion_id"));
                dto.setTituloSesion(rs.getString("nombre_sesion"));
                dto.setCantidad((long) rs.getInt("cantidad_trabajos"));
                dto.setDescripcion("Trabajos asignados");
                resultados.add(dto);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error en consulta sesiones con menos trabajos", e);
        }
        return resultados;
    }
    
    @Override
    public List<ConsultaResultadoDTO> obtenerSesionesOrdenadasPorFechaHora() {
        return obtenerInformacionSesiones();
    }
    
    @Override
    public List<ConsultaResultadoDTO> obtenerSesionesSinPonenteDesignado() {
        String sql = "SELECT s.sesion_id, s.nombre_sesion, s.fecha, s.hora_inicio, s.hora_final " +
                    "FROM sesion s " +
                    "LEFT JOIN presentacion p ON s.sesion_id = p.sesion_id " +
                    "WHERE p.presentacion_id IS NULL " +
                    "ORDER BY s.fecha, s.hora_inicio";
        return ejecutarConsultaSesiones(sql);
    }
    
    // ===== CONSULTAS SOBRE COMITÉS =====
    
    @Override
    public List<ConsultaResultadoDTO> obtenerComiteSeleccionRevisores() {
        String sql = "SELECT c.congresista_id, CONCAT(c.nombre, ' ', c.primer_apellido) as nombre_completo, " +
                    "cs.especialidad, cs.cargo, cs.fecha_ingreso " +
                    "FROM congresista c " +
                    "INNER JOIN comite_seleccion cs ON c.congresista_id = cs.congresista_id " +
                    "WHERE cs.activo = true " +
                    "ORDER BY cs.cargo, c.primer_apellido";
        
        List<ConsultaResultadoDTO> resultados = new ArrayList<>();
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                ConsultaResultadoDTO dto = new ConsultaResultadoDTO();
                dto.setCongresistaId(rs.getInt("congresista_id"));
                dto.setNombreCongresista(rs.getString("nombre_completo"));
                dto.setDescripcion(rs.getString("cargo") + " - " + rs.getString("especialidad"));
                resultados.add(dto);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error en consulta comité selección", e);
        }
        return resultados;
    }
    
    @Override
    public List<ConsultaResultadoDTO> obtenerMiembrosComiteSeleccion() {
        return obtenerComiteSeleccionRevisores();
    }
    
    @Override
    public List<ConsultaResultadoDTO> obtenerMiembrosComiteOrganizador() {
        String sql = "SELECT congresista_id, CONCAT(nombre, ' ', primer_apellido) as nombre_completo, " +
                    "institucion_afiliada, email, " +
                    "CASE WHEN es_miembro_comite AND es_organizador THEN 'Miembro y Organizador' " +
                    "     WHEN es_miembro_comite THEN 'Miembro Comité' " +
                    "     WHEN es_organizador THEN 'Organizador' " +
                    "     ELSE 'Sin rol' END as rol " +
                    "FROM congresista " +
                    "WHERE es_miembro_comite = true OR es_organizador = true " +
                    "ORDER BY primer_apellido, nombre";
        
        List<ConsultaResultadoDTO> resultados = new ArrayList<>();
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                ConsultaResultadoDTO dto = new ConsultaResultadoDTO();
                dto.setCongresistaId(rs.getInt("congresista_id"));
                dto.setNombreCongresista(rs.getString("nombre_completo"));
                dto.setInstitucionCongresista(rs.getString("institucion_afiliada"));
                dto.setEmailCongresista(rs.getString("email"));
                dto.setDescripcion(rs.getString("rol"));
                resultados.add(dto);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error en consulta comité organizador", e);
        }
        return resultados;
    }
    
    @Override
    public List<ConsultaResultadoDTO> obtenerSesionesModadasPorMiembrosComiteOrganizador() {
        String sql = "SELECT s.sesion_id, s.nombre_sesion, s.fecha, " +
                    "CONCAT(c.nombre, ' ', c.primer_apellido) as chairman, " +
                    "CASE WHEN c.es_miembro_comite THEN 'Miembro Comité' " +
                    "     WHEN c.es_organizador THEN 'Organizador' END as rol_chairman " +
                    "FROM sesion s " +
                    "INNER JOIN chairman ch ON s.sesion_id = ch.sesion_id " +
                    "INNER JOIN congresista c ON ch.congresista_id = c.congresista_id " +
                    "WHERE c.es_miembro_comite = true OR c.es_organizador = true " +
                    "ORDER BY s.fecha, s.hora_inicio";
        
        List<ConsultaResultadoDTO> resultados = new ArrayList<>();
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                ConsultaResultadoDTO dto = new ConsultaResultadoDTO();
                dto.setSesionId(rs.getInt("sesion_id"));
                dto.setTituloSesion(rs.getString("nombre_sesion"));
                dto.setFechaSesion(rs.getDate("fecha").toLocalDate());
                dto.setNombreCongresista(rs.getString("chairman"));
                dto.setDescripcion(rs.getString("rol_chairman"));
                resultados.add(dto);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error en consulta sesiones moderadas por comité", e);
        }
        return resultados;
    }
    
    // ===== CONSULTAS SOBRE PONENTES Y CHAIRMAN =====
    
    @Override
    public List<ConsultaResultadoDTO> obtenerPonentesDesignadosPorSesion() {
        String sql = "SELECT s.sesion_id, s.nombre_sesion, " +
                    "CONCAT(c.nombre, ' ', c.primer_apellido) as ponente, " +
                    "t.titulo as trabajo, p.orden_presentacion " +
                    "FROM sesion s " +
                    "INNER JOIN presentacion p ON s.sesion_id = p.sesion_id " +
                    "INNER JOIN trabajo t ON p.trabajo_id = t.trabajo_id " +
                    "INNER JOIN autor a ON t.trabajo_id = a.trabajo_id AND a.autor_principal = true " +
                    "INNER JOIN congresista c ON a.congresista_id = c.congresista_id " +
                    "ORDER BY s.sesion_id, p.orden_presentacion";
        
        List<ConsultaResultadoDTO> resultados = new ArrayList<>();
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                ConsultaResultadoDTO dto = new ConsultaResultadoDTO();
                dto.setSesionId(rs.getInt("sesion_id"));
                dto.setTituloSesion(rs.getString("nombre_sesion"));
                dto.setNombreCongresista(rs.getString("ponente"));
                dto.setTituloTrabajo(rs.getString("trabajo"));
                dto.setCantidad((long) rs.getInt("orden_presentacion"));
                dto.setDescripcion("Orden de presentación");
                resultados.add(dto);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error en consulta ponentes por sesión", e);
        }
        return resultados;
    }
    
    @Override
    public List<ConsultaResultadoDTO> obtenerPonentesYTrabajosParaSesion(Integer sesionId) {
        return obtenerTrabajosSeleccionadosPorSesion(sesionId);
    }
    
    @Override
    public List<ConsultaResultadoDTO> obtenerSesionesModadasPorChairman(Integer chairmanId) {
        String sql = "SELECT s.sesion_id, s.nombre_sesion, s.fecha, s.hora_inicio, s.hora_final, " +
                    "CONCAT(c.nombre, ' ', c.primer_apellido) as chairman " +
                    "FROM sesion s " +
                    "INNER JOIN chairman ch ON s.sesion_id = ch.sesion_id " +
                    "INNER JOIN congresista c ON ch.congresista_id = c.congresista_id " +
                    "WHERE c.congresista_id = ? " +
                    "ORDER BY s.fecha, s.hora_inicio";
        
        List<ConsultaResultadoDTO> resultados = new ArrayList<>();
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, chairmanId);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ConsultaResultadoDTO dto = new ConsultaResultadoDTO();
                    dto.setSesionId(rs.getInt("sesion_id"));
                    dto.setTituloSesion(rs.getString("nombre_sesion"));
                    dto.setFechaSesion(rs.getDate("fecha").toLocalDate());
                    dto.setHoraInicioSesion(rs.getTime("hora_inicio").toLocalTime());
                    dto.setHoraFinSesion(rs.getTime("hora_final").toLocalTime());
                    dto.setNombreCongresista(rs.getString("chairman"));
                    resultados.add(dto);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error en consulta sesiones por chairman", e);
        }
        return resultados;
    }
    
    @Override
    public List<ConsultaResultadoDTO> obtenerChairmanDeSesionEspecifica(Integer sesionId) {
        String sql = "SELECT s.sesion_id, s.nombre_sesion, " +
                    "CONCAT(c.nombre, ' ', c.primer_apellido) as chairman, " +
                    "c.institucion_afiliada, ch.observaciones " +
                    "FROM sesion s " +
                    "INNER JOIN chairman ch ON s.sesion_id = ch.sesion_id " +
                    "INNER JOIN congresista c ON ch.congresista_id = c.congresista_id " +
                    "WHERE s.sesion_id = ?";
        
        List<ConsultaResultadoDTO> resultados = new ArrayList<>();
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, sesionId);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ConsultaResultadoDTO dto = new ConsultaResultadoDTO();
                    dto.setSesionId(rs.getInt("sesion_id"));
                    dto.setTituloSesion(rs.getString("nombre_sesion"));
                    dto.setNombreCongresista(rs.getString("chairman"));
                    dto.setInstitucionCongresista(rs.getString("institucion_afiliada"));
                    dto.setDescripcion(rs.getString("observaciones"));
                    resultados.add(dto);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error en consulta chairman de sesión", e);
        }
        return resultados;
    }
    
    // ===== CONSULTAS DE ESTADÍSTICAS =====
    
    @Override
    public List<ConsultaResultadoDTO> obtenerCantidadTrabajosPorAutor() {
        String sql = "SELECT c.congresista_id, CONCAT(c.nombre, ' ', c.primer_apellido) as nombre_completo, " +
                    "COUNT(a.trabajo_id) as cantidad_trabajos " +
                    "FROM congresista c " +
                    "INNER JOIN autor a ON c.congresista_id = a.congresista_id " +
                    "GROUP BY c.congresista_id, nombre_completo " +
                    "ORDER BY cantidad_trabajos DESC, nombre_completo";
        
        List<ConsultaResultadoDTO> resultados = new ArrayList<>();
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                ConsultaResultadoDTO dto = new ConsultaResultadoDTO();
                dto.setCongresistaId(rs.getInt("congresista_id"));
                dto.setNombreCongresista(rs.getString("nombre_completo"));
                dto.setCantidad((long) rs.getInt("cantidad_trabajos"));
                dto.setDescripcion("Trabajos enviados");
                resultados.add(dto);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error en consulta cantidad trabajos por autor", e);
        }
        return resultados;
    }
    
    @Override
    public List<ConsultaResultadoDTO> obtenerTotalTrabajosEnviados() {
        String sql = "SELECT COUNT(*) as total FROM trabajo";
        List<ConsultaResultadoDTO> resultados = new ArrayList<>();
        
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            if (rs.next()) {
                ConsultaResultadoDTO dto = new ConsultaResultadoDTO();
                dto.setCantidad((long) rs.getInt("total"));
                dto.setDescripcion("Total de trabajos enviados");
                resultados.add(dto);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error en consulta total trabajos", e);
        }
        return resultados;
    }
    
    @Override
    public List<ConsultaResultadoDTO> obtenerAutoresConMasDeUnTrabajo() {
        String sql = "SELECT c.congresista_id, CONCAT(c.nombre, ' ', c.primer_apellido) as nombre_completo, " +
                    "COUNT(a.trabajo_id) as cantidad_trabajos " +
                    "FROM congresista c " +
                    "INNER JOIN autor a ON c.congresista_id = a.congresista_id " +
                    "GROUP BY c.congresista_id, nombre_completo " +
                    "HAVING COUNT(a.trabajo_id) > 1 " +
                    "ORDER BY cantidad_trabajos DESC";
        
        List<ConsultaResultadoDTO> resultados = new ArrayList<>();
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                ConsultaResultadoDTO dto = new ConsultaResultadoDTO();
                dto.setCongresistaId(rs.getInt("congresista_id"));
                dto.setNombreCongresista(rs.getString("nombre_completo"));
                dto.setCantidad((long) rs.getInt("cantidad_trabajos"));
                dto.setDescripcion("Múltiples trabajos");
                resultados.add(dto);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error en consulta autores múltiples trabajos", e);
        }
        return resultados;
    }
    
    @Override
    public List<ConsultaResultadoDTO> obtenerSesionesConMasTrabajos() {
        String sql = "SELECT s.sesion_id, s.nombre_sesion, COUNT(p.presentacion_id) as cantidad_trabajos " +
                    "FROM sesion s " +
                    "LEFT JOIN presentacion p ON s.sesion_id = p.sesion_id " +
                    "GROUP BY s.sesion_id, s.nombre_sesion " +
                    "ORDER BY cantidad_trabajos DESC, s.nombre_sesion";
        
        List<ConsultaResultadoDTO> resultados = new ArrayList<>();
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                ConsultaResultadoDTO dto = new ConsultaResultadoDTO();
                dto.setSesionId(rs.getInt("sesion_id"));
                dto.setTituloSesion(rs.getString("nombre_sesion"));
                dto.setCantidad((long) rs.getInt("cantidad_trabajos"));
                dto.setDescripcion("Trabajos programados");
                resultados.add(dto);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error en consulta sesiones con más trabajos", e);
        }
        return resultados;
    }
    
    @Override
    public List<ConsultaResultadoDTO> obtenerTrabajosEnSesionConMayorCantidad() {
        String sql = "SELECT t.trabajo_id, t.titulo, s.nombre_sesion, " +
                    "COUNT(*) OVER (PARTITION BY s.sesion_id) as trabajos_en_sesion " +
                    "FROM trabajo t " +
                    "INNER JOIN presentacion p ON t.trabajo_id = p.trabajo_id " +
                    "INNER JOIN sesion s ON p.sesion_id = s.sesion_id " +
                    "WHERE s.sesion_id = (" +
                    "   SELECT sesion_id FROM (" +
                    "       SELECT sesion_id, COUNT(*) as cantidad " +
                    "       FROM presentacion GROUP BY sesion_id " +
                    "       ORDER BY cantidad DESC LIMIT 1" +
                    "   ) as max_sesion" +
                    ") ORDER BY p.orden_presentacion";
        return ejecutarConsultaTrabajos(sql);
    }
    
    // ===== CONSULTAS ADICIONALES =====
    
    @Override
    public List<ConsultaResultadoDTO> obtenerAutoresQueNoEsPonentes() {
        String sql = "SELECT DISTINCT c.congresista_id, CONCAT(c.nombre, ' ', c.primer_apellido) as nombre_completo, " +
                    "c.institucion_afiliada " +
                    "FROM congresista c " +
                    "INNER JOIN autor a ON c.congresista_id = a.congresista_id " +
                    "WHERE c.congresista_id NOT IN (" +
                    "   SELECT DISTINCT a2.congresista_id " +
                    "   FROM autor a2 " +
                    "   INNER JOIN trabajo t2 ON a2.trabajo_id = t2.trabajo_id " +
                    "   INNER JOIN presentacion p2 ON t2.trabajo_id = p2.trabajo_id" +
                    ") ORDER BY c.primer_apellido";
        
        List<ConsultaResultadoDTO> resultados = new ArrayList<>();
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                ConsultaResultadoDTO dto = new ConsultaResultadoDTO();
                dto.setCongresistaId(rs.getInt("congresista_id"));
                dto.setNombreCongresista(rs.getString("nombre_completo"));
                dto.setInstitucionCongresista(rs.getString("institucion_afiliada"));
                dto.setDescripcion("Autor que no es ponente");
                resultados.add(dto);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error en consulta autores no ponentes", e);
        }
        return resultados;
    }
    
    @Override
    public List<ConsultaResultadoDTO> obtenerPonentesQueEsMiembrosComiteOrganizador() {
        String sql = "SELECT DISTINCT c.congresista_id, CONCAT(c.nombre, ' ', c.primer_apellido) as nombre_completo, " +
                    "c.institucion_afiliada, " +
                    "CASE WHEN c.es_miembro_comite THEN 'Miembro Comité' " +
                    "     WHEN c.es_organizador THEN 'Organizador' " +
                    "     ELSE 'Ambos' END as rol " +
                    "FROM congresista c " +
                    "INNER JOIN autor a ON c.congresista_id = a.congresista_id " +
                    "INNER JOIN trabajo t ON a.trabajo_id = t.trabajo_id " +
                    "INNER JOIN presentacion p ON t.trabajo_id = p.trabajo_id " +
                    "WHERE (c.es_miembro_comite = true OR c.es_organizador = true) " +
                    "ORDER BY c.primer_apellido";
        
        List<ConsultaResultadoDTO> resultados = new ArrayList<>();
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                ConsultaResultadoDTO dto = new ConsultaResultadoDTO();
                dto.setCongresistaId(rs.getInt("congresista_id"));
                dto.setNombreCongresista(rs.getString("nombre_completo"));
                dto.setInstitucionCongresista(rs.getString("institucion_afiliada"));
                dto.setDescripcion(rs.getString("rol"));
                resultados.add(dto);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error en consulta ponentes del comité", e);
        }
        return resultados;
    }
    
    @Override
    public List<ConsultaResultadoDTO> obtenerAutoresQueEsPonenteEnOtrasSesiones() {
        String sql = "SELECT DISTINCT c.congresista_id, CONCAT(c.nombre, ' ', c.primer_apellido) as nombre_completo, " +
                    "COUNT(DISTINCT p.sesion_id) as sesiones_como_ponente " +
                    "FROM congresista c " +
                    "INNER JOIN autor a ON c.congresista_id = a.congresista_id " +
                    "INNER JOIN trabajo t ON a.trabajo_id = t.trabajo_id " +
                    "INNER JOIN presentacion p ON t.trabajo_id = p.trabajo_id " +
                    "GROUP BY c.congresista_id, nombre_completo " +
                    "HAVING COUNT(DISTINCT p.sesion_id) > 1 " +
                    "ORDER BY sesiones_como_ponente DESC";
        
        List<ConsultaResultadoDTO> resultados = new ArrayList<>();
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                ConsultaResultadoDTO dto = new ConsultaResultadoDTO();
                dto.setCongresistaId(rs.getInt("congresista_id"));
                dto.setNombreCongresista(rs.getString("nombre_completo"));
                dto.setCantidad((long) rs.getInt("sesiones_como_ponente"));
                dto.setDescripcion("Sesiones como ponente");
                resultados.add(dto);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error en consulta autores ponentes múltiples", e);
        }
        return resultados;
    }
    
    public List<ConsultaResultadoDTO> obtenerSesionesPorAreaInvestigacionEspecifica(String areaInvestigacion) {
        String sql = "SELECT DISTINCT s.sesion_id, s.nombre_sesion, s.fecha, " +
                    "COUNT(p.presentacion_id) as trabajos_area " +
                    "FROM sesion s " +
                    "INNER JOIN presentacion p ON s.sesion_id = p.sesion_id " +
                    "INNER JOIN trabajo t ON p.trabajo_id = t.trabajo_id " +
                    "WHERE t.palabras_clave LIKE ? OR t.resumen LIKE ? " +
                    "GROUP BY s.sesion_id, s.nombre_sesion, s.fecha " +
                    "ORDER BY trabajos_area DESC";
        
        List<ConsultaResultadoDTO> resultados = new ArrayList<>();
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            String patron = "%" + areaInvestigacion + "%";
            ps.setString(1, patron);
            ps.setString(2, patron);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ConsultaResultadoDTO dto = new ConsultaResultadoDTO();
                    dto.setSesionId(rs.getInt("sesion_id"));
                    dto.setTituloSesion(rs.getString("nombre_sesion"));
                    dto.setFechaSesion(rs.getDate("fecha").toLocalDate());
                    dto.setCantidad((long) rs.getInt("trabajos_area"));
                    dto.setDescripcion("Trabajos del área: " + areaInvestigacion);
                    resultados.add(dto);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error en consulta sesiones por área", e);
        }
        return resultados;
    }
    
    // ===== CONSULTAS ADICIONALES PARA COMPLETAR LAS 43 =====
    
    /**
     * Obtener trabajos enviados por autores que se registraron después de una fecha determinada
     */
    public List<ConsultaResultadoDTO> obtenerTrabajosDeAutoresRegistradosPostFecha(LocalDate fecha) {
        String sql = "SELECT t.trabajo_id, t.titulo, t.estado, t.fecha_envio, " +
                    "CONCAT(c.nombre, ' ', c.primer_apellido) as autor, c.fecha_registro " +
                    "FROM trabajo t " +
                    "INNER JOIN autor a ON t.trabajo_id = a.trabajo_id " +
                    "INNER JOIN congresista c ON a.congresista_id = c.congresista_id " +
                    "WHERE c.fecha_registro > ? " +
                    "ORDER BY c.fecha_registro DESC, t.titulo";
        
        List<ConsultaResultadoDTO> resultados = new ArrayList<>();
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setDate(1, Date.valueOf(fecha));
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ConsultaResultadoDTO dto = new ConsultaResultadoDTO();
                    dto.setTrabajoId(rs.getInt("trabajo_id"));
                    dto.setTituloTrabajo(rs.getString("titulo"));
                    dto.setEstadoTrabajo(rs.getString("estado"));
                    dto.setFechaEnvioTrabajo(rs.getDate("fecha_envio").toLocalDate());
                    dto.setNombreCongresista(rs.getString("autor"));
                    resultados.add(dto);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error en consulta trabajos por fecha registro", e);
        }
        return resultados;
    }
    
    /**
     * Mostrar trabajos enviados por autores cuyo nombre comienza con una letra específica
     */
    public List<ConsultaResultadoDTO> obtenerTrabajosDeAutoresQueEmpiezanConLetra(String letra) {
        String sql = "SELECT t.trabajo_id, t.titulo, t.estado, t.fecha_envio, " +
                    "CONCAT(c.nombre, ' ', c.primer_apellido) as autor " +
                    "FROM trabajo t " +
                    "INNER JOIN autor a ON t.trabajo_id = a.trabajo_id " +
                    "INNER JOIN congresista c ON a.congresista_id = c.congresista_id " +
                    "WHERE c.nombre LIKE ? " +
                    "ORDER BY c.nombre, t.titulo";
        
        List<ConsultaResultadoDTO> resultados = new ArrayList<>();
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, letra + "%");
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ConsultaResultadoDTO dto = new ConsultaResultadoDTO();
                    dto.setTrabajoId(rs.getInt("trabajo_id"));
                    dto.setTituloTrabajo(rs.getString("titulo"));
                    dto.setEstadoTrabajo(rs.getString("estado"));
                    dto.setFechaEnvioTrabajo(rs.getDate("fecha_envio").toLocalDate());
                    dto.setNombreCongresista(rs.getString("autor"));
                    resultados.add(dto);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error en consulta trabajos por letra autor", e);
        }
        return resultados;
    }
    
    /**
     * Listar trabajos enviados por autores que han enviado trabajos pero aún no se han registrado
     */
    public List<ConsultaResultadoDTO> obtenerTrabajosDeAutoresSinRegistroCompleto() {
        String sql = "SELECT t.trabajo_id, t.titulo, t.estado, t.fecha_envio, " +
                    "CONCAT(c.nombre, ' ', c.primer_apellido) as autor " +
                    "FROM trabajo t " +
                    "INNER JOIN autor a ON t.trabajo_id = a.trabajo_id " +
                    "INNER JOIN congresista c ON a.congresista_id = c.congresista_id " +
                    "ORDER BY t.fecha_envio DESC";
        return ejecutarConsultaTrabajos(sql);
    }
    
    /**
     * Obtener trabajos enviados por autores que son ponentes en otras sesiones
     */
    public List<ConsultaResultadoDTO> obtenerTrabajosDeAutoresPonentesEnOtrasSesiones() {
        String sql = "SELECT DISTINCT t1.trabajo_id, t1.titulo, t1.estado, " +
                    "CONCAT(c.nombre, ' ', c.primer_apellido) as autor, " +
                    "COUNT(DISTINCT p2.sesion_id) as sesiones_como_ponente " +
                    "FROM trabajo t1 " +
                    "INNER JOIN autor a1 ON t1.trabajo_id = a1.trabajo_id " +
                    "INNER JOIN congresista c ON a1.congresista_id = c.congresista_id " +
                    "INNER JOIN autor a2 ON c.congresista_id = a2.congresista_id " +
                    "INNER JOIN trabajo t2 ON a2.trabajo_id = t2.trabajo_id " +
                    "INNER JOIN presentacion p2 ON t2.trabajo_id = p2.trabajo_id " +
                    "GROUP BY t1.trabajo_id, t1.titulo, t1.estado, c.congresista_id, c.nombre, c.primer_apellido " +
                    "HAVING COUNT(DISTINCT p2.sesion_id) > 0 " +
                    "ORDER BY sesiones_como_ponente DESC";
        
        List<ConsultaResultadoDTO> resultados = new ArrayList<>();
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                ConsultaResultadoDTO dto = new ConsultaResultadoDTO();
                dto.setTrabajoId(rs.getInt("trabajo_id"));
                dto.setTituloTrabajo(rs.getString("titulo"));
                dto.setEstadoTrabajo(rs.getString("estado"));
                dto.setNombreCongresista(rs.getString("autor"));
                dto.setCantidad((long) rs.getInt("sesiones_como_ponente"));
                dto.setDescripcion("Sesiones como ponente");
                resultados.add(dto);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error en consulta trabajos autores ponentes", e);
        }
        return resultados;
    }
    
    /**
     * Mostrar las sesiones del evento que participan autores de una determinada área de investigación
     */
    public List<ConsultaResultadoDTO> obtenerSesionesConAutoresDeAreaEspecifica(String areaInvestigacion) {
        return obtenerSesionesPorAreaInvestigacionEspecifica(areaInvestigacion);
    }
    
    /**
     * Obtener trabajos enviados por autores de una institución afiliada en particular
     */
    public List<ConsultaResultadoDTO> obtenerTrabajosDeInstitucionAfiliada(String institucion) {
        return obtenerTrabajosDeAutoresPorInstitucion(institucion);
    }
    
    // ===== MÉTODOS AUXILIARES =====
    
    private List<ConsultaResultadoDTO> ejecutarConsultaCongresistas(String sql) {
        List<ConsultaResultadoDTO> resultados = new ArrayList<>();
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                resultados.add(mapearCongresista(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error en consulta de congresistas", e);
        }
        return resultados;
    }
    
    private List<ConsultaResultadoDTO> ejecutarConsultaTrabajos(String sql) {
        List<ConsultaResultadoDTO> resultados = new ArrayList<>();
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                ConsultaResultadoDTO dto = new ConsultaResultadoDTO();
                dto.setTrabajoId(rs.getInt("trabajo_id"));
                dto.setTituloTrabajo(rs.getString("titulo"));
                try {
                    dto.setEstadoTrabajo(rs.getString("estado"));
                } catch (SQLException e) {
                    // Campo no disponible en esta consulta
                }
                try {
                    if (rs.getDate("fecha_envio") != null) {
                        dto.setFechaEnvioTrabajo(rs.getDate("fecha_envio").toLocalDate());
                    }
                } catch (SQLException e) {
                    // Campo no disponible en esta consulta
                }
                try {
                    dto.setNombreCongresista(rs.getString("autor"));
                } catch (SQLException e) {
                    try {
                        dto.setNombreCongresista(rs.getString("autor_principal"));
                    } catch (SQLException e2) {
                        // Campo no disponible en esta consulta
                    }
                }
                resultados.add(dto);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error en consulta de trabajos", e);
        }
        return resultados;
    }
    
    private List<ConsultaResultadoDTO> ejecutarConsultaSesiones(String sql) {
        List<ConsultaResultadoDTO> resultados = new ArrayList<>();
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                ConsultaResultadoDTO dto = new ConsultaResultadoDTO();
                dto.setSesionId(rs.getInt("sesion_id"));
                dto.setTituloSesion(rs.getString("nombre_sesion"));
                try {
                    if (rs.getDate("fecha") != null) {
                        dto.setFechaSesion(rs.getDate("fecha").toLocalDate());
                    }
                } catch (SQLException e) {
                    // Campo no disponible en esta consulta
                }
                try {
                    if (rs.getTime("hora_inicio") != null) {
                        dto.setHoraInicioSesion(rs.getTime("hora_inicio").toLocalTime());
                    }
                    if (rs.getTime("hora_final") != null) {
                        dto.setHoraFinSesion(rs.getTime("hora_final").toLocalTime());
                    }
                    dto.setNombreSala(rs.getString("nombre_sala"));
                } catch (SQLException e) {
                    // Campos no disponibles en esta consulta
                }
                resultados.add(dto);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error en consulta de sesiones", e);
        }
        return resultados;
    }
    
    private ConsultaResultadoDTO mapearCongresista(ResultSet rs) throws SQLException {
        ConsultaResultadoDTO dto = new ConsultaResultadoDTO();
        dto.setCongresistaId(rs.getInt("congresista_id"));
        dto.setNombreCongresista(rs.getString("nombre"));
        dto.setApellidoCongresista(rs.getString("primer_apellido"));
        dto.setInstitucionCongresista(rs.getString("institucion_afiliada"));
        dto.setEmailCongresista(rs.getString("email"));
        dto.setTelefonoCongresista(rs.getString("telefono_movil"));
        return dto;
    }

    @Override
    public List<ConsultaResultadoDTO> obtenerSesionesPorFechaYSala(LocalDate fecha, Integer salaId) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<ConsultaResultadoDTO> obtenerSesionesConAreaInvestigacionEspecifica(String areaInvestigacion) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}