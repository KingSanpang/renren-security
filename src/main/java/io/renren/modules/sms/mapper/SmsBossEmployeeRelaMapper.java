package io.renren.modules.sms.mapper;

import io.renren.modules.sms.po.SmsBossEmployeeRela;
import io.renren.modules.sms.po.SmsBossEmployeeRelaExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SmsBossEmployeeRelaMapper {
    int countByExample(SmsBossEmployeeRelaExample example);

    int deleteByExample(SmsBossEmployeeRelaExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SmsBossEmployeeRela record);

    int insertSelective(SmsBossEmployeeRela record);

    List<SmsBossEmployeeRela> selectByExample(SmsBossEmployeeRelaExample example);

    SmsBossEmployeeRela selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SmsBossEmployeeRela record, @Param("example") SmsBossEmployeeRelaExample example);

    int updateByExample(@Param("record") SmsBossEmployeeRela record, @Param("example") SmsBossEmployeeRelaExample example);

    int updateByPrimaryKeySelective(SmsBossEmployeeRela record);

    int updateByPrimaryKey(SmsBossEmployeeRela record);
}