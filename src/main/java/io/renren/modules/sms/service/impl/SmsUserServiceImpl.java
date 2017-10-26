package io.renren.modules.sms.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.renren.common.constant.CommonConstants;
import io.renren.common.constant.ErrorConstants;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.sms.dto.SmsBossEmployeeRelaDto;
import io.renren.modules.sms.mapper.SmsBossEmployeeRelaMapper;
import io.renren.modules.sms.po.SmsBossEmployeeRela;
import io.renren.modules.sms.po.SmsBossEmployeeRelaExample;
import io.renren.modules.sms.service.SmsUserService;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.po.SysUser;
import io.renren.modules.sys.service.SysUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <dl>
 * <dt>SmsUserServiceImpl</dt>
 * <dd>Description:sms用户service</dd>
 * <dd>Copyright: Copyright (C) 2006</dd>
 * <dd>CreateDate: 2017-10-20</dd>
 * </dl>
 *
 * @author Administrator
 */
@Service("smsUserService")
public class SmsUserServiceImpl implements SmsUserService {
    @Autowired
    private SmsBossEmployeeRelaMapper smsBossEmployeeRelaMapper;
    @Autowired
    private SysUserService sysUserService;

    @Override
    public SmsBossEmployeeRela queryByEmployeeId(long userId) {
        SmsBossEmployeeRelaExample example = new SmsBossEmployeeRelaExample();
        example.createCriteria().andEmployeeIdEqualTo(userId)
                .andStatusNotEqualTo(CommonConstants.STATUS.DELETED.getValue());
        List<SmsBossEmployeeRela> relas = smsBossEmployeeRelaMapper.selectByExample(example);
        return relas.isEmpty() ? null : relas.get(0);
    }

    @Override
    public R updateBossTel(SmsBossEmployeeRela rela) {
        //查询老板id
        SysUserEntity boss = sysUserService.queryByUserName(rela.getBossTel());
        if(boss == null){
            return R.error(ErrorConstants.UserError.USERNAME_NOT_EXIST_ERROR, "老板号不存在！");
        }
        if(boss.getStatus().equals(CommonConstants.STATUS.NORMAL)){
            return R.error(-1, "已经通过老板审批不能修改。");
        }
        SmsBossEmployeeRela relaOld = queryByEmployeeId(rela.getEmployeeId());
        rela.setStatus(CommonConstants.STATUS.AUDITING.getValue());//审核中
        rela.setSetHangup(CommonConstants.STATUS.FORBIDDEN.getValue());//禁止设置短信签名
        rela.setBossId(boss.getUserId());
        if(relaOld == null){//不存在，新增
            smsBossEmployeeRelaMapper.insert(rela);
        }else{//存在，更新
            rela.setId(relaOld.getId());
            smsBossEmployeeRelaMapper.updateByPrimaryKey(rela);
        }
        return null;
    }

    @Override
    public List<SmsBossEmployeeRela> queryEmployeeList(SmsBossEmployeeRela rela, PageInfo pageInfo) {
        SmsBossEmployeeRelaExample example = new SmsBossEmployeeRelaExample();
        SmsBossEmployeeRelaExample.Criteria criteria = example.createCriteria();
        criteria.andStatusNotEqualTo(CommonConstants.STATUS.DELETED.getValue())
                .andBossIdEqualTo(rela.getBossId());
        if(StringUtils.isNotBlank(rela.getEmployeeName())){
            criteria.andEmployeeNameLike(rela.getEmployeeName() + "%");
        }
        PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize());
        List<SmsBossEmployeeRela> employees = smsBossEmployeeRelaMapper.selectByExample(example);
        return employees;
    }

    @Override
    public R deleteEmployee(SmsBossEmployeeRelaDto relaDto) {
        SmsBossEmployeeRelaExample example = new SmsBossEmployeeRelaExample();
        example.createCriteria().andBossIdEqualTo(relaDto.getBossId()).andIdEqualTo(relaDto.getId())
                .andStatusNotEqualTo(CommonConstants.STATUS.DELETED.getValue());
        SmsBossEmployeeRela updateRela = new SmsBossEmployeeRela();
        updateRela.setStatus(CommonConstants.STATUS.DELETED.getValue());
        int count = smsBossEmployeeRelaMapper.updateByExampleSelective(updateRela, example);
        if(count > 0){
            return null;
        }else{
            return R.error("数据不存在！");
        }
    }
    private SmsBossEmployeeRela queryById(long id){
        return smsBossEmployeeRelaMapper.selectByPrimaryKey(id);
    }

    @Override
    public R changeSetHangup(SmsBossEmployeeRelaDto relaDto) {
        SmsBossEmployeeRela oldRela = queryById(relaDto.getId());
        if(oldRela == null || !oldRela.getBossId().equals(relaDto.getBossId())){
            return R.error("记录不存在！");
        }
        byte newValue = 0;
        if(oldRela.getSetHangup().equals(CommonConstants.STATUS.FORBIDDEN.getValue())){
            newValue = 1;
        }
        SmsBossEmployeeRela updateRela = new SmsBossEmployeeRela();
        updateRela.setSetHangup(newValue);
        updateRela.setId(relaDto.getId());
        int count = smsBossEmployeeRelaMapper.updateByPrimaryKeySelective(updateRela);
        if(count > 0){
            return null;
        }else{
            return R.error("记录不存在！");
        }
    }

    @Override
    public R changeStatus(SmsBossEmployeeRelaDto relaDto) {
        SmsBossEmployeeRela oldRela = queryById(relaDto.getId());
        if(oldRela == null || !oldRela.getBossId().equals(relaDto.getBossId()) || oldRela.getStatus().equals(CommonConstants.STATUS.DELETED.getValue())){
            return R.error("记录不存在！");
        }
        SmsBossEmployeeRela updateRela = new SmsBossEmployeeRela();
        updateRela.setId(relaDto.getId());
        updateRela.setStatus(relaDto.getStatus());
        int count = smsBossEmployeeRelaMapper.updateByPrimaryKeySelective(updateRela);
        if(count > 0){
            return null;
        }else{
            return R.error("记录不存在！");
        }
    }
}
