package io.renren.common.validator;

import com.baidu.unbiz.fluentvalidator.ValidationError;
import com.baidu.unbiz.fluentvalidator.Validator;
import com.baidu.unbiz.fluentvalidator.ValidatorContext;
import com.baidu.unbiz.fluentvalidator.ValidatorHandler;

/**
 * 长度校验
 * Created by shuzheng on 2017/2/18.
 */
public class LengthValidator extends ValidatorHandler<String> implements Validator<String> {

    private int min;

    private int max;
    private int errorCode;
    private String fieldName;

    public LengthValidator(int min, int max, int errorCode, String fieldName) {
        this.min = min;
        this.max = max;
        this.errorCode = errorCode;
        this.fieldName = fieldName;
    }

    @Override
    public boolean validate(ValidatorContext context, String s) {
        if (null == s || s.length() > max || s.length() < min) {
            context.addError(ValidationError.create(String.format("%s长度必须介于%s~%s之间！", fieldName, min, max))
                    .setErrorCode(this.errorCode)
                    .setField(fieldName)
                    .setInvalidValue(s));
            return false;
        }
        return true;
    }

}
