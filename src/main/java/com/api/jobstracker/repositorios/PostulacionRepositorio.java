package com.api.jobstracker.repositorios;

import com.api.jobstracker.dominio.modelo.Postulaciones;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostulacionRepositorio extends JpaRepository<Postulaciones, Integer> {
    List<Postulaciones> findByEmpresa_Id(Integer id);

    List<Postulaciones> findByPuesto_Id(Integer id);
}
