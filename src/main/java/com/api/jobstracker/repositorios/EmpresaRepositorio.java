package com.api.jobstracker.repositorios;

import com.api.jobstracker.dominio.modelo.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmpresaRepositorio extends JpaRepository<Empresa, Integer> {
    Optional<Empresa> findByNombre(String nombre);
}
