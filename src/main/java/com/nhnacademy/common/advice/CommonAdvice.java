package com.nhnacademy.common.advice;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.nhnacademy.common.exception.CommonHttpException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class CommonAdvice {

    private final PropertyNamingStrategies.NamingBase namingBase;

    public CommonAdvice(PropertyNamingStrategy namingStrategy) {
        this.namingBase = (namingStrategy instanceof PropertyNamingStrategies.NamingBase namingBase)
                ? namingBase
                : null;
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<CommonErrorResponse> bindExceptionHandler(
            BindException e,
            HttpServletRequest request
    ) {
        List<String> errors = new ArrayList<>();
        e.getBindingResult().getAllErrors().forEach(error -> {
            if (error instanceof FieldError fieldError) {
                errors.add(
                        "{\"field\":\"%s\", \"rejected_value\":\"%s\", \"message\":\"%s\"}".formatted(
                                namingBase != null
                                        ? namingBase.translate(fieldError.getField())
                                        : fieldError.getField(),
                                String.valueOf(fieldError.getRejectedValue()),
                                fieldError.getDefaultMessage()
                        )
                );
            }
        });

        return ResponseEntity
                .badRequest()
                .body(CommonErrorResponse.of(
                        HttpStatus.BAD_REQUEST.value(),
                        String.join(", ", errors),
                        request.getRequestURI()
                ));
    }

    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<CommonErrorResponse> httpMessageNotReadableExceptionHandler(
            InvalidFormatException e,
            HttpServletRequest request
    ) {
        String path = request.getRequestURI();

        String fieldName = extractFieldName(e.getPathReference());
        String message = "[%s] 필드의 값이 허용되지 않는 형식입니다."
                .formatted(fieldName.substring(1, fieldName.length() - 2));

        return ResponseEntity
                .badRequest()
                .body(CommonErrorResponse.of(
                        HttpStatus.BAD_REQUEST.value(),
                        message,
                        path
                ));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<CommonErrorResponse> databaseExceptionHandler(
            DataIntegrityViolationException e,
            HttpServletRequest request
    ) {
        String path = request.getRequestURI();
        log.warn("path({}): {}", path, e.getMessage());

        return ResponseEntity
                .badRequest()
                .body(CommonErrorResponse.of(
                        HttpStatus.BAD_REQUEST.value(),
                        e.getMessage(),
                        path
                ));
    }

    @ExceptionHandler(CommonHttpException.class)
    public ResponseEntity<CommonErrorResponse> exceptionHandler(
            CommonHttpException e,
            HttpServletRequest request
    ) {
        String path = request.getRequestURI();
        log.warn("path({}): {}", path, e.getMessage(), e);

        return ResponseEntity
                .status(e.getStatusCode())
                .body(CommonErrorResponse.of(
                        e.getStatusCode(),
                        e.getMessage(),
                        path
                ));
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<CommonErrorResponse> throwableHandler(
            Throwable e,
            HttpServletRequest request
    ) {
        String path = request.getRequestURI();
        log.warn("path({}): {}", path, e.getMessage(), e);
        int httpStatus = HttpStatus.INTERNAL_SERVER_ERROR.value();

        return ResponseEntity
                .status(httpStatus)
                .body(CommonErrorResponse.of(
                        httpStatus,
                        e.getMessage(),
                        path
                ));
    }

    private String extractFieldName(String pathReference) {
        // pathReference: com.nhnacademy.sensor_type_mapping.dto.SensorDataMappingInfo["sensor_status"]
        int start = pathReference.indexOf('[');
        int end = pathReference.indexOf(']');

        if (start >= 0 && end > start) {
            String camelField = pathReference.substring(start + 1, end);
            return namingBase != null ? namingBase.translate(camelField) : camelField;
        }
        return "알 수 없는 필드";
    }
}
