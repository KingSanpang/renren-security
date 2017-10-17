package io.renren.common.validator;

import com.baidu.unbiz.fluentvalidator.ValidationError;
import com.baidu.unbiz.fluentvalidator.Validator;
import com.baidu.unbiz.fluentvalidator.ValidatorContext;
import com.baidu.unbiz.fluentvalidator.ValidatorHandler;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

/**
 * 当前值是否被数组包含
 * Created by shuzheng on 2017/2/18.
 */
public class InArrValidator extends ValidatorHandler<String> implements Validator<String> {

    private String[] types;
    private int errorCode;
    private String fieldName;
    private boolean checkNull;//检查是否为空，true不能为空，false可以为空

    public InArrValidator(String[] types, boolean checkNull, int errorCode, String fieldName) {
        this.types = types;
        this.fieldName = fieldName;
        this.errorCode = errorCode;
    }

    @Override
    public boolean validate(ValidatorContext context, String s) {
        if(checkNull){//true，不能为空
            if(StringUtils.isBlank(s)){
                context.addError(ValidationError.create(String.format("%s不能为空！", fieldName))
                        .setErrorCode(this.errorCode)
                        .setField(fieldName)
                        .setInvalidValue(s));
                return false;
            }
        }
        if(!ArrayUtils.contains(types, s)) {
            context.addError(ValidationError.create(String.format("%s值不合法！", fieldName))
                    .setErrorCode(-1)
                    .setField(fieldName)
                    .setInvalidValue(s));
            return false;
        }
        return true;
    }

}
