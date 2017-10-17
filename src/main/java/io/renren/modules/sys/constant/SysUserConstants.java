package io.renren.modules.sys.constant;

/**
 * <dl>
 * <dt>SysUserConstants</dt>
 * <dd>Description:用户常量类</dd>
 * <dd>Copyright: Copyright (C) 2006</dd>
 * <dd>Company: 青牛（北京）技术有限公司</dd>
 * <dd>CreateDate: 2017-10-13</dd>
 * </dl>
 *
 * @author Administrator
 */
public class SysUserConstants {
    public static String[] USER_TYPES = {"0","1","2"};//0：管理员；1：老板；2：员工
    public enum USER_TYPE {
        ADMIN("0"), BOSS("1"), EMPLOYEE("2");

        private String value;

        USER_TYPE(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
