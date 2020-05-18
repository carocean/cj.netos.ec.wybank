package cj.netos.ec.wybank.bo;

import cj.netos.ec.wybank.model.WithdrawRecord;

public class WithdrawBO {
    String withdrawer;
    String withdrawerName;
    String wenyBankID;
    WithdrawRecord record;
    public String getWithdrawerName() {
        return withdrawerName;
    }

    public void setWithdrawerName(String withdrawerName) {
        this.withdrawerName = withdrawerName;
    }


    public String getWithdrawer() {
        return withdrawer;
    }

    public void setWithdrawer(String withdrawer) {
        this.withdrawer = withdrawer;
    }


    public String getWenyBankID() {
        return wenyBankID;
    }

    public void setWenyBankID(String wenyBankID) {
        this.wenyBankID = wenyBankID;
    }

    public WithdrawRecord getRecord() {
        return record;
    }

    public void setRecord(WithdrawRecord record) {
        this.record = record;
    }
}
