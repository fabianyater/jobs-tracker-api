package com.api.jobstracker.dominio.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostulacionRespuesta {
    private Integer id;
    private String url;
    private String tituloPuesto;
    private String nombreEmpresa;
    private String fechaPostulacion;
    private String fechaActualizacion;
    private String estado;
    private String descripcion;
}
