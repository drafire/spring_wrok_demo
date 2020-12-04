package com.teligen.demo.handler;

import com.teligen.demo.dto.ResultCode;
import com.teligen.demo.dto.ResultDTO;
import com.teligen.demo.exception.BusinessException;
import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import org.hibernate.validator.internal.engine.path.NodeImpl;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

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

    //缺失参数校验
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResultDTO missingParamException(MissingServletRequestParameterException e) {
        logger.error(e.getMessage(), e);
        return ResultDTO.fail("参数" + e.getParameterName() + "(" + e.getParameterType() + ")缺失");
    }

    //非object参数类型校验
    @ExceptionHandler(ConstraintViolationException.class)
    public ResultDTO constraintViolationException(ConstraintViolationException e) {
        logger.error(e.getMessage(), e);
        Set<ConstraintViolation<?>> constraintViolationsSet = e.getConstraintViolations();
        for (ConstraintViolation constraintViolation : constraintViolationsSet) {
            String message = constraintViolation.getMessage();
            PathImpl propertyPath = (PathImpl) ((ConstraintViolationImpl) e.getConstraintViolations().toArray()[0]).getPropertyPath();
            NodeImpl leafNode = propertyPath.getLeafNode();

            return ResultDTO.fail(ResultCode.PARAM_ERROR.getCode(),leafNode.getName() + message);

        }
        return ResultDTO.fail();
    }

    @ExceptionHandler(Exception.class)
    public ResultDTO handleException(Exception e) {
        if (e instanceof HttpMessageNotReadableException) {
            logger.error(e.getMessage(), e);
            return ResultDTO.fail(ResultCode.PARAM_ERROR.getCode(), "json参数不能为空或不规范");
        } else if (e instanceof HttpRequestMethodNotSupportedException) {
            logger.error(e.getMessage());
            return ResultDTO.fail(ResultCode.METHOD_NOT_SUPPORT.getCode(), ((HttpRequestMethodNotSupportedException) e).getMethod() + "不支持");
        }

        logger.error(e.getMessage(), e);
        return ResultDTO.fail();
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResultDTO validationError(MethodArgumentNotValidException ex) {
        FieldError fieldError = ex.getBindingResult().getFieldError();
        logger.error(fieldError.getField() + fieldError.getDefaultMessage());
        return ResultDTO.fail(ResultCode.PARAM_ERROR.getCode(), fieldError.getField() + fieldError.getDefaultMessage());
    }
}
