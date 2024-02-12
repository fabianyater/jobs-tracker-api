package com.api.jobstracker.dominio.modelo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "postulaciones_estados")
public class PostulacionesEstado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "postulacion_estado_id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "postulaciones_id_postulacion", nullable = false)
    private Postulacion postulacionIdPostulacion;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "estados_estado_id", nullable = false)
    private Estado estadosEstado;

    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;

}