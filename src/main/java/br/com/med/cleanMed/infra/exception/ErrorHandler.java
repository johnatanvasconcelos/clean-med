package br.com.med.cleanMed.infra.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity errorHandler404(){
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity errorHandler400(MethodArgumentNotValidException exception){
        var errors = exception.getFieldErrors();
        return ResponseEntity.badRequest().body(errors.stream().map(ErrorDataValidation::new).toList());
    }

    @ExceptionHandler(AppointmentValidationException.class)
    public ResponseEntity errorHandlerBusinessRule(AppointmentValidationException exception){
        return ResponseEntity.badRequest().body(exception.getMessage());
    }

    private record ErrorDataValidation(String field, String message){
        public ErrorDataValidation(FieldError error){
            this(error.getField(), error.getDefaultMessage());
        }
    }
}
