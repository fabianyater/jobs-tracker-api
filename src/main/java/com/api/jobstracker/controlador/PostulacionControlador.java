package com.api.jobstracker.controlador;

import com.api.jobstracker.dominio.dto.EstadoSolicitud;
import com.api.jobstracker.dominio.dto.PostulacionRespuesta;
import com.api.jobstracker.dominio.dto.PostulacionSolicitud;
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

    @GetMapping
    public ResponseEntity<ApiRespuesta<List<PostulacionRespuesta>>> listarTodas() {
        List<PostulacionRespuesta> postulaciones = postulacionesServicio.listarPostulaciones();
        ApiRespuesta<List<PostulacionRespuesta>> respuesta = ApiRespuesta.ok(postulaciones);
        return new ResponseEntity<>(respuesta, respuesta.getStatus());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiRespuesta<Void>> eliminarPostulacion(@PathVariable("id") Integer id) {
        postulacionesServicio.eliminarPostulacion(id);

        ApiRespuesta<Void> respuesta = ApiRespuesta.ok(null);
        return new ResponseEntity<>(respuesta, respuesta.getStatus());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiRespuesta<Void>> actualizarEstado(@PathVariable("id") Integer id, @RequestBody EstadoSolicitud estadoSolicitud) {
        postulacionesServicio.actualizarEstado(id, estadoSolicitud);

        ApiRespuesta<Void> respuesta = ApiRespuesta.ok(null);
        return new ResponseEntity<>(respuesta, respuesta.getStatus());
    }
}
