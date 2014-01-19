package es.kgp.chat.server.controller;

import es.kgp.chat.server.exception.GenericErrorResponse;
import es.kgp.chat.server.exception.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by kgp on 19/01/2014.
 */
@ControllerAdvice
public class WebExceptionHandler {

    @ExceptionHandler(UnauthorizedException.class)
    public @ResponseBody ResponseEntity<GenericErrorResponse> handleUnauthorizedException(UnauthorizedException exception){
        return new ResponseEntity<>(new GenericErrorResponse(exception.getMessage()), HttpStatus.UNAUTHORIZED);
    }

}
