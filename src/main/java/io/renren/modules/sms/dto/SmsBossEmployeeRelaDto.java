package io.renren.modules.sms.dto;

/**
 * <dl>
 * <dt>SmsBossEmployeeRelaDto</dt>
 * <dd>Description:</dd>
 * <dd>Copyright: Copyright (C) 2006</dd>
 * <dd>Company: 青牛（北京）技术有限公司</dd>
 * <dd>CreateDate: 2017-10-23</dd>
 * </dl>
 *
 * @author Administrator
 */
public class SmsBossEmployeeRelaDto {
    private Long id;

    private Long bossId;

    private String bossTel;

    private Long employeeId;

    private String employeeName;

    private Byte setHangup;

    private Byte status;
    private int pageNum;
    private int pageSize;

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBossId() {
        return bossId;
    }

    public void setBossId(Long bossId) {
        this.bossId = bossId;
    }

    public String getBossTel() {
        return bossTel;
    }

    public void setBossTel(String bossTel) {
        this.bossTel = bossTel;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public Byte getSetHangup() {
        return setHangup;
    }

    public void setSetHangup(Byte setHangup) {
        this.setHangup = setHangup;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }
}
