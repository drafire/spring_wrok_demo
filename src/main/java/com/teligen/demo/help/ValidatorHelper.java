package com.teligen.demo.help;

import com.teligen.demo.dto.ResultCode;
import com.teligen.demo.exception.BusinessException;
import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import org.hibernate.validator.internal.engine.path.NodeImpl;
import org.hibernate.validator.internal.engine.path.PathImpl;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

public class ValidatorHelper {

    /**
     * 手动校验参数的合法性，可以对各种分组（包括Default.class）进行参数校验
     * 结合后端代码，可用于对不同参数输入时其他参数是否合法的校验补充
     * 例如：登记名字时，可以不登记性别。但是，如果登记了性别，那么性别只有两个值：男、女
     *
     * @param param
     * @param clazz
     * @throws BusinessException  自定义异常
     */
    public static void checkParam(Object param, Class<?> clazz) throws BusinessException {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Object>> validateSet = validator.validate(param, clazz);
        for (ConstraintViolation item : validateSet) {
            String message = item.getMessage();
            PathImpl propertyPath = (PathImpl) ((ConstraintViolationImpl) validateSet.toArray()[0]).getPropertyPath();
            NodeImpl leafNode = propertyPath.getLeafNode();
            throw new BusinessException(ResultCode.PARAM_ERROR.getCode(), leafNode.getName() + message);
        }
    }
}
