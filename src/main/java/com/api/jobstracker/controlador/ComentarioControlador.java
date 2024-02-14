package com.api.jobstracker.controlador;

import com.api.jobstracker.dominio.dto.ComentarioRespuesta;
import com.api.jobstracker.dominio.dto.ComentarioSolicitud;
import com.api.jobstracker.dominio.modelo.ApiRespuesta;
import com.api.jobstracker.servicios.ComentarioServicio;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("comentarios")
public class ComentarioControlador {
    private final ComentarioServicio comentarioServicio;

    @PostMapping
    public ResponseEntity<ApiRespuesta<ComentarioRespuesta>> agregarComentario(@RequestBody ComentarioSolicitud comentarioSolicitud) {
        ComentarioRespuesta comentarioRespuesta = comentarioServicio.agregarComentario(comentarioSolicitud);
        ApiRespuesta<ComentarioRespuesta> apiRespuesta = ApiRespuesta.ok(comentarioRespuesta);

        return new ResponseEntity<>(apiRespuesta, apiRespuesta.getStatus());
    }

    @GetMapping("/postulacion/id/{postulacionId}")
    public ResponseEntity<ApiRespuesta<List<ComentarioRespuesta>>> obtenerComentarios(
            @PathVariable("postulacionId") Integer postulacionId) {
        List<ComentarioRespuesta> comentarioRespuestas = comentarioServicio.listarComentarios(postulacionId);
        ApiRespuesta<List<ComentarioRespuesta>> apiRespuesta = ApiRespuesta.ok(comentarioRespuestas);

        return new ResponseEntity<>(apiRespuesta, apiRespuesta.getStatus());
    }
}
