package com.api.jobstracker.servicios.impl;

import com.api.jobstracker.dominio.dto.ComentarioSolicitud;
import com.api.jobstracker.dominio.modelo.Comentario;
import com.api.jobstracker.dominio.modelo.Postulacion;
import com.api.jobstracker.repositorios.ComentarioRepositorio;
import com.api.jobstracker.repositorios.PostulacionRepositorio;
import com.api.jobstracker.servicios.ComentarioServicio;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@AllArgsConstructor
public class ComentarioServicioImpl implements ComentarioServicio {
    private final ComentarioRepositorio comentarioRepositorio;
    private final PostulacionRepositorio postulacionRepositorio;


    @Override
    public void agregarComentario( ComentarioSolicitud comentarioSolicitud) {
        Postulacion postulacion = postulacionRepositorio.findById(comentarioSolicitud.getPostulacionId())
                .orElseThrow(() -> new RuntimeException("Postulación no encontrada con el id: " + comentarioSolicitud.getPostulacionId()));

        Comentario comentario = new Comentario();
        comentario.setComentario(comentarioSolicitud.getComentario());
        comentario.setPostulacion(postulacion);

        comentarioRepositorio.save(comentario);
    }
}
