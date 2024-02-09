package com.api.jobstracker.servicios.impl;

import com.api.jobstracker.dominio.dto.ComentarioRespuesta;
import com.api.jobstracker.dominio.dto.EstadoSolicitud;
import com.api.jobstracker.dominio.dto.PostulacionRespuesta;
import com.api.jobstracker.dominio.dto.PostulacionSolicitud;
import com.api.jobstracker.dominio.modelo.*;
import com.api.jobstracker.repositorios.*;
import com.api.jobstracker.servicios.ComentarioServicio;
import com.api.jobstracker.servicios.PostulacionesServicio;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class PostulacionesServicioImpl implements PostulacionesServicio {
    private final EmpresaRepositorio empresaRepositorio;
    private final PuestoRepositorio puestoRepositorio;
    private final PostulacionRepositorio postulacionRepositorio;
    private final PostulacionEstadoRepositorio postulacionEstadoRepositorio;
    private final EstadoRepositorio estadoRepositorio;
    private final ComentarioRepositorio comentarioRepositorio;

    @Override
    public void agregarPostulacion(PostulacionSolicitud postulacionSolicitud) {
        String nombreEmpresa = postulacionSolicitud.getNombreEmpresa();
        String nombrePuesto = postulacionSolicitud.getTituloPuesto();
        String url = postulacionSolicitud.getUrl();

        Empresa empresa = empresaRepositorio.findByNombre(nombreEmpresa)
                .orElseGet(() -> {
                    Empresa nuevaEmpresa = new Empresa();
                    nuevaEmpresa.setNombre(nombreEmpresa);

                    return empresaRepositorio.save(nuevaEmpresa);
                });

        Puesto puesto = puestoRepositorio.findByTitulo(nombrePuesto)
                .orElseGet(() -> {
                    Puesto nuevoPuesto = new Puesto();
                    nuevoPuesto.setTitulo(nombrePuesto);
                    return puestoRepositorio.save(nuevoPuesto);
                });

        Estado estado = estadoRepositorio.findById(1).orElseThrow(() -> new RuntimeException("Estado no encontrado"));

        Postulacion postulacion = new Postulacion();

        postulacion.setEmpresa(empresa);
        postulacion.setPuesto(puesto);
        postulacion.setUrl(url);
        postulacion.setFechaPostulacion(LocalDate.now());

        postulacionRepositorio.save(postulacion);

        PostulacionesEstado postulacionesEstado = new PostulacionesEstado();
        postulacionesEstado.setEstadosEstado(estado);
        postulacionesEstado.setPostulacionIdPostulacion(postulacion);
        postulacionesEstado.setFechaActualizacion(LocalDate.now());

        postulacionEstadoRepositorio.save(postulacionesEstado);
    }

    @Override
    public List<PostulacionRespuesta> listarPostulaciones() {
        List<Postulacion> postulaciones = postulacionRepositorio.findAll();
        List<PostulacionesEstado> postulacionesEstado = postulacionEstadoRepositorio.findAll();
        List<Comentario> comentarios = comentarioRepositorio.findAll();
        Map<Integer, String> postulacionEstados = new HashMap<>();
        Map<Integer, LocalDate> fechaActualizacionEstados = new HashMap<>();

        postulacionesEstado.forEach(pe -> {
            Estado estado = estadoRepositorio.findById(pe.getEstadosEstado().getId()).orElse(null);

            if (estado != null) {
                postulacionEstados.put(pe.getPostulacionIdPostulacion().getId(), estado.getEstado());
                fechaActualizacionEstados.put(pe.getPostulacionIdPostulacion().getId(), pe.getFechaActualizacion());
            }
        });

        return postulaciones.stream().map(postulacion -> {
            PostulacionRespuesta dto = new PostulacionRespuesta();
            dto.setId(postulacion.getId());
            dto.setFechaPostulacion(postulacion.getFechaPostulacion());
            dto.setUrl(postulacion.getUrl());
            dto.setTituloPuesto(postulacion.getPuesto().getTitulo());
            dto.setNombreEmpresa(postulacion.getEmpresa().getNombre());
            dto.setEstado(postulacionEstados.get(postulacion.getId()));
            dto.setFechaActualizacion(fechaActualizacionEstados.get(postulacion.getId()));

            List<ComentarioRespuesta> comentariosFiltrados = comentarios.stream()
                    .filter(comentario -> comentario.getPostulacion().getId().equals(postulacion.getId()))
                    .map(comentario -> new ComentarioRespuesta(comentario.getComentario()))
                    .collect(Collectors.toList());

            dto.setComentarios(comentariosFiltrados);
            return dto;
        }).toList();
    }

    @Override
    public void eliminarPostulacion(Integer postulacionId) {
        Postulacion postulacion = postulacionRepositorio.findById(postulacionId)
                .orElseThrow(() -> new RuntimeException("Postulación no encontrada con el id: " + postulacionId));

        Integer empresaId = postulacion.getEmpresa().getId();
        Integer puestoId = postulacion.getPuesto().getId();

        postulacionRepositorio.delete(postulacion);

        if (postulacionRepositorio.findByEmpresa_Id(empresaId).isEmpty()) {
            empresaRepositorio.deleteById(empresaId);
        }

        if (postulacionRepositorio.findByPuesto_Id(puestoId).isEmpty()) {
            puestoRepositorio.deleteById(puestoId);
        }
    }

    @Override
    public void actualizarEstado(Integer id, EstadoSolicitud estado) {
        Postulacion postulacion = postulacionRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Postulación no encontrada con el id: " + id));

        Estado estadoId = estadoRepositorio.findByEstado(estado.getEstado());

        PostulacionesEstado postulacionesEstado = new PostulacionesEstado();
        postulacionesEstado.setPostulacionIdPostulacion(postulacion);
        postulacionesEstado.setEstadosEstado(estadoId);
        postulacionesEstado.setFechaActualizacion(LocalDate.now());

        postulacionEstadoRepositorio.save(postulacionesEstado);
    }
}
