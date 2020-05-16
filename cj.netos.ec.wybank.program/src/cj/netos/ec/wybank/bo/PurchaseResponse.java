package cj.netos.ec.wybank.bo;


import cj.netos.ec.wybank.model.PurchaseRecord;

public class PurchaseResponse {
    String purchaser;
    String purchaserName;
    String recordSN;
    String wenyBankID;
    String state;
    String message;
    PurchaseRecord record;

    public String getRecordSN() {
        return recordSN;
    }

    public void setRecordSN(String recordSN) {
        this.recordSN = recordSN;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPurchaser() {
        return purchaser;
    }

    public void setPurchaser(String purchaser) {
        this.purchaser = purchaser;
    }

    public String getPurchaserName() {
        return purchaserName;
    }

    public void setPurchaserName(String purchaserName) {
        this.purchaserName = purchaserName;
    }

    public String getWenyBankID() {
        return wenyBankID;
    }

    public void setWenyBankID(String wenyBankID) {
        this.wenyBankID = wenyBankID;
    }

    public PurchaseRecord getRecord() {
        return record;
    }

    public void setRecord(PurchaseRecord record) {
        this.record = record;
    }
}
