package com.api.jobstracker.servicios.impl;

import com.api.jobstracker.dominio.dto.EstadoSolicitud;
import com.api.jobstracker.dominio.dto.PostulacionRespuesta;
import com.api.jobstracker.dominio.dto.PostulacionSolicitud;
import com.api.jobstracker.dominio.modelo.*;
import com.api.jobstracker.repositorios.*;
import com.api.jobstracker.servicios.PostulacionesServicio;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

        Postulaciones postulacion = new Postulaciones();

        postulacion.setEmpresa(empresa);
        postulacion.setPuesto(puesto);
        postulacion.setUrl(url);
        postulacion.setFechaPostulacion(LocalDate.now());

        postulacionRepositorio.save(postulacion);

        PostulacionesEstado postulacionesEstado = new PostulacionesEstado();
        postulacionesEstado.setEstadosEstado(estado);
        postulacionesEstado.setPostulacionesIdPostulacion(postulacion);
        postulacionesEstado.setFechaActualizacion(LocalDate.now());

        postulacionEstadoRepositorio.save(postulacionesEstado);
    }

    @Override
    public List<PostulacionRespuesta> listarPostulaciones() {
        List<Postulaciones> postulaciones = postulacionRepositorio.findAll();
        List<PostulacionesEstado> postulacionesEstado = postulacionEstadoRepositorio.findAll();
        Map<Integer, String> postulacionEstados = new HashMap<>();

        postulacionesEstado.forEach(pe -> {
            Estado estado = estadoRepositorio.findById(pe.getEstadosEstado().getId()).orElse(null);
            if (estado != null) {
                postulacionEstados.put(pe.getPostulacionesIdPostulacion().getId(), estado.getEstado());
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
            return dto;
        }).toList();
    }

    @Override
    public void eliminarPostulacion(Integer postulacionId) {
        Postulaciones postulacion = postulacionRepositorio.findById(postulacionId)
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
        Postulaciones postulacion = postulacionRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Postulación no encontrada con el id: " + id));

        Estado estadoId = estadoRepositorio.findByEstado(estado.getEstado());

        PostulacionesEstado postulacionesEstado = postulacionEstadoRepositorio
                .findByPostulacionesIdPostulacion_Id(postulacion.getId());

        postulacionesEstado.setEstadosEstado(estadoId);

        postulacionEstadoRepositorio.save(postulacionesEstado);
    }
}
