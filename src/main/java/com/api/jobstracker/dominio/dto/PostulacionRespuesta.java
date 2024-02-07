package com.api.jobstracker.dominio.dto;

import com.api.jobstracker.dominio.modelo.Estado;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Getter
@Setter
public class PostulacionRespuesta {
    private Integer id;
    private String url;
    private String tituloPuesto;
    private String nombreEmpresa;
    private String fechaPostulacion;
    private String notas;
    private String estado;

    public void setFechaPostulacion(LocalDate fecha) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d 'de' MMMM 'de' yyyy", new Locale("es", "ES"));
        this.fechaPostulacion = fecha.format(formatter);
    }
}
