package com.api.jobstracker.servicios.impl;

import com.api.jobstracker.dominio.dto.EstadoSolicitud;
import com.api.jobstracker.dominio.dto.PostulacionRespuesta;
import com.api.jobstracker.dominio.dto.PostulacionSolicitud;
import com.api.jobstracker.dominio.modelo.Empresa;
import com.api.jobstracker.dominio.modelo.Postulaciones;
import com.api.jobstracker.dominio.modelo.Puesto;
import com.api.jobstracker.repositorios.EmpresaRepositorio;
import com.api.jobstracker.repositorios.PostulacionRepositorio;
import com.api.jobstracker.repositorios.PuestoRepositorio;
import com.api.jobstracker.servicios.PostulacionesServicio;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class PostulacionesServicioImpl implements PostulacionesServicio {
    private final EmpresaRepositorio empresaRepositorio;
    private final PuestoRepositorio puestoRepositorio;
    private final PostulacionRepositorio postulacionRepositorio;

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

        Postulaciones postulacion = new Postulaciones();

        postulacion.setEmpresa(empresa);
        postulacion.setPuesto(puesto);
        postulacion.setUrl(url);
        postulacion.setFechaPostulacion(LocalDate.now());
        postulacion.setEstado("Enviada");

        postulacionRepositorio.save(postulacion);
    }

    @Override
    public List<PostulacionRespuesta> listarPostulaciones() {
        return postulacionRepositorio.findAll().stream().map(postulacion -> {
            PostulacionRespuesta dto = new PostulacionRespuesta();
            dto.setId(postulacion.getId());
            dto.setFechaPostulacion(postulacion.getFechaPostulacion());
            dto.setEstado(postulacion.getEstado());
            dto.setUrl(postulacion.getUrl());
            dto.setTituloPuesto(postulacion.getPuesto().getTitulo());
            dto.setNombreEmpresa(postulacion.getEmpresa().getNombre());
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

        if (!postulacion.getEstado().equals(estado.getEstado())) {
            postulacion.setEstado(estado.getEstado());
            postulacionRepositorio.save(postulacion);
        }
    }
}
