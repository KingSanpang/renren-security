package io.renren.common.validator;

import com.baidu.unbiz.fluentvalidator.ValidationError;
import com.baidu.unbiz.fluentvalidator.Validator;
import com.baidu.unbiz.fluentvalidator.ValidatorContext;
import com.baidu.unbiz.fluentvalidator.ValidatorHandler;

/**
 * 校验数字大小
 * Created by shuzheng on 2017/2/18.
 */
public class SizeValidator extends ValidatorHandler<Integer> implements Validator<Integer> {

    private int min;

    private int max;
    private int errorCode;
    private String fieldName;

    public SizeValidator(int min, int max, int errorCode, String fieldName) {
        this.min = min;
        this.max = max;
        this.errorCode = errorCode;
        this.fieldName = fieldName;
    }

    @Override
    public boolean validate(ValidatorContext context, Integer integer) {
        if (null == integer || integer.intValue() > max || integer.intValue() < min) {
            context.addError(ValidationError.create(String.format("%s必须大于%s，小于%s", fieldName, max, min))
                    .setErrorCode(this.errorCode)
                    .setField(fieldName)
                    .setInvalidValue(integer));
            return false;
        }
        return true;
    }

}
