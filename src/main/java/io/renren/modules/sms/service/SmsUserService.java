package io.renren.modules.sms.service;



import com.github.pagehelper.PageInfo;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.sms.dto.SmsBossEmployeeRelaDto;
import io.renren.modules.sms.po.SmsBossEmployeeRela;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.po.SysUser;

import java.util.List;
import java.util.Map;

public interface SmsUserService {
	/**
	 * 根据用户id查询老板员工绑定关系
	 * @param userId
	 * @return
     */
	SmsBossEmployeeRela queryByEmployeeId(long userId);

	/**
	 * 更改当前用户对应的老板号
	 * @param rela
	 * @return
     */
	R updateBossTel(SmsBossEmployeeRela rela);

	/**
	 * 查询当前用户的所有员工
	 * @param user
	 * @return
     */
	List<SmsBossEmployeeRela> queryEmployeeList(SmsBossEmployeeRela rela, PageInfo pageInfo);

	/**
	 * 删除员工
	 * @param relaDto
	 * @return
     */
	R deleteEmployee(SmsBossEmployeeRelaDto relaDto);
}
