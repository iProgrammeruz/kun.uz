package com.codeuz.controller;

import com.codeuz.exp.AppBadException;
import com.codeuz.exp.AppForbiddenException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class HandlerController {

    @ExceptionHandler(AppBadException.class)
    public ResponseEntity<String> handle(AppBadException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }


    @ExceptionHandler(AppForbiddenException.class)
    public ResponseEntity<String> handler(AppForbiddenException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
    }


}
