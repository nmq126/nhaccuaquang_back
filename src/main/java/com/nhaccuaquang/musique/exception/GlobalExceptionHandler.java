package com.nhaccuaquang.musique.exception;

import com.nhaccuaquang.musique.entity.ResponseHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseHandler handleAllExceptions(Exception ex) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        return ResponseHandler.ResponseHandlerBuilder.aResponseHandler()
                .withStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .withMessage("Server error")
                .withData("error", details)
                .build();
    }

    @ExceptionHandler(NotFoundException.class)
    public final ResponseHandler handleNotFoundException(NotFoundException ex) {
        return ResponseHandler.ResponseHandlerBuilder.aResponseHandler()
                .withStatus(HttpStatus.NOT_FOUND.value())
                .withMessage(ex.getMessage())
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseHandler handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
        List<String> details = new ArrayList<>();
        for(ObjectError error : ex.getBindingResult().getAllErrors()) {
            details.add(error.getDefaultMessage());
        }
        return ResponseHandler.ResponseHandlerBuilder.aResponseHandler()
                .withStatus(HttpStatus.BAD_REQUEST.value())
                .withMessage("Validation fails")
                .withData("error", details)
                .build();
    }
}
