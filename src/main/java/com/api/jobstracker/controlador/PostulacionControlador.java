package com.api.jobstracker.controlador;

import com.api.jobstracker.dominio.dto.*;
import com.api.jobstracker.dominio.modelo.ApiRespuesta;
import com.api.jobstracker.servicios.PostulacionesServicio;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("postulaciones")
public class PostulacionControlador {
    private final PostulacionesServicio postulacionesServicio;

    @PostMapping
    public ResponseEntity<ApiRespuesta<Void>> agregarPostulacion(@RequestBody PostulacionSolicitud postulacionDTO) {
        postulacionesServicio.agregarPostulacion(postulacionDTO);
        ApiRespuesta<Void> apiRespuesta = ApiRespuesta.created();
        return new ResponseEntity<>(apiRespuesta, apiRespuesta.getStatus());
    }

    @GetMapping()
    public ResponseEntity<ApiRespuesta<PostulacionRespuestaPaginada>> listarTodas(
            @RequestParam(value = "page") int currentPage,
            @RequestParam("items") int itemsPerPage,
            @RequestParam("estado") List<String> estados) {
        PostulacionRespuestaPaginada postulacionesPaginadas = postulacionesServicio.listarPostulaciones(currentPage, itemsPerPage, estados);
        ApiRespuesta<PostulacionRespuestaPaginada> respuesta = ApiRespuesta.ok(postulacionesPaginadas);
        return new ResponseEntity<>(respuesta, respuesta.getStatus());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiRespuesta<Void>> eliminarPostulacion(@PathVariable("id") Integer id) {
        postulacionesServicio.eliminarPostulacion(id);

        ApiRespuesta<Void> respuesta = ApiRespuesta.ok(null);
        return new ResponseEntity<>(respuesta, respuesta.getStatus());
    }

    @PostMapping("/{id}")
    public ResponseEntity<ApiRespuesta<Void>> actualizarEstado(@PathVariable("id") Integer id, @RequestBody EstadoSolicitud estadoSolicitud) {
        postulacionesServicio.actualizarEstado(id, estadoSolicitud);

        ApiRespuesta<Void> respuesta = ApiRespuesta.ok(null);
        return new ResponseEntity<>(respuesta, respuesta.getStatus());
    }

    @GetMapping("/timeline/{id}")
    public ResponseEntity<ApiRespuesta<List<PostulacionTimelineRespuesta>>> listarTimeline(@PathVariable("id") int id) {
        List<PostulacionTimelineRespuesta>  postulacionTimelineRespuestas = postulacionesServicio.obtenerPostulacionesTimeline(id);
        ApiRespuesta<List<PostulacionTimelineRespuesta>> respuesta = ApiRespuesta.ok(postulacionTimelineRespuestas);

        return new ResponseEntity<>(respuesta, respuesta.getStatus());
    }
}
