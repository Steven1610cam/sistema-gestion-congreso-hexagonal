package com.danielarroyo.congreso.infraestructura.presentacion;

import com.danielarroyo.congreso.infraestructura.controladores.SistemaController;
import com.danielarroyo.congreso.infraestructura.dto.*;
import com.danielarroyo.congreso.infraestructura.conexion.ConexionBD;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;
import java.util.List;
import javax.swing.Timer;

/**
 *
 * @author Steven
 */
public class DashboardPrincipal extends javax.swing.JFrame {

    /**
     * Creates new form DashboardPrincipal
     */
    public DashboardPrincipal() {
        sistema = new SistemaController();
        initComponents();
        configurarVentana();
        configurarTabla();
        configurarConsultas();
        iniciarMonitoreo();
    }
    
    /**
     * Configurar propiedades de la ventana
     */
    private void configurarVentana() {
        setTitle("Sistema de Congreso - Dashboard de Consultas");
        setLocationRelativeTo(null);
    }

    /**
     * Configurar tabla de resultados
     */
    private void configurarTabla() {
        modeloTabla = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaResultados.setModel(modeloTabla);
    }

/**
 * Configurar consultas disponibles - LAS 43 CONSULTAS
 */
private void configurarConsultas() {
        comboConsultas.removeAllItems();
        
        // Opción por defecto
        comboConsultas.addItem(new ConsultaItem("-- SELECCIONA UNA CONSULTA --", "", ""));
        
        // === CONGRESISTAS (10 consultas) ===
        comboConsultas.addItem(new ConsultaItem("📋 Lista de potenciales asistentes registrados", "congresistas", "potenciales_asistentes"));
        comboConsultas.addItem(new ConsultaItem("👥 Información básica de congresistas", "congresistas", "info_basica"));
        comboConsultas.addItem(new ConsultaItem("📱 Congresistas con teléfono móvil", "congresistas", "con_telefono"));
        comboConsultas.addItem(new ConsultaItem("🏛️ Miembros del comité organizador", "congresistas", "comite_organizador"));
        comboConsultas.addItem(new ConsultaItem("❌ Asistentes sin registro en la aplicación", "congresistas", "sin_registro"));
        comboConsultas.addItem(new ConsultaItem("📝 Congresistas registrados sin trabajos", "congresistas", "sin_trabajos"));
        comboConsultas.addItem(new ConsultaItem("🪑 Asistentes que son chairman", "congresistas", "son_chairman"));
        comboConsultas.addItem(new ConsultaItem("📞 Sin dirección de correo electrónico", "congresistas", "sin_email"));
        comboConsultas.addItem(new ConsultaItem("🔤 Congresistas por letra inicial", "congresistas", "por_letra"));
        comboConsultas.addItem(new ConsultaItem("📅 Registrados después de fecha específica", "congresistas", "despues_fecha"));
        
        // === TRABAJOS (18 consultas) ===
        comboConsultas.addItem(new ConsultaItem("📄 Trabajos enviados por autor específico", "trabajos", "por_autor"));
        comboConsultas.addItem(new ConsultaItem("✅ Trabajos seleccionados para presentación", "trabajos", "seleccionados"));
        comboConsultas.addItem(new ConsultaItem("📊 Trabajos seleccionados por sesión", "trabajos", "por_sesion"));
        comboConsultas.addItem(new ConsultaItem("⏳ Trabajos sin revisar por el comité", "trabajos", "sin_revisar"));
        comboConsultas.addItem(new ConsultaItem("🏫 Trabajos por institución específica", "trabajos", "por_institucion"));
        comboConsultas.addItem(new ConsultaItem("🔍 Trabajos con palabras clave", "trabajos", "palabras_clave"));
        comboConsultas.addItem(new ConsultaItem("📱 Trabajos de autores con teléfono", "trabajos", "autores_telefono"));
        comboConsultas.addItem(new ConsultaItem("📈 Cantidad de trabajos por autor", "trabajos", "cantidad_por_autor"));
        comboConsultas.addItem(new ConsultaItem("🔢 Número total de trabajos enviados", "trabajos", "total_enviados"));
        comboConsultas.addItem(new ConsultaItem("🏆 Trabajos más populares", "trabajos", "mas_populares"));
        comboConsultas.addItem(new ConsultaItem("👥 Autores con más de un trabajo", "trabajos", "autores_multiples"));
        comboConsultas.addItem(new ConsultaItem("📝 Trabajos con resumen largo (>300)", "trabajos", "resumen_largo"));
        comboConsultas.addItem(new ConsultaItem("🔤 Trabajos ordenados por título", "trabajos", "ordenados_titulo"));
        comboConsultas.addItem(new ConsultaItem("❌ Autores sin trabajos registrados", "trabajos", "autores_sin_trabajos"));
        comboConsultas.addItem(new ConsultaItem("🎤 Trabajos de autores ponentes", "trabajos", "autores_ponentes"));
        
        // === COMITÉS (4 consultas) ===
        comboConsultas.addItem(new ConsultaItem("👨‍⚖️ Comité de selección encargado", "comites", "comite_seleccion"));
        comboConsultas.addItem(new ConsultaItem("📋 Miembros del comité de selección", "comites", "miembros_seleccion"));
        comboConsultas.addItem(new ConsultaItem("🏛️ Miembros del comité organizador", "comites", "miembros_organizador"));
        comboConsultas.addItem(new ConsultaItem("🎤 Ponentes miembros del comité", "comites", "ponentes_comite"));
        
        // === SESIONES (11 consultas) ===
        comboConsultas.addItem(new ConsultaItem("📅 Información de las sesiones", "sesiones", "info_sesiones"));
        comboConsultas.addItem(new ConsultaItem("🏢 Salas designadas por sesión", "sesiones", "salas_sesion"));
        comboConsultas.addItem(new ConsultaItem("📆 Sesiones por día específico", "sesiones", "por_dia"));
        comboConsultas.addItem(new ConsultaItem("🎤 Lista de ponentes por sesión", "sesiones", "ponentes_sesion"));
        comboConsultas.addItem(new ConsultaItem("📋 Ponentes y trabajos por sesión", "sesiones", "ponentes_trabajos"));
        comboConsultas.addItem(new ConsultaItem("👤 Sesiones por chairman específico", "sesiones", "por_chairman"));
        comboConsultas.addItem(new ConsultaItem("ℹ️ Chairman de sesión específica", "sesiones", "chairman_sesion"));
        comboConsultas.addItem(new ConsultaItem("🏛️ Sesiones moderadas por comité", "sesiones", "moderadas_comite"));
        comboConsultas.addItem(new ConsultaItem("❌ Sesiones sin chairman designado", "sesiones", "sin_chairman"));
        comboConsultas.addItem(new ConsultaItem("🏢 Sesiones sin sala asignada", "sesiones", "sin_sala"));
        comboConsultas.addItem(new ConsultaItem("📉 Sesiones con menos de 3 trabajos", "sesiones", "menos_tres"));
        comboConsultas.addItem(new ConsultaItem("🗓️ Sesiones ordenadas por fecha/hora", "sesiones", "ordenadas_fecha"));
        
        // === ESTADÍSTICAS Y ANÁLISIS ===
        comboConsultas.addItem(new ConsultaItem("📊 Sesiones con más trabajos asignados", "estadisticas", "sesiones_mas_trabajos"));
        comboConsultas.addItem(new ConsultaItem("📈 Trabajos de la sesión más popular", "estadisticas", "trabajos_sesion_popular"));
        comboConsultas.addItem(new ConsultaItem("🎯 Sesiones por área de investigación", "estadisticas", "sesiones_area"));
        comboConsultas.addItem(new ConsultaItem("❌ Autores que no son ponentes", "estadisticas", "autores_no_ponentes"));
        comboConsultas.addItem(new ConsultaItem("🔄 Sesiones en día y sala específicos", "estadisticas", "sesiones_dia_sala"));
        comboConsultas.addItem(new ConsultaItem("📊 Asistentes ordenados por apellido", "estadisticas", "asistentes_apellido"));
        comboConsultas.addItem(new ConsultaItem("🏷️ Autores con nombres comunes", "estadisticas", "nombres_comunes"));
    }

