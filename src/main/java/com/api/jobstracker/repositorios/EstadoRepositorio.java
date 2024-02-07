package com.api.jobstracker.repositorios;

import com.api.jobstracker.dominio.modelo.Estado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstadoRepositorio extends JpaRepository<Estado, Integer> {
    Estado findByEstado(String estado);
}
