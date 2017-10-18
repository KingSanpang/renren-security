package io.renren.common.constant;

/**
 * <dl>
 * <dt>CommonConstants</dt>
 * <dd>Description:公共的常亮</dd>
 * <dd>Copyright: Copyright (C) 2006</dd>
 * <dd>Company: 青牛（北京）技术有限公司</dd>
 * <dd>CreateDate: 2017-10-17</dd>
 * </dl>
 *
 * @author Administrator
 */
public class CommonConstants {
    public static String REDIS_KEY_MENU = "menu_list_%s";//menu_list_menuid

    public enum STATUS {
        DELETED((byte)-1),//删除
        FORBIDDEN((byte)0),//禁用
        NORMAL((byte)1),//正常，允许
        AUDITING((byte)2);//审核中

        private byte value;
        STATUS(byte value) {
            this.value = value;
        }

        public byte getValue() {
            return value;
        }
    }
}
