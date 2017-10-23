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

/**
 * sms用户管理
 */

@RestController
@RequestMapping("/sms/user")
public class SmsUserController extends AbstractController {
	@Autowired
	private SmsUserService smsUserService;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysUserRoleService sysUserRoleService;

	/**
	 * 跳转个人信息页面
	 * @return
     */
	@RequestMapping(value="info", method=RequestMethod.GET)
	@RequiresPermissions("sms:user:info")
	public ModelAndView info(){
		ModelAndView mav = new ModelAndView("modules/sms/user/info.jsp");
		//查询个人信息
		SysUserEntity user = getUser();
		mav.addObject("user", user);
		//查询老板信息，是否可以自定义短信签名，老板审核的状态
		mav.addObject("bossRela", smsUserService.queryByEmployeeId(user.getUserId()));
		List<SysMenuEntity> menus = MenuUtils.getMenuListInRedis(getUserId());
		mav.addObject("menus", menus);
		mav.addObject("curMenu", "sms/user/info");//当前菜单
		return mav;
	}

	/**
	 * 更改当前用户对应的老板号
	 * @param rela
	 * @return
     */
	@RequestMapping(value="updateBossTel",method = RequestMethod.POST)
	@RequiresPermissions("sms:user:updateBossTel")
	public R updateBossTel(@RequestBody SmsBossEmployeeRela rela){
		logger.info("updateBossTel rela=" + rela.toString());
		R r;
		ComplexResult validateRes = SmsUserValidator.validateUpdateBossTel(rela);
		if (!validateRes.isSuccess()) {
			return R.error(validateRes.getErrors());
		}
		SysUserEntity user = getUser();
		rela.setEmployeeId(user.getUserId());
		rela.setEmployeeName(user.getUsername());
		try{
			r = smsUserService.updateBossTel(rela);
			if(r != null){
				return r;
			}else{
				r = R.ok();
			}
		}catch (Exception e){
			logger.error("smsUserController.updateBossTel error", e);
			r = R.error("更新老板号码发生异常！");
		}
		return r;
	}

	/**
	 * 跳转sms员工信息管理页面
	 * @return
     */
	@RequestMapping(value="employeeList", method=RequestMethod.GET)
	@RequiresPermissions("sms:user:employeeList")
	public ModelAndView userList(){
		ModelAndView mav = new ModelAndView("modules/sms/user/employeeList.jsp");
		SysUserEntity user = getUser();
		List<SysMenuEntity> menus = MenuUtils.getMenuListInRedis(user.getUserId());
		mav.addObject("menus", menus);
		mav.addObject("curMenu", "sms/user/employeeList");//当前菜单
		return mav;
	}
	@RequestMapping(value="employeeList", method=RequestMethod.POST)
	@RequiresPermissions("sms:user:employeeList")
	public Object employeeList(@RequestBody SmsBossEmployeeRelaDto relaDto){
		R r;
		SysUserEntity user = getUser();
		try {
			SmsBossEmployeeRela rela = new SmsBossEmployeeRela();
			PageInfo pageInfo = new PageInfo();
			pageInfo.setPageNum(relaDto.getPageNum());
			pageInfo.setPageSize(relaDto.getPageSize());
			BeanUtils.copyProperties(relaDto, rela);
			rela.setBossId(user.getUserId());
			//获取当前用户的所有员工信息
			List<SmsBossEmployeeRela> employees = smsUserService.queryEmployeeList(rela, pageInfo);
			long total = ((Page)employees).getTotal();
			r = R.ok().put("rows", employees).put("total", total);
		}catch (Exception e){
			logger.error("employeeList error", e);
			r = R.error("查询员工列表出现异常！");
		}
		return r;
	}
	@RequestMapping(value="deleteEmployee", method=RequestMethod.POST)
	@RequiresPermissions("sms:user:deleteEmployee")
	public R deleteEmployee(@RequestBody SmsBossEmployeeRelaDto relaDto){
		R r;
		relaDto.setBossId(getUserId());
		try{
			r = smsUserService.deleteEmployee(relaDto);
			if(r == null){
				r = R.ok();
			}
		}catch (Exception e){
			logger.error("deleteEmployee error:", e);
			r = R.error("删除出现异常！");
		}
		return r;
	}

	@RequestMapping(value="changeSetHangup", method=RequestMethod.POST)
	@RequiresPermissions("sms:user:changeSetHangup")
	public R changeSetHangup(@RequestBody SmsBossEmployeeRelaDto relaDto){
		R r;
		relaDto.setBossId(getUserId());
		try{
			r = smsUserService.changeSetHangup(relaDto);
			if(r == null){
				r = R.ok();
			}
		}catch (Exception e){
			logger.error("changeSetHangup error:", e);
			r = R.error("修改出现异常！");
		}
		return r;
	}
	@RequestMapping(value="changeStatus", method=RequestMethod.POST)
	@RequiresPermissions("sms:user:changeStatus")
	public R changeStatus(@RequestBody SmsBossEmployeeRelaDto relaDto){
		R r;
		relaDto.setBossId(getUserId());
		try{
			r = smsUserService.changeStatus(relaDto);
			if(r == null){
				r = R.ok();
			}
		}catch (Exception e){
			logger.error("changeStatus error:", e);
			r = R.error("修改出现异常！");
		}
		return r;
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
