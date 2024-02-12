package com.api.jobstracker.dominio.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@Getter
@Setter
public class PostulacionRespuesta {
    private Integer id;
    private String url;
    private String tituloPuesto;
    private String nombreEmpresa;
    private String fechaPostulacion;
    private String fechaActualizacion;
    private List<ComentarioRespuesta> comentarios;
    private String estado;

    public void setFechaPostulacion(LocalDate fecha) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d 'de' MMMM 'de' yyyy", new Locale("es", "ES"));
        this.fechaPostulacion = fecha.format(formatter);
    }

    public void setFechaPostulacion(LocalDateTime fecha) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d 'de' MMMM 'de' yyyy", new Locale("es", "ES"));
        this.fechaActualizacion = fecha.format(formatter);
    }
}
