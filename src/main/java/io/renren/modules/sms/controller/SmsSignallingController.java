package io.renren.modules.sms.controller;

import com.baidu.unbiz.fluentvalidator.ComplexResult;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import io.renren.common.annotation.SysLog;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.common.utils.R;
import io.renren.common.validator.Assert;
import io.renren.common.validator.ValidatorUtils;
import io.renren.common.validator.group.AddGroup;
import io.renren.common.validator.group.UpdateGroup;
import io.renren.modules.signalling.util.SmsSignallingMqUtils;
import io.renren.modules.sms.dto.SignallingDto;
import io.renren.modules.sms.dto.SmsBossEmployeeRelaDto;
import io.renren.modules.sms.po.SmsBossEmployeeRela;
import io.renren.modules.sms.service.SmsUserService;
import io.renren.modules.sms.validator.SmsUserValidator;
import io.renren.modules.sys.controller.AbstractController;
import io.renren.modules.sys.entity.SysMenuEntity;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.po.SysUser;
import io.renren.modules.sys.service.SysUserRoleService;
import io.renren.modules.sys.service.SysUserService;
import io.renren.modules.sys.shiro.ShiroUtils;
import io.renren.modules.sys.util.MenuUtils;
import io.renren.modules.sys.validator.SysUserValidator;
import org.apache.commons.lang.ArrayUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 信令接收
 */

@RestController
@RequestMapping("/sms/signalling")
public class SmsSignallingController extends AbstractController {
	private ExecutorService threadPool= Executors.newFixedThreadPool(5);
	@Autowired
	private SmsSignallingMqUtils smsSignallingMqUtils;
	/**
	 * 更改当前用户对应的老板号
	 * @param rela
	 * @return
     */
	@RequestMapping(value="/receiver")
	public R receiver(@RequestBody final SignallingDto signalling){
		logger.info("receiver signalling=" + signalling);
		R r;
		try{
			threadPool.execute(new Runnable() {
				@Override
				public void run() {
					smsSignallingMqUtils.sendMessage(signalling);
				}
			});
			r = R.ok();
		}catch (Exception e){
			r = R.error();
		}
		return r;
	}
}
