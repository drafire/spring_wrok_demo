package com.teligen.demo.handler;

import com.teligen.demo.dto.ResultDTO;
import com.teligen.demo.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BusinessExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 处理自定义异常
     */
    @ExceptionHandler(BusinessException.class)
    public ResultDTO handleRRException(BusinessException e) {
        return ResultDTO.fail(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public ResultDTO handleDuplicateKeyException(DuplicateKeyException e) {
        logger.error(e.getMessage(), e);
        return ResultDTO.fail("数据库中已存在该记录");
    }


    @ExceptionHandler(Exception.class)
    public ResultDTO handleException(Exception e) {
        if (e instanceof HttpMessageNotReadableException) {
            return ResultDTO.fail("json参数不能为空");
        }

        logger.error(e.getMessage(), e);
        return ResultDTO.fail();
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResultDTO validationError(MethodArgumentNotValidException ex) {
        FieldError fieldError = ex.getBindingResult().getFieldError();
        logger.error(fieldError.getField() + fieldError.getDefaultMessage());
        return ResultDTO.fail(-99, fieldError.getField() + fieldError.getDefaultMessage());
    }
}
