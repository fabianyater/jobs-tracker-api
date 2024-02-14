package com.api.jobstracker.servicios.impl;

import com.api.jobstracker.dominio.dto.*;
import com.api.jobstracker.dominio.modelo.*;
import com.api.jobstracker.repositorios.*;
import com.api.jobstracker.servicios.PostulacionesServicio;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

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

        LocalDateTime fechaActualizacion = ZonedDateTime.now(ZoneId.of("America/Bogota"))
                .withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();
        LocalDateTime fechaPostulacion = ZonedDateTime.now(ZoneId.of("America/Bogota"))
                .withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();

        postulacion.setEmpresa(empresa);
        postulacion.setPuesto(puesto);
        postulacion.setUrl(url);
        postulacion.setFechaPostulacion(fechaPostulacion);

        postulacionRepositorio.save(postulacion);

        PostulacionesEstado postulacionesEstado = new PostulacionesEstado();
        postulacionesEstado.setEstadosEstado(estado);
        postulacionesEstado.setPostulacionIdPostulacion(postulacion);
        postulacionesEstado.setFechaActualizacion(fechaActualizacion);

        postulacionEstadoRepositorio.save(postulacionesEstado);
    }

    @Override
    public PostulacionRespuestaPaginada listarPostulaciones(int currentPage, int itemsPerPage) {
        PostulacionRespuestaPaginada postulacionRespuestaPaginada = new PostulacionRespuestaPaginada();
        List<Postulacion> postulaciones = postulacionRepositorio.findAll();
        List<PostulacionesEstado> postulacionesEstado = postulacionEstadoRepositorio.findAll();
        Map<Integer, String> postulacionEstados = new HashMap<>();
        Map<Integer, LocalDateTime> fechaActualizacionEstados = new HashMap<>();

        postulacionesEstado.forEach(pe -> {
            Estado estado = estadoRepositorio.findById(pe.getEstadosEstado().getId()).orElse(null);

            if (estado != null) {
                postulacionEstados.put(pe.getPostulacionIdPostulacion().getId(), estado.getEstado());
                fechaActualizacionEstados.put(pe.getPostulacionIdPostulacion().getId(), pe.getFechaActualizacion());
            }
        });



        List<PostulacionRespuesta> postulacionRespuestas = postulaciones.stream()
                .map(postulacion -> {
                    PostulacionRespuesta dto = new PostulacionRespuesta();
                    dto.setId(postulacion.getId());
                    dto.setFechaPostulacion(postulacion.getFechaPostulacion().toString());
                    dto.setUrl(postulacion.getUrl());
                    dto.setTituloPuesto(postulacion.getPuesto().getTitulo());
                    dto.setNombreEmpresa(postulacion.getEmpresa().getNombre());
                    dto.setEstado(postulacionEstados.get(postulacion.getId()));
                    dto.setFechaActualizacion(String.valueOf(fechaActualizacionEstados.get(postulacion.getId())));
                    dto.setDescripcion(postulacion.getDescripcion());

                    return dto;
                })
                .sorted((p1, p2) -> p2.getFechaActualizacion().compareTo(p1.getFechaActualizacion()))
                .toList();

        int totalItems = postulacionRespuestas.size();
        int startIndex = (currentPage - 1) * itemsPerPage;

        List<PostulacionRespuesta> paginatedPostulaciones = postulacionRespuestas.stream()
                .skip(startIndex)
                .limit(itemsPerPage)
                .toList();

        postulacionRespuestaPaginada.setCurrentPage(currentPage);
        postulacionRespuestaPaginada.setItemsPerPage(itemsPerPage);
        postulacionRespuestaPaginada.setTotalItems(totalItems);
        postulacionRespuestaPaginada.setPostulaciones(paginatedPostulaciones);

        return postulacionRespuestaPaginada;
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
        postulacionesEstado.setFechaActualizacion(LocalDateTime.now());

        postulacionEstadoRepositorio.save(postulacionesEstado);
    }

    @Override
    public List<PostulacionTimelineRespuesta> obtenerPostulacionesTimeline(Integer postulacionId) {
        List<Object[]> resultados = postulacionRepositorio.findEstadosByPostulacionId(postulacionId);
        List<PostulacionTimelineRespuesta> timelineRespuestas = new ArrayList<>();

        for(Object[] resultado : resultados) {
            PostulacionTimelineRespuesta dto = new PostulacionTimelineRespuesta();
            dto.setEstado((String) resultado[0]);

            Timestamp timestamp = (Timestamp) resultado[1];
            dto.setFechaActualizacion(timestamp.toLocalDateTime().toString());
            dto.setColor(resultado[2].toString());

            timelineRespuestas.add(dto);
        }

        return timelineRespuestas
                .stream()
                .sorted((o1, o2) -> o2.getFechaActualizacion().compareTo(o1.getFechaActualizacion()))
                .toList();
    }
}
