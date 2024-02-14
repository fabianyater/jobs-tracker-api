package com.api.jobstracker.servicios.impl;

import com.api.jobstracker.dominio.dto.ComentarioRespuesta;
import com.api.jobstracker.dominio.dto.ComentarioSolicitud;
import com.api.jobstracker.dominio.mapeador.ComentarioRespuestaMapper;
import com.api.jobstracker.dominio.modelo.Comentario;
import com.api.jobstracker.dominio.modelo.Postulacion;
import com.api.jobstracker.repositorios.ComentarioRepositorio;
import com.api.jobstracker.repositorios.PostulacionRepositorio;
import com.api.jobstracker.servicios.ComentarioServicio;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class ComentarioServicioImpl implements ComentarioServicio {
    private final ComentarioRepositorio comentarioRepositorio;
    private final PostulacionRepositorio postulacionRepositorio;
    private final ComentarioRespuestaMapper comentarioRespuestaMapper;


    @Override
    public ComentarioRespuesta agregarComentario(ComentarioSolicitud comentarioSolicitud) {
        Postulacion postulacion = postulacionRepositorio.findById(comentarioSolicitud.getPostulacionId())
                .orElseThrow(() -> new RuntimeException("Postulación no encontrada con el id: " + comentarioSolicitud.getPostulacionId()));

        Comentario comentario = new Comentario();
        comentario.setComentario(comentarioSolicitud.getComentario());
        comentario.setPostulacion(postulacion);
        comentario.setFechaPublicacion(LocalDateTime.now());

        comentarioRepositorio.save(comentario);

        return comentarioRespuestaMapper.mapearAComentarionRespuesta(comentario);
    }

    @Override
    public List<ComentarioRespuesta> listarComentarios(Integer postulacionId) {
        List<Comentario> comentarios = comentarioRepositorio.findAll();
        return comentarios.stream()
                .filter(comentario -> comentario.getPostulacion().getId().equals(postulacionId))
                .map(comentario -> {
                    ComentarioRespuesta comentarioRespuesta = new ComentarioRespuesta();
                    comentarioRespuesta.setComentario(comentario.getComentario());
                    comentarioRespuesta.setFechaPublicacion(comentario.getFechaPublicacion().toString());

                    return comentarioRespuesta;
                })
                .toList();
    }
}
