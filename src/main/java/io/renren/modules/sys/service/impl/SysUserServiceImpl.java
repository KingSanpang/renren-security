package io.renren.modules.sys.service.impl;


import io.renren.common.annotation.DataFilter;
import io.renren.common.constant.CommonConstants;
import io.renren.common.constant.ConfigConstants;
import io.renren.common.constant.ErrorConstants;
import io.renren.common.utils.R;
import io.renren.modules.sys.constant.SysUserConstants;
import io.renren.modules.sys.mapper.SysUserMapper;
import io.renren.modules.sys.po.SysUser;
import io.renren.modules.sys.po.SysUserExample;
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
	public R register(SysUser user) {
		SysUser tempUser = this.queryByUserNameAndStatus(user);
		if(tempUser != null){
			return R.error(ErrorConstants.USER_ERROR.USERNAME_EXIST_ERROR, "用户名已经存在！");
		}
		user.setCreateTime(new Date());
		//sha256加密
		String salt = RandomStringUtils.randomAlphanumeric(20);
		user.setSalt(salt);
		user.setPassword(ShiroUtils.sha256(user.getPassword(), user.getSalt()));
		this.initCommonUserInfo(user);
		sysUserMapper.insert(user);
		List<Long> userRoleList = new ArrayList<Long>();
		if(user.getUserType().equals(SysUserConstants.USER_TYPE.BOSS)){
			//保存用户与角色关系
			 userRoleList.add(configConstants.getBossUserRole());
		}else{//员工
			//保存用户与角色关系
			userRoleList.add(configConstants.getCommonUserRole());
		}
		sysUserRoleService.saveOrUpdate(user.getUserId(), userRoleList);
		return null;
	}

	/**
	 * 设置普通用户信息
	 * @param user
     */
	private void initCommonUserInfo(SysUser user){
		user.setDeptId(configConstants.getCommonUserDept());
		user.setMobile(user.getUsername());
		user.setStatus(CommonConstants.STATUS.NORMAL.getValue());
	}
	private SysUser queryByUserNameAndStatus(SysUser user){
		SysUserExample example = new SysUserExample();
		example.createCriteria().andUsernameEqualTo(user.getUsername()).andStatusNotEqualTo(CommonConstants.STATUS.DELETED.getValue());
		List<SysUser> users = sysUserMapper.selectByExample(example);
		return users.isEmpty() ? null : users.get(0);
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
