package com.api.jobstracker.controlador;

import com.api.jobstracker.dominio.modelo.ApiRespuesta;
import com.api.jobstracker.dominio.modelo.Estado;
import com.api.jobstracker.servicios.EstadoServicio;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("estados")
public class EstadoControlador {
    private final EstadoServicio estadoServicio;

    @GetMapping
    public ResponseEntity<ApiRespuesta<List<Estado>>> obtenerEstados() {
        List<Estado> estados = estadoServicio.obtenerEstados();
        ApiRespuesta<List<Estado>> apiRespuesta = ApiRespuesta.ok(estados);

        return new ResponseEntity<>(apiRespuesta, apiRespuesta.getStatus());
    }
}
