package cj.netos.ec.wybank.model;

/**
 * Table: shunt_record
 */
public class ShuntRecord {
    /**
     * Column: sn
     */
    private String sn;

    /**
     * Column: operator
     * Remark: 操作者: 通过api的操作者，或受申购触发而将申购人作为操作者
     */
    private String operator;

    /**
     * Column: person_name
     * Remark: 操作者中文名
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
     * Remark: -1分账失败 0分账中 1分账完成 
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
     * Column: source
     * Remark: 记录来源： 0 为通过api触发 1 为能过事件触发
     */
    private Integer source;

    /**
     * Column: shunters
     * Remark: 适用的分账规则表，以json方式存储
     */
    private String shunters;

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn == null ? null : sn.trim();
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
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

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public String getShunters() {
        return shunters;
    }

    public void setShunters(String shunters) {
        this.shunters = shunters == null ? null : shunters.trim();
    }
}