package io.renren.common.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * <dl>
 * <dt>ConfigConstants</dt>
 * <dd>Description:映射配置文件config.properties</dd>
 * <dd>Copyright: Copyright (C) 2006</dd>
 * <dd>Company: 青牛（北京）技术有限公司</dd>
 * <dd>CreateDate: 2017-10-17</dd>
 * </dl>
 *
 * @author Administrator
 */

@Component
public class ConfigConstants {
    @Value("${common.user.dept}")
    private long commonUserDept;
    @Value("${common.user.role}")
    private long commonUserRole;
    @Value("${boss.user.role}")
    private long bossUserRole;

    public long getBossUserRole() {
        return bossUserRole;
    }

    public void setBossUserRole(long bossUserRole) {
        this.bossUserRole = bossUserRole;
    }

    public long getCommonUserDept() {
        return commonUserDept;
    }

    public void setCommonUserDept(long commonUserDept) {
        this.commonUserDept = commonUserDept;
    }

    public long getCommonUserRole() {
        return commonUserRole;
    }

    public void setCommonUserRole(long commonUserRole) {
        this.commonUserRole = commonUserRole;
    }
}
