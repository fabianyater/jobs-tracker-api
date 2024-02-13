package com.api.jobstracker.servicios;

import com.api.jobstracker.dominio.dto.*;

import java.util.List;

public interface PostulacionesServicio {
    void agregarPostulacion(PostulacionSolicitud postulacionSolicitud);
    PostulacionRespuestaPaginada listarPostulaciones(int currentPage, int itemsPerPage);
    void eliminarPostulacion(Integer postulacionId);
    void actualizarEstado(Integer id, EstadoSolicitud estado);
    List<PostulacionTimelineRespuesta> obtenerPostulacionesTimeline(Integer postulacionId);
}