    /**
     * Iniciar monitoreo del sistema
     */
    private void iniciarMonitoreo() {
        timerConexion = new Timer(5000, e -> {
            boolean conectado = ConexionBD.verificarConexion();
            lblEstadoSistema.setText(conectado ? "🟢 Sistema Operativo" : "🔴 Error de Conexión");
            lblEstadoSistema.setForeground(conectado ? new Color(34, 197, 94) : new Color(239, 68, 68));
        });
        timerConexion.start();
        timerConexion.getActionListeners()[0].actionPerformed(null);
    }

    /**
     * Manejar selección de consulta
     */
    private void onConsultaSeleccionada() {
        ConsultaItem item = (ConsultaItem) comboConsultas.getSelectedItem();
        if (item != null && !item.categoria.isEmpty()) {
            btnEjecutar2.setEnabled(true);
        } else {
            btnEjecutar2.setEnabled(false);
        }
    }

    /**
     * Ejecutar la consulta seleccionada
     */
    private void ejecutarConsulta() {
        ConsultaItem item = (ConsultaItem) comboConsultas.getSelectedItem();
        if (item == null || item.categoria.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this, 
                "Por favor selecciona una consulta de la lista", 
                "Información", 
                javax.swing.JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        try {
            btnEjecutar2.setText("⏳ Ejecutando...");
            btnEjecutar2.setEnabled(false);
            
            // Ejecutar según categoría
            switch (item.categoria) {
                case "congresistas":
                    ejecutarConsultaCongresistas(item.tipo);
                    break;
                case "trabajos":
                    ejecutarConsultaTrabajos(item.tipo);
                    break;
                case "comites":
                    ejecutarConsultaComites(item.tipo);
                    break;
                case "sesiones":
                    ejecutarConsultaSesiones(item.tipo);
                    break;
                case "estadisticas":
                    ejecutarConsultaEstadisticas(item.tipo);
                    break;
            }
            
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, 
                "Error al ejecutar consulta: " + e.getMessage(), 
                "Error", 
                javax.swing.JOptionPane.ERROR_MESSAGE);
        } finally {
            btnEjecutar2.setText("🚀 EJECUTAR");
            btnEjecutar2.setEnabled(true);
        }
    }


/**
 * Manejar selección de consulta
 */


/**
     * IMPLEMENTAR EL BOTÓN EJECUTAR
     */

/**
 * Ejecutar consultas de congresistas
 */
private void ejecutarConsultaCongresistas(String tipo) {
        List<CongresistaResponseDTO> resultados;
        String titulo;
        
        switch (tipo) {
            case "potenciales_asistentes":
            case "info_basica":
                resultados = sistema.getCongresistas().obtenerTodosCongresistas();
                titulo = "Información de Congresistas";
                break;
            case "con_telefono":
                resultados = sistema.getCongresistas().obtenerCongresistasConTelefono();
                titulo = "Congresistas con Teléfono Móvil";
                break;
            case "comite_organizador":
                resultados = sistema.getCongresistas().obtenerMiembrosComiteOrganizador();
                titulo = "Miembros del Comité Organizador";
                break;
            default:
                mostrarMensaje("Esta consulta será implementada próximamente");
                return;
        }
        
        mostrarResultadosCongresistas(resultados, titulo);
    }

/**
 * Ejecutar consultas de trabajos
 */
private void ejecutarConsultaTrabajos(String tipo) {
        List<TrabajoResponseDTO> resultados;
        String titulo;
        
        switch (tipo) {
            case "seleccionados":
                resultados = sistema.getTrabajos().obtenerTrabajosSeleccionados();
                titulo = "Trabajos Seleccionados para Presentación";
                break;
            case "sin_revisar":
                resultados = sistema.getTrabajos().obtenerTrabajosSinRevisar();
                titulo = "Trabajos Sin Revisar";
                break;
            case "total_enviados":
                Long total = sistema.getTrabajos().contarTotalTrabajos();
                mostrarMensaje("Total de trabajos enviados: " + total);
                return;
            case "ordenados_titulo":
                resultados = sistema.getTrabajos().obtenerTrabajosOrdenadosPorTitulo();
                titulo = "Trabajos Ordenados por Título";
                break;
            default:
                mostrarMensaje("Esta consulta será implementada próximamente");
                return;
        }
        
        mostrarResultadosTrabajos(resultados, titulo);
    }

/**
 * Ejecutar consultas de comités
 */
private void ejecutarConsultaComites(String tipo) {
        switch (tipo) {
            case "miembros_seleccion":
            case "miembros_organizador":
                List<CongresistaResponseDTO> miembros = sistema.getCongresistas().obtenerMiembrosComiteOrganizador();
                mostrarResultadosCongresistas(miembros, "Miembros del Comité");
                break;
            default:
                mostrarMensaje("Esta consulta será implementada próximamente");
        }
    }

/**
 * Ejecutar consultas de sesiones
 */
private void ejecutarConsultaSesiones(String tipo) {
        List<SesionResponseDTO> resultados;
        String titulo;
        
        switch (tipo) {
            case "info_sesiones":
                resultados = sistema.getSesiones().obtenerTodasSesiones();
                titulo = "Información de Todas las Sesiones";
                break;
            case "sin_chairman":
                resultados = sistema.getSesiones().obtenerSesionesSinChairman();
                titulo = "Sesiones Sin Chairman Designado";
                break;
            case "sin_sala":
                resultados = sistema.getSesiones().obtenerSesionesSinSalaAsignada();
                titulo = "Sesiones Sin Sala Asignada";
                break;
            case "menos_tres":
                resultados = sistema.getSesiones().obtenerSesionesConMenosDeTresTrabajos();
                titulo = "Sesiones con Menos de 3 Trabajos";
                break;
            case "ordenadas_fecha":
                resultados = sistema.getSesiones().obtenerSesionesOrdenadasPorFechaHora();
                titulo = "Sesiones Ordenadas por Fecha y Hora";
                break;
            default:
                mostrarMensaje("Esta consulta será implementada próximamente");
                return;
        }
        
        mostrarResultadosSesiones(resultados, titulo);
    }

/**
 * Ejecutar consultas de estadísticas
 */
private void ejecutarConsultaEstadisticas(String tipo) {
        switch (tipo) {
            case "sesiones_mas_trabajos":
                List<ConsultaResultadoDTO> sesionesPopulares = sistema.getSesiones().obtenerSesionesConMasTrabajos();
                mostrarResultadosEstadisticas(sesionesPopulares, "Sesiones con Más Trabajos");
                break;
            case "autores_multiples":
                List<ConsultaResultadoDTO> autoresActivos = sistema.getTrabajos().obtenerAutoresConMasTrabajos();
                mostrarResultadosEstadisticas(autoresActivos, "Autores con Múltiples Trabajos");
                break;
            case "trabajos_sesion_popular":
                List<ConsultaResultadoDTO> trabajosPopulares = sistema.getTrabajos().obtenerTrabajosPopulares();
                mostrarResultadosEstadisticas(trabajosPopulares, "Trabajos Más Populares");
                break;
            default:
                mostrarMensaje("Esta consulta será implementada próximamente");
        }
    }

/**
 * Mostrar resultados de congresistas
 */
private void mostrarResultadosCongresistas(List<CongresistaResponseDTO> congresistas, String titulo) {
        modeloTabla.setRowCount(0);
        modeloTabla.setColumnCount(0);
        
        // Columnas
        modeloTabla.addColumn("ID");
        modeloTabla.addColumn("Nombre");
        modeloTabla.addColumn("Apellido");
        modeloTabla.addColumn("Institución");
        modeloTabla.addColumn("Email");
        modeloTabla.addColumn("Teléfono");
        modeloTabla.addColumn("Miembro Comité");
        modeloTabla.addColumn("Organizador");
        
        // Datos
        for (CongresistaResponseDTO c : congresistas) {
            Object[] fila = {
                c.getCongresistaId(),
                c.getNombre(),
                c.getPrimerApellido(),
                c.getInstitucionAfiliada(),
                c.getEmail(),
                c.getTelefonoMovil() != null ? c.getTelefonoMovil() : "N/A",
                c.isEsMiembroComite() ? "Sí" : "No",
                c.isEsOrganizador() ? "Sí" : "No"
            };
            modeloTabla.addRow(fila);
        }
        
        lblTotalRegistros.setText("Total: " + congresistas.size() + " registros");
        mostrarMensaje(titulo + " - " + congresistas.size() + " resultados encontrados");
    }

/**
 * Mostrar resultados de trabajos
 */
private void mostrarResultadosTrabajos(List<TrabajoResponseDTO> trabajos, String titulo) {
    modeloTabla.setRowCount(0);
    modeloTabla.setColumnCount(0);
    
    // Columnas
    modeloTabla.addColumn("ID");
    modeloTabla.addColumn("Título");
    modeloTabla.addColumn("Estado");
    modeloTabla.addColumn("Fecha Envío");
    modeloTabla.addColumn("Palabras Clave");
    modeloTabla.addColumn("Palabras Resumen");
    
    // Datos
    for (TrabajoResponseDTO t : trabajos) {
        Object[] fila = {
            t.getTrabajoId(),
            t.getTitulo(),
            t.getEstado(),
            t.getFechaEnvio(),
            t.getPalabrasClave() != null ? t.getPalabrasClave() : "N/A",
            t.getCantidadPalabrasResumen()
        };
        modeloTabla.addRow(fila);
    }
    
    lblTotalRegistros.setText("Total: " + trabajos.size() + " registros");
    mostrarMensaje(titulo + " - " + trabajos.size() + " resultados encontrados");
}

/**
 * Mostrar resultados de sesiones
 */
private void mostrarResultadosSesiones(List<SesionResponseDTO> sesiones, String titulo) {
    modeloTabla.setRowCount(0);
    modeloTabla.setColumnCount(0);
    
    // Columnas
    modeloTabla.addColumn("ID");
    modeloTabla.addColumn("Nombre Sesión");
    modeloTabla.addColumn("Fecha");
    modeloTabla.addColumn("Hora Inicio");
    modeloTabla.addColumn("Hora Final");
    modeloTabla.addColumn("Estado");
    modeloTabla.addColumn("Sala");
    modeloTabla.addColumn("Duración (min)");
    
    // Datos
    for (SesionResponseDTO s : sesiones) {
        Object[] fila = {
            s.getSesionId(),
            s.getNombreSesion(),
            s.getFecha(),
            s.getHoraInicio(),
            s.getHoraFinal(),
            s.getEstado(),
            s.getSalaId() != null ? "Sala " + s.getSalaId() : "Sin asignar",
            s.getDuracionMinutos()
        };
        modeloTabla.addRow(fila);
    }
    
    lblTotalRegistros.setText("Total: " + sesiones.size() + " registros");
    mostrarMensaje(titulo + " - " + sesiones.size() + " resultados encontrados");
}

/**
 * Mostrar resultados de estadísticas
 */
private void mostrarResultadosEstadisticas(List<ConsultaResultadoDTO> estadisticas, String titulo) {
    modeloTabla.setRowCount(0);
        modeloTabla.setColumnCount(0);
        
        // Columnas dinámicas según el tipo de estadística
        modeloTabla.addColumn("ID");
        modeloTabla.addColumn("Descripción");
        modeloTabla.addColumn("Cantidad");
        modeloTabla.addColumn("Detalles");
        
        // Datos
        for (ConsultaResultadoDTO e : estadisticas) {
            Object[] fila = {
                e.getCongresistaId() != null ? e.getCongresistaId() : 
                e.getTrabajoId() != null ? e.getTrabajoId() : 
                e.getSesionId() != null ? e.getSesionId() : "-",
                e.getNombreCongresista() != null ? e.getNombreCongresista() :
                e.getTituloTrabajo() != null ? e.getTituloTrabajo() :
                e.getTituloSesion() != null ? e.getTituloSesion() : "N/A",
                e.getCantidad(),
                e.getDescripcion()
            };
            modeloTabla.addRow(fila);
    }
    
    lblTotalRegistros.setText("Total: " + estadisticas.size() + " registros");
    mostrarMensaje(titulo + " - " + estadisticas.size() + " resultados encontrados");
}

/**
 * Mostrar mensaje informativo
 */
private void mostrarMensaje(String mensaje) {
    javax.swing.JOptionPane.showMessageDialog(this, mensaje, "Información", javax.swing.JOptionPane.INFORMATION_MESSAGE);
}

/**
 * Clase interna para items del combo
 */
private static class ConsultaItem {
    String nombre;
    String categoria;
    String tipo;
    
