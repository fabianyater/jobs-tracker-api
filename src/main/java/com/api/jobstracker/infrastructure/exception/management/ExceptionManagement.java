package com.api.jobstracker.infrastructure.exception.management;

import com.api.jobstracker.commons.constant.ApiConstant;
import com.api.jobstracker.commons.constant.ErrorConstant;
import com.api.jobstracker.infrastructure.exception.custom.BusinessException;
import com.api.jobstracker.infrastructure.exception.custom.ErrorResponse;
import com.api.jobstracker.infrastructure.exception.custom.ForbiddenException;
import com.api.jobstracker.infrastructure.exception.custom.NotDataFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import java.time.ZonedDateTime;
import java.util.*;

@Slf4j
@ControllerAdvice
public class ExceptionManagement {
    public enum ErrorType {
        ERROR,
        WARN,
        INVALID,
        FATAL
    }

    /**
     * @param ex      Excepción recibida {@link Exception}
     * @param request Objeto Http Servlet de petición
     * @return errorResponse {@link ErrorResponse} respuesta específica para {@link Exception}
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse generalException(Exception ex, WebRequest request) {
        ErrorResponse apiError =
                ErrorResponse.builder()
                        .type(ErrorType.FATAL.name())
                        .code(ErrorConstant.GENERIC_ERROR_CODE)
                        .message(ErrorConstant.GENERIC_ERROR_MESSAGE)
                        .moreInfo(ex.getMessage())
                        .uuid(request.getHeader(ApiConstant.HEADER_UUID))
                        .build();

        log.debug(apiError.toString());

        return apiError;
    }

    /**
     * @param ex      ex Excepción recibida {@link NotDataFoundException}
     * @param request Objeto Http Servlet de petición.
     * @return errorResponse {@link ErrorResponse} respuesta específica para {@link NotDataFoundException}
     */
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotDataFoundException.class)
    public ErrorResponse notDataFoundException(NotDataFoundException ex, WebRequest request) {
        ErrorResponse apiError =
                ErrorResponse.builder()
                        .type(ErrorType.WARN.name())
                        .code(ErrorConstant.RECORD_NOT_FOUND_CODE)
                        .message(ErrorConstant.RECORD_NOT_FOUND_MESSAGE)
                        .moreInfo(ex.getMessage())
                        .uuid(request.getHeader(ApiConstant.HEADER_UUID))
                        .build();

        log.debug(apiError.toString());

        return apiError;
    }

    /**
     * @param ex      Excepción recibida {@link HttpRequestMethodNotSupportedException}
     * @param request Objeto Http Servlet de petición.
     * @return errorResponse {@link ErrorResponse} respuesta específica para {@link
     * HttpRequestMethodNotSupportedException}.
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(value = HttpStatus.METHOD_NOT_ALLOWED)
    public ErrorResponse resolveHttpRequestMethodNotSupportedException(
            HttpRequestMethodNotSupportedException ex, WebRequest request) {
        ErrorResponse apiError =
                ErrorResponse.builder()
                        .type(ErrorType.ERROR.name())
                        .code(ErrorConstant.BAD_REQUEST_CODE)
                        .message(ex.getMessage())
                        .uuid(request.getHeader(ApiConstant.HEADER_UUID))
                        .build();

        log.debug(apiError.toString());

        return apiError;
    }

    /**
     * @param ex      Excepción recibida {@link HttpMediaTypeNotSupportedException}
     * @param request Objeto Http Servlet de petición.
     * @return errorResponse {@link ErrorResponse} respuesta específica para {@link
     * HttpMediaTypeNotSupportedException}.
     */
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseStatus(value = HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    public ErrorResponse resolveHttpMediaTypeNotSupportedException(
            HttpMediaTypeNotSupportedException ex, WebRequest request) {
        ErrorResponse apiError =
                ErrorResponse.builder()
                        .type(ErrorType.ERROR.name())
                        .code(ErrorConstant.BAD_REQUEST_CODE)
                        .message(ex.getMessage())
                        .uuid(request.getHeader(ApiConstant.HEADER_UUID))
                        .build();

        log.debug(apiError.toString());

        return apiError;
    }

    /**
     * @param ex      Excepción recibida {@link HttpMediaTypeNotAcceptableException}
     * @param request Objeto Http Servlet de petición.
     * @return errorResponse {@link ErrorResponse} respuesta específica para {@link
     * HttpMediaTypeNotAcceptableException}.
     */
    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    @ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
    public ErrorResponse resolveHttpMediaTypeNotAcceptableException(
            WebRequest request, HttpMediaTypeNotAcceptableException ex) {
        ErrorResponse apiError =
                ErrorResponse.builder()
                        .type(ErrorType.INVALID.name())
                        .code(ErrorConstant.BAD_REQUEST_CODE)
                        .message(ex.getMessage())
                        .uuid(request.getHeader(ApiConstant.HEADER_UUID))
                        .build();

        log.debug(apiError.toString());

        return apiError;
    }

    /**
     * @param ex      Excepción recibida {@link MethodArgumentNotValidException}
     * @param request Objeto Http Servlet de petición.
     * @return errorResponse {@link ErrorResponse} respuesta específica para {@link
     * MethodArgumentNotValidException}.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorResponse resolveMethodArgumentNotValidException(
            MethodArgumentNotValidException ex, WebRequest request) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        List<String> fields = new ArrayList<>();
        Map<String, List<String>> groupedErrors = new HashMap<>();

        for (FieldError fieldError : fieldErrors) {
            String field = fieldError.getField();
            groupedErrors.computeIfAbsent(
                    fieldError.getDefaultMessage(), key -> Collections.singletonList(field));
            fields.add(field);
        }

        ErrorResponse apiError =
                ErrorResponse.builder()
                        .type(ErrorType.INVALID.name())
                        .code(ErrorConstant.BAD_REQUEST_CODE)
                        .message(groupedErrors.toString())
                        .moreInfo(fields.toString())
                        .uuid(request.getHeader(ApiConstant.HEADER_UUID))
                        .build();

        log.debug(apiError.toString());

        return apiError;
    }

    /**
     * @param ex      Excepción recibida {@link BusinessException}
     * @param request Objeto Http Servlet de petición.
     * @return errorResponse {@link ErrorResponse} respuesta específica para {@link
     * BusinessException}.
     */
    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(value = HttpStatus.OK)
    public ErrorResponse resolveBusinessException(WebRequest request, BusinessException ex) {
        ErrorResponse apiError =
                ErrorResponse.builder()
                        .type(ErrorType.WARN.name())
                        .code(ex.getCode())
                        .message(ex.getMessage())
                        .uuid(request.getHeader(ApiConstant.HEADER_UUID))
                        .build();

        log.debug(apiError.toString());

        return apiError;
    }

    /**
     * @param ex      Excepción recibida {@link ForbiddenException}
     * @param request Objeto Http Servlet de petición.
     * @return errorResponse {@link ErrorResponse} respuesta específica para {@link
     * ForbiddenException}.
     */
    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public ErrorResponse resolveForbiddenException(WebRequest request, ForbiddenException ex) {
        ErrorResponse apiError =
                ErrorResponse.builder()
                        .type(ErrorType.WARN.name())
                        .code(ex.getCode())
                        .message(ex.getMessage())
                        .uuid(request.getHeader(ApiConstant.HEADER_UUID))
                        .build();

        log.debug(apiError.toString());

        return apiError;
    }

    /**
     * @param ex  excepción generada por JPA
     * @param req Petición
     * @return objeto de respuesta específico para {@see DataIntegrityViolationException}
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public ErrorResponse resolveDataIntegrityViolationException(
            HttpServletRequest req, DataIntegrityViolationException ex) {
        String error = NestedExceptionUtils.getMostSpecificCause(ex).getMessage();
        String[] message = Arrays
                .stream(error.split(":")).map(String::trim)
                .toArray(String[]::new);

        ErrorResponse errorResponse =
                ErrorResponse.builder()
                        .type(ErrorType.ERROR.name())
                        .code(ErrorConstant.GENERIC_ERROR_CODE)
                        .message(message[0])
                        .location(req.getRequestURI())
                        .moreInfo(
                                message[0].replace(ErrorConstant.PREFIX_DETAIL_MESSAGE, StringUtils.EMPTY).trim())
                        .uuid(req.getHeader(ApiConstant.HEADER_UUID))
                        .timestamp(ZonedDateTime.now())
                        .build();

        log.error(errorResponse.toString());

        return errorResponse;
    }
}
