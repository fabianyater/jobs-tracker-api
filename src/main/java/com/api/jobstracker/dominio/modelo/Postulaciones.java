package com.api.jobstracker.dominio.modelo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "postulaciones")
public class Postulaciones {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_postulacion", nullable = false)
    private Integer id;



    @Column(name = "fecha_postulacion")
    private LocalDate fechaPostulacion;

    @Column(name = "estado", length = 100)
    private String estado;

    @Column(name = "notas", length = Integer.MAX_VALUE)
    private String notas;

    @Column(name = "url")
    private String url;

    @ManyToOne
    @JoinColumn(name = "puesto_id_puesto")
    private Puesto puesto;

    @ManyToOne
    @JoinColumn(name = "empresa_id_empresa")
    private Empresa empresa;

}