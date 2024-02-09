package com.api.jobstracker.dominio.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ComentarioSolicitud {
    private int postulacionId;
    private String comentario;
}
