package com.api.jobstracker.repositorios;

import com.api.jobstracker.dominio.modelo.Puesto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PuestoRepositorio extends JpaRepository<Puesto, Integer> {
    Optional<Puesto> findByTitulo(String titulo);
}
