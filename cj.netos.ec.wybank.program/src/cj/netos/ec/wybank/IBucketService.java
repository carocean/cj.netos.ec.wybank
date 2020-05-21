package cj.netos.ec.wybank;

import cj.netos.ec.wybank.bo.*;
import cj.studio.ecm.net.CircuitException;
import cj.studio.openport.ISecuritySession;

import java.util.List;
import java.util.Map;

public interface IBucketService {

    abstract PriceResult getPriceBucket( String wenyBankID) throws CircuitException;

    abstract FundResult getFundBucket( String wenyBankID) throws CircuitException;

    FreezenResult getFreezenBucket( String wenyBankID) throws CircuitException;

    FreeResult getFreeBucket( String wenyBankID) throws CircuitException;

    StockResult getStockBucket( String wenyBankID) throws CircuitException;

    Map<String, Object> getAllBucketOfBank( String wenyBankID) throws CircuitException;

    List<ShuntResult> getAllShuntBucket( String wenyBankID, String shunters) throws CircuitException;

    ShuntResult getShuntBucket( String wenyBankID, String shunter) throws CircuitException;
}
