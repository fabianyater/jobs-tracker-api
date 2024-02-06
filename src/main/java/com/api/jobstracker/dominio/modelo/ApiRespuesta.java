package com.api.jobstracker.dominio.modelo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
public class ApiRespuesta<T> {
    private T data;
    private String message;
    private int code;
    private HttpStatus status;

    public static <T> ApiRespuesta<T> ok(T data) {
        return new ApiRespuesta<>(data, "Success", 200, HttpStatus.OK);
    }

    public static <T> ApiRespuesta<T> created() {
        return new ApiRespuesta<>(null, "Created", 201, HttpStatus.CREATED);
    }

    public static ApiRespuesta<Void> error(String message, int code, HttpStatus status) {
        return new ApiRespuesta<>(null, message, code, status);
    }
}
