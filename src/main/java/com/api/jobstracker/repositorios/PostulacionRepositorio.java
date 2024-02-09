package com.api.jobstracker.repositorios;

import com.api.jobstracker.dominio.modelo.Postulacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostulacionRepositorio extends JpaRepository<Postulacion, Integer> {
    List<Postulacion> findByEmpresa_Id(Integer id);

    List<Postulacion> findByPuesto_Id(Integer id);
}
