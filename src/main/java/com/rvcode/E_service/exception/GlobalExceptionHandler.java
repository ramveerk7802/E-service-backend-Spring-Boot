package com.rvcode.E_service.exception;


import com.rvcode.E_service.response.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotAuthorized.class)
    public ErrorResponse userExceptionHandler(UserNotAuthorized e){
        return new ErrorResponse(LocalDateTime.now(),e.getMessage(),"User Not Found");
    }

    @ExceptionHandler(MyCustomException.class)
    public ErrorResponse customExceptionHandler(MyCustomException e){
        return new ErrorResponse(LocalDateTime.now(),e.getMessage(),"");
    }
}
