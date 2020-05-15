package cj.netos.ec.wybank.bo;

import cj.netos.ec.wybank.model.ExchangeRecord;

public class ExchangeWenyBO {
    String exchanger;
    String exchangerName;
    String wenyBankID;
    ExchangeRecord record;

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
