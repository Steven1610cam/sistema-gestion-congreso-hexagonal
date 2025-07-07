package com.danielarroyo.congreso.casosdeuso.puertos.salida;

import com.danielarroyo.congreso.infraestructura.dto.ConsultaResultadoDTO;
import java.time.LocalDate;
import java.util.List;

/**
 * PUERTO DE SALIDA - Consultas avanzadas y complejas
 * Repository especializado para las 43 consultas específicas
 * @author Daniel Arroyo
 */
public interface ConsultasAvanzadasRepository {
    
    // ===== CONSULTAS SOBRE CONGRESISTAS =====
    List<ConsultaResultadoDTO> obtenerPotencialesAsistentes();
    List<ConsultaResultadoDTO> obtenerAsistentesSinRegistro();
    List<ConsultaResultadoDTO> obtenerAsistentesConRegistroSinTrabajos();
    List<ConsultaResultadoDTO> obtenerAsistentesSinEmail();
    List<ConsultaResultadoDTO> obtenerCongresistasOrdenadosPorApellido();
    List<ConsultaResultadoDTO> obtenerCongresistasConNombreComun();
    List<ConsultaResultadoDTO> obtenerCongresistasQueEmpiezanConLetra(String letra);
    List<ConsultaResultadoDTO> obtenerCongresistasRegistradosDespuesDeFecha(LocalDate fecha);
    
    // ===== CONSULTAS SOBRE TRABAJOS =====
    List<ConsultaResultadoDTO> obtenerTrabajosPorAutorEspecifico(String nombreAutor);
    List<ConsultaResultadoDTO> obtenerTrabajosSeleccionadosParaPresentacion();
    List<ConsultaResultadoDTO> obtenerTrabajosSeleccionadosPorSesion(Integer sesionId);
    List<ConsultaResultadoDTO> obtenerTrabajosSinRevisarPorComite();
    List<ConsultaResultadoDTO> obtenerTrabajosSeleccionadosPorInstitucion(String institucion);
    List<ConsultaResultadoDTO> obtenerTrabajosConPalabrasClave(String palabrasClave);
    List<ConsultaResultadoDTO> obtenerTrabajosDeAutoresConTelefono();
    List<ConsultaResultadoDTO> obtenerTrabajosDeAutoresPorInstitucion(String institucion);
    List<ConsultaResultadoDTO> obtenerTrabajosConResumenLargo(int minimoCaracteres);
    List<ConsultaResultadoDTO> obtenerTrabajosOrdenadosPorTitulo();
    
    // ===== CONSULTAS SOBRE SESIONES =====
    List<ConsultaResultadoDTO> obtenerInformacionSesiones();
    List<ConsultaResultadoDTO> obtenerSalasDesignadasPorSesion();
    List<ConsultaResultadoDTO> obtenerSesionesPorDiaEspecifico(LocalDate fecha);
    List<ConsultaResultadoDTO> obtenerSesionesSinChairman();
    List<ConsultaResultadoDTO> obtenerSesionesSinSalaAsignada();
    List<ConsultaResultadoDTO> obtenerSesionesConMenosDeTresTrabajos();
    List<ConsultaResultadoDTO> obtenerSesionesPorFechaYSala(LocalDate fecha, Integer salaId);
    List<ConsultaResultadoDTO> obtenerSesionesOrdenadasPorFechaHora();
    
    // ===== CONSULTAS SOBRE PONENTES Y CHAIRMAN =====
    List<ConsultaResultadoDTO> obtenerPonentesDesignadosPorSesion();
    List<ConsultaResultadoDTO> obtenerPonentesYTrabajosParaSesion(Integer sesionId);
    List<ConsultaResultadoDTO> obtenerSesionesModadasPorChairman(Integer chairmanId);
    List<ConsultaResultadoDTO> obtenerChairmanDeSesionEspecifica(Integer sesionId);
    List<ConsultaResultadoDTO> obtenerAsistentesQueEsChairman();
    List<ConsultaResultadoDTO> obtenerAutoresQueNoEsPonentes();
    List<ConsultaResultadoDTO> obtenerPonentesQueEsMiembrosComiteOrganizador();
    List<ConsultaResultadoDTO> obtenerAutoresQueEsPonenteEnOtrasSesiones();
    List<ConsultaResultadoDTO> obtenerSesionesSinPonenteDesignado();
    
    // ===== CONSULTAS SOBRE COMITÉS =====
    List<ConsultaResultadoDTO> obtenerComiteSeleccionRevisores();
    List<ConsultaResultadoDTO> obtenerMiembrosComiteSeleccion();
    List<ConsultaResultadoDTO> obtenerSesionesModadasPorMiembrosComiteOrganizador();
    List<ConsultaResultadoDTO> obtenerMiembrosComiteOrganizador();
    
    // ===== CONSULTAS DE ESTADÍSTICAS =====
    List<ConsultaResultadoDTO> obtenerCantidadTrabajosPorAutor();
    List<ConsultaResultadoDTO> obtenerTotalTrabajosEnviados();
    List<ConsultaResultadoDTO> obtenerAutoresConMasDeUnTrabajo();
    List<ConsultaResultadoDTO> obtenerSesionesConMasTrabajos();
    List<ConsultaResultadoDTO> obtenerTrabajosEnSesionConMayorCantidad();
    List<ConsultaResultadoDTO> obtenerSesionesConAreaInvestigacionEspecifica(String areaInvestigacion);
}