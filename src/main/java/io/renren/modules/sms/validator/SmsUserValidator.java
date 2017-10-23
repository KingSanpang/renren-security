package io.renren.modules.sms.validator;

import com.baidu.unbiz.fluentvalidator.ComplexResult;
import com.baidu.unbiz.fluentvalidator.FluentValidator;
import com.baidu.unbiz.fluentvalidator.ResultCollectors;
import io.renren.common.validator.InArrValidator;
import io.renren.common.validator.LengthValidator;
import io.renren.modules.sms.po.SmsBossEmployeeRela;
import io.renren.modules.sys.constant.SysUserConstants;
import io.renren.modules.sys.po.SysUser;

/**
 * <dl>
 * <dt>SysUserValidator</dt>
 * <dd>Description:smsUser相关的校验</dd>
 * <dd>Copyright: Copyright (C) 2006</dd>
 * <dd>Company: 青牛（北京）技术有限公司</dd>
 * <dd>CreateDate: 2017-10-13</dd>
 * </dl>
 *
 * @author Administrator
 */
public class SmsUserValidator {
    public static ComplexResult validateUpdateBossTel(SmsBossEmployeeRela rela){
        ComplexResult result = FluentValidator.checkAll().failFast()
                .on(rela.getBossTel(), new LengthValidator(1, 20, -1, "老板手机号"))
                .doValidate()
                .result(ResultCollectors.toComplex());
        return result;
    }
}
