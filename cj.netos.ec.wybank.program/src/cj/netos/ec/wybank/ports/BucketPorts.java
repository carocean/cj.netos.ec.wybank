package cj.netos.ec.wybank.ports;

import cj.netos.ec.wybank.IBucketService;
import cj.netos.ec.wybank.bo.*;
import cj.studio.ecm.annotation.CjService;
import cj.studio.ecm.annotation.CjServiceRef;
import cj.studio.ecm.net.CircuitException;
import cj.studio.openport.ISecuritySession;

import java.util.List;
import java.util.Map;

@CjService(name = "/trade/balance.ports")
public class BucketPorts implements IBucketPorts {
    @CjServiceRef
    IBucketService bucketService;


    @Override
    public PriceResult getPriceBucket(ISecuritySession securitySession, String wenyBankID) throws CircuitException {
        return bucketService.getPriceBucket(wenyBankID);
    }

    @Override
    public FundResult getFundBucket(ISecuritySession securitySession, String wenyBankID) throws CircuitException {
        return bucketService.getFundBucket(wenyBankID);
    }

    @Override
    public FreezenResult getFreezenBucket(ISecuritySession securitySession, String wenyBankID) throws CircuitException {
        return bucketService.getFreezenBucket(wenyBankID);
    }

    @Override
    public FreeResult getFreeBucket(ISecuritySession securitySession, String wenyBankID) throws CircuitException {
        return bucketService.getFreeBucket(wenyBankID);
    }

    @Override
    public StockResult getStockBucket(ISecuritySession securitySession, String wenyBankID) throws CircuitException {
        return bucketService.getStockBucket(wenyBankID);
    }

    @Override
    public Map<String, Object> getAllBucketOfBank(ISecuritySession securitySession, String wenyBankID) throws CircuitException {
        return bucketService.getAllBucketOfBank(wenyBankID);
    }

    @Override
    public List<ShuntResult> getAllShuntBucket(ISecuritySession securitySession, String wenyBankID, String shunters) throws CircuitException {
        return bucketService.getAllShuntBucket(wenyBankID, shunters);
    }

    @Override
    public ShuntResult getShuntBucket(ISecuritySession securitySession, String wenyBankID, String shunter) throws CircuitException {
        return bucketService.getShuntBucket(wenyBankID, shunter);
    }
}
