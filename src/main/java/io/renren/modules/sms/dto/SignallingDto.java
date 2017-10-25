package io.renren.modules.sms.dto;

import java.io.Serializable;

/**
 * <dl>
 * <dt>SignallingDto</dt>
 * <dd>Description:手机信令结构体</dd>
 * <dd>Copyright: Copyright (C) 2006</dd>
 * <dd>CreateDate: 2017-10-23</dd>
 * </dl>
 *
 * @author Administrator
 */
public class SignallingDto implements Serializable{

    private static final long serialVersionUID = -8260662821275574110L;

    private String streamingNo;//本消息唯一标示，防止重复提交，32位长度
    private String timeStamp;//当前时间戳，yyyyMMddHHmmssSSS
    private String fromNo;//来电号码
    private String mobileNo;//本机号码

    @Override
    public String toString() {
        return "SignallingDto{" +
                "streamingNo='" + streamingNo + '\'' +
                ", timeStamp='" + timeStamp + '\'' +
                ", fromNo='" + fromNo + '\'' +
                ", mobileNo='" + mobileNo + '\'' +
                '}';
    }

    public String getStreamingNo() {
        return streamingNo;
    }

    public void setStreamingNo(String streamingNo) {
        this.streamingNo = streamingNo;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getFromNo() {
        return fromNo;
    }

    public void setFromNo(String fromNo) {
        this.fromNo = fromNo;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }
}
