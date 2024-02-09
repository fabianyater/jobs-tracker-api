package com.api.jobstracker.servicios;

import com.api.jobstracker.dominio.dto.EstadoSolicitud;
import com.api.jobstracker.dominio.dto.PostulacionRespuesta;
import com.api.jobstracker.dominio.dto.PostulacionSolicitud;

import java.util.List;

public interface PostulacionesServicio {
    void agregarPostulacion(PostulacionSolicitud postulacionSolicitud);
    List<PostulacionRespuesta> listarPostulaciones();
    void eliminarPostulacion(Integer postulacionId);
    void actualizarEstado(Integer id, EstadoSolicitud estado);
}
