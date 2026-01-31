package br.com.marinas.projeto.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EstudanteNaoEncontradoException.class)
    public ResponseEntity<String> handleEstudanteNaoEncontradoException(Exception e){
        return  ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(e.getMessage() + "\n"
                        + e.getClass());
    }
}
