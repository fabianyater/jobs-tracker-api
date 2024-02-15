package com.api.jobstracker.infrastructure.exception.custom;

import java.io.Serial;

public class NotDataFoundException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -6450278167900735942L;

    public NotDataFoundException() {
        super();
    }

    /**
     * @param message mensaje de error
     */
    public NotDataFoundException(String message) {
        super(message);
    }
}
