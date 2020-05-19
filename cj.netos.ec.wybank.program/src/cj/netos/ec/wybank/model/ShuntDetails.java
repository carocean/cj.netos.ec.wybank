package cj.netos.ec.wybank.model;

import java.math.BigDecimal;

/**
 * Table: shunt_details
 */
public class ShuntDetails {
    /**
     * Column: id
     */
    private String id;

    /**
     * Column: shunter
     */
    private String shunter;

    /**
     * Column: amount
     * Remark: 分账金额
     */
    private Long amount;

    /**
     * Column: ratio
     */
    private BigDecimal ratio;

    /**
     * Column: shunt_sn
     * Remark: 关联分账记录
     */
    private String shuntSn;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getShunter() {
        return shunter;
    }

    public void setShunter(String shunter) {
        this.shunter = shunter == null ? null : shunter.trim();
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public BigDecimal getRatio() {
        return ratio;
    }

    public void setRatio(BigDecimal ratio) {
        this.ratio = ratio;
    }

    public String getShuntSn() {
        return shuntSn;
    }

    public void setShuntSn(String shuntSn) {
        this.shuntSn = shuntSn == null ? null : shuntSn.trim();
    }
}