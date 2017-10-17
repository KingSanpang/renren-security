package io.renren.modules.sys.validator;

import com.baidu.unbiz.fluentvalidator.ComplexResult;
import com.baidu.unbiz.fluentvalidator.FluentValidator;
import com.baidu.unbiz.fluentvalidator.ResultCollectors;
import io.renren.common.validator.InArrValidator;
import io.renren.common.validator.LengthValidator;
import io.renren.common.validator.NotNullValidator;
import io.renren.modules.sys.constant.SysUserConstants;
import io.renren.modules.sys.po.SysUser;

/**
 * <dl>
 * <dt>SysUserValidator</dt>
 * <dd>Description:</dd>
 * <dd>Copyright: Copyright (C) 2006</dd>
 * <dd>Company: 青牛（北京）技术有限公司</dd>
 * <dd>CreateDate: 2017-10-13</dd>
 * </dl>
 *
 * @author Administrator
 */
public class SysUserValidator {
    public static ComplexResult validateAddSysUser(SysUser user){
        ComplexResult result = FluentValidator.checkAll().failFast()
                .on(user.getUsername(), new LengthValidator(1, 20, -1, "手机号"))
                .on(user.getUserType(), new InArrValidator(SysUserConstants.USER_TYPES, true, -1, "您的身份"))
                .on(user.getPassword(), new LengthValidator(6, 20, -1, "密码"))
                .doValidate()
                .result(ResultCollectors.toComplex());
        return result;
    }
}
