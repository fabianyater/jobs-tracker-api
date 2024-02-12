package com.api.jobstracker.dominio.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PostulacionRespuestaPaginada {
    private int currentPage;
    private int itemsPerPage;
    private int totalItems;
    private List<PostulacionRespuesta> postulaciones;
}
