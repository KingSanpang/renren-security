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
    public enum STATUS {
        //删除、禁用、正常
        DELETED((byte)-1), FORBIDDEN((byte)0), NORMAL((byte)1);

        private byte value;
        STATUS(byte value) {
            this.value = value;
        }

        public byte getValue() {
            return value;
        }
    }
}