    public ConsultaItem(String nombre, String categoria, String tipo) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.tipo = tipo;
    }
    
    @Override
    public String toString() {
        return nombre;
    }
}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        comboConsultas = new javax.swing.JComboBox<>();
        btnEjecutar2 = new javax.swing.JButton();
        tabla = new javax.swing.JScrollPane();
        tablaResultados = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        lblEstadoSistema = new javax.swing.JLabel();
        lblTotalRegistros = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sistema Congreso");
        setPreferredSize(new java.awt.Dimension(1200, 800));

        jLabel1.setText("Consulta:");

        comboConsultas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboConsultasActionPerformed(evt);
            }
        });

        btnEjecutar2.setText("🚀 EJECUTAR");
        btnEjecutar2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEjecutar2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(218, 218, 218)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(comboConsultas, javax.swing.GroupLayout.PREFERRED_SIZE, 396, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnEjecutar2)
                .addContainerGap(136, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboConsultas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEjecutar2))
                .addContainerGap(34, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.PAGE_START);

        tablaResultados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tabla.setViewportView(tablaResultados);

        getContentPane().add(tabla, java.awt.BorderLayout.CENTER);

        lblEstadoSistema.setText("Estado del sistema");

        lblTotalRegistros.setText("Total: 0 registros");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(120, 120, 120)
                .addComponent(lblEstadoSistema, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(253, 253, 253)
                .addComponent(lblTotalRegistros, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(365, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEstadoSistema)
                    .addComponent(lblTotalRegistros))
                .addContainerGap(43, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel2, java.awt.BorderLayout.PAGE_END);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnEjecutar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEjecutar2ActionPerformed
        ejecutarConsulta();
    }//GEN-LAST:event_btnEjecutar2ActionPerformed

    private void comboConsultasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboConsultasActionPerformed
        ConsultaItem item = (ConsultaItem) comboConsultas.getSelectedItem();
        if (item != null && !item.categoria.isEmpty()) {
            btnEjecutar2.setEnabled(true);
        } else {
            btnEjecutar2.setEnabled(false);
        }
    }//GEN-LAST:event_comboConsultasActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(DashboardPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DashboardPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DashboardPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DashboardPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DashboardPrincipal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEjecutar2;
    private javax.swing.JComboBox<ConsultaItem> comboConsultas;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblEstadoSistema;
    private javax.swing.JLabel lblTotalRegistros;
    private javax.swing.JScrollPane tabla;
    private javax.swing.JTable tablaResultados;
    // End of variables declaration//GEN-END:variables
    private SistemaController sistema;
    private DefaultTableModel modeloTabla;
    private Timer timerConexion;



}
