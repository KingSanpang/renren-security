package io.renren.modules.sys.util;

import com.google.gson.reflect.TypeToken;
import io.renren.common.constant.CommonConstants;
import io.renren.common.utils.RedisUtils;
import io.renren.modules.sys.entity.SysMenuEntity;
import io.renren.modules.sys.service.SysMenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * <dl>
 * <dt>UserUtils</dt>
 * <dd>Description:用户的工具类</dd>
 * <dd>Copyright: Copyright (C) 2006</dd>
 * <dd>Company: 青牛（北京）技术有限公司</dd>
 * <dd>CreateDate: 2017-10-18</dd>
 * </dl>
 *
 * @author Administrator
 */

@Component
public class MenuUtils {
    private static Logger logger = LoggerFactory.getLogger(MenuUtils.class);

    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private SysMenuService sysMenuService;

    private static MenuUtils menuUtils;

    @PostConstruct
    public void init(){
        menuUtils = this;
        menuUtils.redisUtils = this.redisUtils;
        menuUtils.sysMenuService = this.sysMenuService;
    }

    public static void putMenuListInRedis(long userId, List<SysMenuEntity> menus){
        menuUtils.redisUtils.set(String.format(CommonConstants.REDIS_KEY_MENU, userId), menus);
    }

    public static List getMenuListInRedis(long userId){
        List<SysMenuEntity> menus = menuUtils.redisUtils.getList(String.format(CommonConstants.REDIS_KEY_MENU, userId), SysMenuEntity.class, new TypeToken<ArrayList<SysMenuEntity>>(){}.getType());
        if(menus == null){//查询数据库
            menus = menuUtils.sysMenuService.getUserMenuList(userId);
            putMenuListInRedis(userId, menus);
        }
        return menus;
    }
}
