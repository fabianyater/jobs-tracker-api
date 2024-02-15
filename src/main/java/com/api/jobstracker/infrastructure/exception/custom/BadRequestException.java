package com.api.jobstracker.infrastructure.exception.custom;

import lombok.Getter;

import java.io.Serial;
import java.util.Collections;
import java.util.List;

@Getter
public class BadRequestException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 8925303792177335247L;
    private final List<String> badFields;

    /**
     * @param message mensaje de excepción arrojada por bad request
     * @param badFields lista de campos que originaron la excepción
     */
    public BadRequestException(String message, List<String> badFields) {
        super(message);
        this.badFields = Collections.unmodifiableList(badFields);
    }
}
