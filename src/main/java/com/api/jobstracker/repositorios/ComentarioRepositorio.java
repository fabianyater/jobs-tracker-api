package com.api.jobstracker.repositorios;

import com.api.jobstracker.dominio.modelo.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComentarioRepositorio extends JpaRepository<Comentario, Integer> {
    List<Comentario> findByPostulacion_Id(Integer id);
}
