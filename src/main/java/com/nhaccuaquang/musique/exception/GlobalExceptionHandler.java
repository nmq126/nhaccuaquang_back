package com.nhaccuaquang.musique.exception;

import com.nhaccuaquang.musique.entity.ResponseHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity handleAllExceptions(Exception ex) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        return new ResponseEntity(ResponseHandler.ResponseHandlerBuilder.aResponseHandler()
                .withStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .withMessage("Server error")
                .withData("error", details)
                .build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NotFoundException.class)
    public final ResponseEntity handleNotFoundException(NotFoundException ex) {
        return new ResponseEntity(ResponseHandler.ResponseHandlerBuilder.aResponseHandler()
                .withStatus(HttpStatus.NOT_FOUND.value())
                .withMessage(ex.getMessage())
                .build(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateException.class)
    public final ResponseEntity handleDuplicateException(DuplicateException ex) {
        return new ResponseEntity(ResponseHandler.ResponseHandlerBuilder.aResponseHandler()
                .withStatus(HttpStatus.NOT_FOUND.value())
                .withMessage(ex.getMessage())
                .build(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
        List<String> details = new ArrayList<>();
        for(ObjectError error : ex.getBindingResult().getAllErrors()) {
            details.add(error.getDefaultMessage());
        }
        return new ResponseEntity(ResponseHandler.ResponseHandlerBuilder.aResponseHandler()
                .withStatus(HttpStatus.BAD_REQUEST.value())
                .withMessage("Validation fails")
                .withData("error", details)
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public final ResponseEntity handleAccessDeniedException(AccessDeniedException ex) {
        return new ResponseEntity(ResponseHandler.ResponseHandlerBuilder.aResponseHandler()
                .withStatus(HttpStatus.FORBIDDEN.value())
                .withMessage(ex.getMessage())
                .withData("error", "No permission")
                .build(), HttpStatus.FORBIDDEN);
    }
}
