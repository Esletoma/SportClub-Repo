package com.sportclub.actividadescentro_ms.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@ResponseBody
public class HorarioNotFoundAdvice {
    @ResponseBody
    @ExceptionHandler(HorarioNotFoundException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    String EntityNotFoundAdvice(HorarioNotFoundException ex){
        return ex.getMessage();
    }
}
