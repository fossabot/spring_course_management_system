package com.gaogaoqwq.course_info_management_system_backend.exception;

import com.gaogaoqwq.course_info_management_system_backend.response.R;
import com.gaogaoqwq.course_info_management_system_backend.response.ResultCode;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public R error(@NotNull Exception e) {
        e.printStackTrace();
        return R.setResult(ResultCode.INTERNAL_SERVER_ERROR);
    }

    @ResponseBody
    @ExceptionHandler(NullPointerException.class)
    public R error(@NotNull NullPointerException e) {
        e.printStackTrace();
        return R.setResult(ResultCode.NULL_POINTER_EXCEPTION);
    }

    @ResponseBody
    @ExceptionHandler(AuthenticationException.class)
    public R AuthenticationExceptionHandler(@NotNull AuthenticationException e) {
        return R.failure()
                .code(ResultCode.UNAUTHORIZED.getCode())
                .message(e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(BadCredentialsException.class)
    public R BadCredentialsExceptionHandler(@NotNull BadCredentialsException e) {
        return R.failure()
                .code(ResultCode.UNAUTHORIZED.getCode())
                .message(e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(ParamException.class)
    public R ParamExceptionHandler(@NotNull ParamException e) {
        return R.failure()
                .code(e.getCode())
                .message(e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(QueryException.class)
    public R QueryExceptionHandler(@NotNull QueryException e) {
        return R.failure()
                .code(ResultCode.NOT_FOUND.getCode())
                .message(e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(SQLException.class)
    public R SQLExceptionHandler(@NotNull SQLException e) {
        return R.failure()
                .code(ResultCode.CONFLICT.getCode())
                .message(e.getMessage());
    }

}
