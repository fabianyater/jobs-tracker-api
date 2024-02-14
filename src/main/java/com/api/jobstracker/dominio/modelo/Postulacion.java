package com.api.jobstracker.dominio.modelo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "postulaciones")
public class Postulacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_postulacion", nullable = false)
    private Integer id;

    @Column(name = "fecha_postulacion")
    private LocalDateTime fechaPostulacion;

    @Column(name = "url")
    private String url;

    @Column(name = "descpription", columnDefinition = "text")
    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "puesto_id_puesto")
    private Puesto puesto;

    @ManyToOne
    @JoinColumn(name = "empresa_id_empresa")
    private Empresa empresa;
}