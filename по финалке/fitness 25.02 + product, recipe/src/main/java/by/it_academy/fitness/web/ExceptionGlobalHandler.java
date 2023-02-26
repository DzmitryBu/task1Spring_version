package by.it_academy.fitness.web;

import by.it_academy.fitness.core.dto.MultipleError;
import by.it_academy.fitness.core.dto.SingleError;
import by.it_academy.fitness.core.exception.MultipleErrorResponse;
import by.it_academy.fitness.core.exception.SingleErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionGlobalHandler {

    @ExceptionHandler()
    public ResponseEntity<SingleError> handlerSingle(SingleErrorResponse e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new SingleError(e.getLogref(), e.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<MultipleError> handlerMulti(MultipleErrorResponse e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MultipleError(e.getLogref(), e.getErrors()));
    }

    @ExceptionHandler
    public ResponseEntity<String> handlerNPE(Throwable e){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
}
