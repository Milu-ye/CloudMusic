package com.eren.cloudmusic.common.config;


import com.eren.cloudmusic.common.domain.vo.Response;
import com.eren.cloudmusic.common.exceptions.ServerException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Response handleException(Exception e) {
        e.printStackTrace();
        return new Response(-1, "SERVER_ERROR");
    }

    @ExceptionHandler(ServerException.class)
    public Response handleServerException(ServerException e) {
        return new Response(e.getCode(),e.getMessage());
    }

}
