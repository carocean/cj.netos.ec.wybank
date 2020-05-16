package cj.netos.ec.wybank.bo;

import cj.netos.ec.wybank.model.ExchangeRecord;

public class ExchangeResponse {
    String exchanger;
    String exchangerName;
    String wenyBankID;
    String status;
    String message;
    String recordSN;
    ExchangeRecord record;

    public String getRecordSN() {
        return recordSN;
    }

    public void setRecordSN(String recordSN) {
        this.recordSN = recordSN;
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

    public String getExchanger() {
        return exchanger;
    }

    public void setExchanger(String exchanger) {
        this.exchanger = exchanger;
    }

    public String getExchangerName() {
        return exchangerName;
    }

    public void setExchangerName(String exchangerName) {
        this.exchangerName = exchangerName;
    }

    public String getWenyBankID() {
        return wenyBankID;
    }

    public void setWenyBankID(String wenyBankID) {
        this.wenyBankID = wenyBankID;
    }

    public ExchangeRecord getRecord() {
        return record;
    }

    public void setRecord(ExchangeRecord record) {
        this.record = record;
    }
}
