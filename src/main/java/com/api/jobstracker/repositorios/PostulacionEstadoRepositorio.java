package com.api.jobstracker.repositorios;

import com.api.jobstracker.dominio.modelo.PostulacionesEstado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostulacionEstadoRepositorio extends JpaRepository<PostulacionesEstado, Integer> {
}
