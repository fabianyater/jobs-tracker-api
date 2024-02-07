package com.api.jobstracker.servicios;

import com.api.jobstracker.dominio.modelo.Estado;

import java.util.List;

public interface EstadoServicio {
    List<Estado> obtenerEstados();
}
