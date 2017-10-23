package io.renren.modules.sms.po;

public class SmsBossEmployeeRela {
    private Long id;

    private Long bossId;

    private String bossTel;

    private Long employeeId;

    private String employeeName;

    private Byte setHangup;

    private Byte status;

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