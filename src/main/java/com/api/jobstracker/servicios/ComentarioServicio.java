package com.api.jobstracker.servicios;

import com.api.jobstracker.dominio.dto.ComentarioRespuesta;
import com.api.jobstracker.dominio.dto.ComentarioSolicitud;

import java.util.List;

public interface ComentarioServicio {
    ComentarioRespuesta agregarComentario(ComentarioSolicitud comentarioSolicitud);
    List<ComentarioRespuesta> listarComentarios(Integer postulacionId);
}
