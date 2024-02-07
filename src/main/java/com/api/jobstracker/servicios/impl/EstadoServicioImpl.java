package com.api.jobstracker.servicios.impl;

import com.api.jobstracker.dominio.modelo.Estado;
import com.api.jobstracker.repositorios.EstadoRepositorio;
import com.api.jobstracker.servicios.EmpresaServicio;
import com.api.jobstracker.servicios.EstadoServicio;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EstadoServicioImpl implements EstadoServicio {
    private final EstadoRepositorio estadoRepositorio;


    @Override
    public List<Estado> obtenerEstados() {
        return estadoRepositorio.findAll();
    }
}
