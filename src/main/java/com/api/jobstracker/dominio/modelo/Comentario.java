package com.api.jobstracker.dominio.modelo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
@Entity
@Table(name = "comentarios")
public class Comentario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comentario_id")
    private Integer id;

    @Column(name = "comentario", columnDefinition = "text")
    private String comentario;

    @Column(name = "fecha_publicacion")
    private ZonedDateTime fechaPublicacion;

    @ManyToOne
    @JoinColumn(name = "postulacion_id_postulacion")
    private Postulacion postulacion;

}
