package cj.netos.ec.wybank.bo;

import cj.netos.ec.wybank.model.WithdrawRecord;

public class WithdrawResponse {
    String withdrawer;
    String withdrawerName;
    String wenyBankID;
    String status;
    String message;
    String recordSN;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRecordSN() {
        return recordSN;
    }

    public void setRecordSN(String recordSN) {
        this.recordSN = recordSN;
    }
}
