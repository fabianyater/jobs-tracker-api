package com.api.jobstracker.servicios.impl;

import com.api.jobstracker.dominio.dto.*;
import com.api.jobstracker.dominio.modelo.*;
import com.api.jobstracker.repositorios.*;
import com.api.jobstracker.servicios.PostulacionesServicio;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.*;

@Service
@Transactional
@AllArgsConstructor
public class PostulacionesServicioImpl implements PostulacionesServicio {
    private static final Logger logger = LoggerFactory.getLogger(PostulacionesServicioImpl.class);
    private final EmpresaRepositorio empresaRepositorio;
    private final PuestoRepositorio puestoRepositorio;
    private final PostulacionRepositorio postulacionRepositorio;
    private final PostulacionEstadoRepositorio postulacionEstadoRepositorio;
    private final EstadoRepositorio estadoRepositorio;


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

        ZonedDateTime fechaActualizacion = ZonedDateTime.now(ZoneId.of("America/Bogota"));
        ZonedDateTime fechaPostulacion = ZonedDateTime.now(ZoneId.of("America/Bogota"));


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
        Map<Integer, ZonedDateTime> fechaActualizacionEstados = new HashMap<>();

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
        postulacionesEstado.setFechaActualizacion(ZonedDateTime.now());

        postulacionEstadoRepositorio.save(postulacionesEstado);
    }

    @Override
    public List<PostulacionTimelineRespuesta> obtenerPostulacionesTimeline(Integer postulacionId) {
        if (postulacionId == null) {
            logger.error("El ID de postulación es nulo");
            return Collections.emptyList(); // Retorna una lista vacía o maneja el error como prefieras
        }

        List<Object[]> resultados = postulacionRepositorio.findEstadosByPostulacionId(postulacionId);

        if (resultados.isEmpty()) {
            logger.warn("No se encontraron estados para la postulación con ID: {}", postulacionId);
            return Collections.emptyList(); // Igualmente, puedes decidir cómo manejar esta situación
        }

        List<PostulacionTimelineRespuesta> timelineRespuestas = new ArrayList<>();

        for (Object[] resultado : resultados) {
            PostulacionTimelineRespuesta dto = new PostulacionTimelineRespuesta();

            try {
                dto.setEstado((String) resultado[0]);
            } catch (ClassCastException e) {
                logger.error("Error al castear el estado a String", e);
                continue;
            }

            try {
                Instant instant;
                if (resultado[1] instanceof java.sql.Timestamp timestamp) {
                    instant = timestamp.toInstant();
                } else if (resultado[1] instanceof Instant instant1) {
                    instant = instant1;
                } else {
                    throw new IllegalArgumentException("Tipo de dato no soportado para la conversión: "
                            + resultado[1].getClass().getName());
                }

                ZonedDateTime zonedDateTime = instant.atZone(ZoneId.of("America/Bogota"));
                dto.setFechaActualizacion(zonedDateTime.toLocalDateTime().toString());
            } catch (IllegalArgumentException e) {
                logger.error("Error al convertir la fecha: {}", e.getMessage());
            } catch (Exception e) {
                logger.error("Error inesperado al procesar la fecha: ", e);
            }

            // Casting para el color
            try {
                dto.setColor(resultado[2].toString()); // Aquí asumimos que siempre es posible llamar toString(), pero podría necesitar un manejo similar si esperas un tipo específico
            } catch (Exception e) {
                logger.error("Error al obtener el color", e);
                continue; // Salta esta iteración del bucle
            }

            timelineRespuestas.add(dto);
        }

        return timelineRespuestas
                .stream()
                .sorted((o1, o2) -> o2.getFechaActualizacion().compareTo(o1.getFechaActualizacion()))
                .toList();
    }
}
