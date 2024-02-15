package com.api.jobstracker.infrastructure.exception.custom;

import lombok.Getter;

import java.io.Serial;

@Getter
public class ForbiddenException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = -6450278167900735942L;

    private final int code;

    /**
     * @param code código de error
     * @param message mensaje de error
     */
    public ForbiddenException(int code, String message) {
        super(message);
        this.code = code;
    }
}
