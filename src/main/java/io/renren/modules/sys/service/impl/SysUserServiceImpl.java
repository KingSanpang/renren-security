package io.renren.modules.sys.service.impl;


import io.renren.common.annotation.DataFilter;
import io.renren.common.constant.CommonConstants;
import io.renren.common.constant.ConfigConstants;
import io.renren.modules.sys.constant.SysUserConstants;
import io.renren.modules.sys.mapper.SysUserMapper;
import io.renren.modules.sys.po.SysUser;
import io.renren.modules.sys.shiro.ShiroUtils;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.dao.SysUserDao;
import io.renren.modules.sys.service.SysUserRoleService;
import io.renren.modules.sys.service.SysUserService;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


/**
 * 系统用户
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年9月18日 上午9:46:09
 */
@Service("sysUserService")
public class SysUserServiceImpl implements SysUserService {
	@Autowired
	private SysUserDao sysUserDao;
	@Autowired
	private SysUserRoleService sysUserRoleService;
	@Autowired
	private SysUserMapper sysUserMapper;
	@Autowired
	private ConfigConstants configConstants;

	@Override
	public void register(SysUser user) {
		user.setCreateTime(new Date());
		//sha256加密
		String salt = RandomStringUtils.randomAlphanumeric(20);
		user.setSalt(salt);
		user.setPassword(ShiroUtils.sha256(user.getPassword(), user.getSalt()));
		this.initCommonUserInfo(user);
		sysUserMapper.insert(user);
		//保存用户与角色关系
		List<Long> userRoleList = new ArrayList<Long>(){{
			add(configConstants.getCommonUserRole());
		}};
		sysUserRoleService.saveOrUpdate(user.getUserId(), userRoleList);
	}

	/**
	 * 设置普通用户信息
	 * @param user
     */
	private void initCommonUserInfo(SysUser user){
		user.setDeptId(configConstants.getCommonUserDept());
		user.setUserType(SysUserConstants.USER_TYPE.EMPLOYEE.getValue());
		user.setMobile(user.getUsername());
		user.setStatus(CommonConstants.STATUS.NORMAL.getValue());
	}

	@Override
	public List<String> queryAllPerms(Long userId) {
		return sysUserDao.queryAllPerms(userId);
	}

	@Override
	public List<Long> queryAllMenuId(Long userId) {
		return sysUserDao.queryAllMenuId(userId);
	}

	@Override
	public SysUserEntity queryByUserName(String username) {
		return sysUserDao.queryByUserName(username);
	}
	
	@Override
	public SysUserEntity queryObject(Long userId) {
		return sysUserDao.queryObject(userId);
	}

	@Override
	@DataFilter(tableAlias = "u", user = false)
	public List<SysUserEntity> queryList(Map<String, Object> map){
		return sysUserDao.queryList(map);
	}
	
	@Override
	@DataFilter(tableAlias = "u", user = false)
	public int queryTotal(Map<String, Object> map) {
		return sysUserDao.queryTotal(map);
	}

	@Override
	@Transactional
	public void save(SysUserEntity user) {
		user.setCreateTime(new Date());
		//sha256加密
		String salt = RandomStringUtils.randomAlphanumeric(20);
		user.setSalt(salt);
		user.setPassword(ShiroUtils.sha256(user.getPassword(), user.getSalt()));
		sysUserDao.save(user);
		
		//保存用户与角色关系
		sysUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());
	}

	@Override
	@Transactional
	public void update(SysUserEntity user) {
		if(StringUtils.isBlank(user.getPassword())){
			user.setPassword(null);
		}else{
			user.setPassword(ShiroUtils.sha256(user.getPassword(), user.getSalt()));
		}
		sysUserDao.update(user);
		
		//保存用户与角色关系
		sysUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());
	}

	@Override
	@Transactional
	public void deleteBatch(Long[] userId) {
		sysUserDao.deleteBatch(userId);
	}

	@Override
	public int updatePassword(Long userId, String password, String newPassword) {
		Map<String, Object> map = new HashMap<>();
		map.put("userId", userId);
		map.put("password", password);
		map.put("newPassword", newPassword);
		return sysUserDao.updatePassword(map);
	}

}
