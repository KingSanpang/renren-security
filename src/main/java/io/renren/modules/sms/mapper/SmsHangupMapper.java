package io.renren.modules.sms.mapper;

import io.renren.modules.sms.po.SmsHangup;
import io.renren.modules.sms.po.SmsHangupExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SmsHangupMapper {
    int countByExample(SmsHangupExample example);

    int deleteByExample(SmsHangupExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SmsHangup record);

    int insertSelective(SmsHangup record);

    List<SmsHangup> selectByExample(SmsHangupExample example);

    SmsHangup selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SmsHangup record, @Param("example") SmsHangupExample example);

    int updateByExample(@Param("record") SmsHangup record, @Param("example") SmsHangupExample example);

    int updateByPrimaryKeySelective(SmsHangup record);

    int updateByPrimaryKey(SmsHangup record);
}