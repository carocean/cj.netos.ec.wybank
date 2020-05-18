package cj.netos.ec.wybank.model;

/**
 * Table: withdraw_record
 */
public class WithdrawRecord {
    /**
     * Column: sn
     */
    private String sn;

    /**
     * Column: withdrawer
     * Remark: 提现人
     */
    private String withdrawer;

    /**
     * Column: person_name
     * Remark: 提现者中文名
     */
    private String personName;

    /**
     * Column: req_amount
     * Remark: 请求的金额
     */
    private Long reqAmount;

    /**
     * Column: real_amount
     * Remark: 实际提到的金额
     */
    private Long realAmount;

    /**
     * Column: state
     * Remark: -1提现失败 0提现中 1提现完成 
     */
    private Integer state;

    /**
     * Column: bankid
     */
    private String bankid;

    /**
     * Column: ctime
     */
    private String ctime;

    /**
     * Column: dtime
     */
    private String dtime;

    /**
     * Column: note
     */
    private String note;

    /**
     * Column: status
     * Remark: 返回状态码
     */
    private String status;

    /**
     * Column: message
     * Remark: 返回结果
     */
    private String message;

    /**
     * Column: shunter
     * Remark: 向哪个分账器提现
     */
    private String shunter;

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn == null ? null : sn.trim();
    }

    public String getWithdrawer() {
        return withdrawer;
    }

    public void setWithdrawer(String withdrawer) {
        this.withdrawer = withdrawer == null ? null : withdrawer.trim();
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName == null ? null : personName.trim();
    }

    public Long getReqAmount() {
        return reqAmount;
    }

    public void setReqAmount(Long reqAmount) {
        this.reqAmount = reqAmount;
    }

    public Long getRealAmount() {
        return realAmount;
    }

    public void setRealAmount(Long realAmount) {
        this.realAmount = realAmount;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getBankid() {
        return bankid;
    }

    public void setBankid(String bankid) {
        this.bankid = bankid == null ? null : bankid.trim();
    }

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime == null ? null : ctime.trim();
    }

    public String getDtime() {
        return dtime;
    }

    public void setDtime(String dtime) {
        this.dtime = dtime == null ? null : dtime.trim();
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message == null ? null : message.trim();
    }

    public String getShunter() {
        return shunter;
    }

    public void setShunter(String shunter) {
        this.shunter = shunter == null ? null : shunter.trim();
    }
}