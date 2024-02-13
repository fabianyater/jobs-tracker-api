package com.api.jobstracker.repositorios;

import com.api.jobstracker.dominio.dto.PostulacionTimelineRespuesta;
import com.api.jobstracker.dominio.modelo.Postulacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostulacionRepositorio extends JpaRepository<Postulacion, Integer> {
    List<Postulacion> findByEmpresa_Id(Integer id);

    List<Postulacion> findByPuesto_Id(Integer id);

    @Query(value = "SELECT e.estado, pe.fecha_actualizacion, e.color " +
            "FROM postulaciones_estados pe " +
            "INNER JOIN estados e ON pe.estados_estado_id = e.estado_id " +
            "WHERE pe.postulaciones_id_postulacion = :idPostulacion", nativeQuery = true)
    List<Object[]> findEstadosByPostulacionId(@Param("idPostulacion") Integer idPostulacion);


}
