package com.smartgroup.marketauction.web.errorhandling;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.smartgroup.marketauction.dto.ResponseDTO;
import com.smartgroup.marketauction.web.errorhandling.exceptions.ExerciseNotFoundException;
import com.smartgroup.marketauction.web.errorhandling.exceptions.ModelIdNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ModelIdNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseDTO handleModelIdNotFound(ModelIdNotFoundException ex) {
        return new ResponseDTO(HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }

    @ExceptionHandler(ExerciseNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseDTO handleExerciseNotFound(ExerciseNotFoundException ex) {
        return new ResponseDTO(HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }
}