package com.api.jobstracker.dominio.mapeador;

import com.api.jobstracker.dominio.dto.ComentarioRespuesta;
import com.api.jobstracker.dominio.modelo.Comentario;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ComentarioRespuestaMapper {
    ComentarioRespuesta mapearAComentarionRespuesta(Comentario comentario);
}
