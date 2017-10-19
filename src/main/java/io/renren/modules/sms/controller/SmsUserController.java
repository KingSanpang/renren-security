package io.renren.modules.sms.controller;

import com.baidu.unbiz.fluentvalidator.ComplexResult;
import io.renren.common.annotation.SysLog;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.common.utils.R;
import io.renren.common.validator.Assert;
import io.renren.common.validator.ValidatorUtils;
import io.renren.common.validator.group.AddGroup;
import io.renren.common.validator.group.UpdateGroup;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

/**
 * sms用户管理
 */

@RestController
@RequestMapping("/sms/user")
public class SmsUserController extends AbstractController {
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysUserRoleService sysUserRoleService;

	@RequestMapping(value="info", method=RequestMethod.GET)
	@RequiresPermissions("sms:user:info")
	public ModelAndView info(){
		ModelAndView mav = new ModelAndView("modules/sms/user/info.jsp");
		//查询个人信息
		SysUserEntity user = getUser();
		mav.addObject("user", user);
		//查询老板信息，是否可以自定义短信签名，老板审核的状态
		//TODO


		List<SysMenuEntity> menus = MenuUtils.getMenuListInRedis(getUserId());
		mav.addObject("menus", menus);
		mav.addObject("curMenu", "sms/user/info");//当前菜单
		return mav;
	}





	@RequestMapping("/gotoUpdate")
	@RequiresPermissions("sms:hangup:list")//查询权限
	public ModelAndView gotoUpdate(){
		ModelAndView mav = new ModelAndView();
		List<SysMenuEntity> menus = MenuUtils.getMenuListInRedis(getUserId());
		mav.addObject("menus", menus);
		mav.addObject("curMenu", "sms/hangup/gotoUpdate");//当前菜单

		mav.setViewName("/modules/sms/hangup/update.jsp");
		return mav;
	}
	
	/**
	 * 所有用户列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("sys:user:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
		Query query = new Query(params);
		List<SysUserEntity> userList = sysUserService.queryList(query);
		int total = sysUserService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(userList, total, query.getLimit(), query.getPage());

		return R.ok().put("page", pageUtil);
	}
	
	/**
	 * 修改登录用户密码
	 */
	@SysLog("修改密码")
	@RequestMapping("/password")
	public R password(String password, String newPassword){
		Assert.isBlank(newPassword, "新密码不为能空");

		//原密码
		password = ShiroUtils.sha256(password, getUser().getSalt());
		//新密码
		newPassword = ShiroUtils.sha256(newPassword, getUser().getSalt());
				
		//更新密码
		int count = sysUserService.updatePassword(getUserId(), password, newPassword);
		if(count == 0){
			return R.error("原密码不正确");
		}
		
		return R.ok();
	}
	

	/**
	 * 保存用户
	 */
	@SysLog("保存用户")
	@RequestMapping("/save")
	@RequiresPermissions("sys:user:save")
	public R save(@RequestBody SysUserEntity user){
		ValidatorUtils.validateEntity(user, AddGroup.class);
		
		sysUserService.save(user);
		
		return R.ok();
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView gotoRegister(){
		return new ModelAndView("register.jsp");
	}

	@RequestMapping(value="/register", method=RequestMethod.POST)
	public R register(@RequestBody SysUser user){
		logger.info("register user = {}", user.toString());
		R r;
		ComplexResult validateRes = SysUserValidator.validateAddSysUser(user);
		if (!validateRes.isSuccess()) {
			return R.error(validateRes.getErrors());
		}
		try{
			r = sysUserService.register(user);
			if(r != null){
				return r;
			}else{
				r = R.ok();
			}
		}catch (Exception e){
			logger.error("SysUserController.register error", e);
			r = R.error("注册发生异常！");
		}
		return r;
	}
	
	/**
	 * 修改用户
	 */
	@SysLog("修改用户")
	@RequestMapping("/update")
	@RequiresPermissions("sys:user:update")
	public R update(@RequestBody SysUserEntity user){
		ValidatorUtils.validateEntity(user, UpdateGroup.class);

		sysUserService.update(user);
		
		return R.ok();
	}
	
	/**
	 * 删除用户
	 */
	@SysLog("删除用户")
	@RequestMapping("/delete")
	@RequiresPermissions("sys:user:delete")
	public R delete(@RequestBody Long[] userIds){
		if(ArrayUtils.contains(userIds, 1L)){
			return R.error("系统管理员不能删除");
		}
		
		if(ArrayUtils.contains(userIds, getUserId())){
			return R.error("当前用户不能删除");
		}
		
		sysUserService.deleteBatch(userIds);
		
		return R.ok();
	}
}
