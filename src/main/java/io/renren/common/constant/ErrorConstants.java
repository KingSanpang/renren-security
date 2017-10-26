package io.renren.common.constant;

/**
 * <dl>
 * <dt>ErrorConstants</dt>
 * <dd>Description:错误代码常亮类</dd>
 * <dd>Copyright: Copyright (C) 2006</dd>
 * <dd>CreateDate: 2017-10-18</dd>
 * </dl>
 *
 * @author Administrator
 */
public class ErrorConstants {
    //用户相关，10开头
    public static class UserError{
        public static final int USERNAME_EXIST_ERROR = 100001;//用户名已经存在
        public static final int USERNAME_NOT_EXIST_ERROR = 100002;//用户名不存在

        private UserError() {
        }
    }
    //手机鉴权相关，11开头
    public static class MobileAuth{
        public static final int SIGN_NOT_EQUALS = 110001;//sign不匹配
        public static final int NOT_LOGIN = 110002;//未登录
        public static final int NEED_REFRESH_TOKEN = 110003;//登录时间太长，需要刷新token

        private MobileAuth() {
        }
    }

    private ErrorConstants() {
    }
}
