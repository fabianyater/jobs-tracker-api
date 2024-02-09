package com.api.jobstracker.controlador;

import com.api.jobstracker.dominio.dto.ComentarioSolicitud;
import com.api.jobstracker.dominio.modelo.ApiRespuesta;
import com.api.jobstracker.servicios.ComentarioServicio;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("comentarios")
public class ComentarioControlador {
    private final ComentarioServicio comentarioServicio;

    @PostMapping
    public ResponseEntity<ApiRespuesta<Void>> agregarComentario(@RequestBody ComentarioSolicitud comentarioSolicitud) {
        comentarioServicio.agregarComentario(comentarioSolicitud);
        ApiRespuesta<Void> apiRespuesta = ApiRespuesta.created();

        return new ResponseEntity<>(apiRespuesta, apiRespuesta.getStatus());
    }
}
