package cj.netos.ec.wybank.ports;

import cj.netos.ec.wybank.bo.*;
import cj.studio.ecm.net.CircuitException;
import cj.studio.openport.AccessTokenIn;
import cj.studio.openport.IOpenportService;
import cj.studio.openport.ISecuritySession;
import cj.studio.openport.PKeyInRequest;
import cj.studio.openport.annotations.CjOpenport;
import cj.studio.openport.annotations.CjOpenportAppSecurity;
import cj.studio.openport.annotations.CjOpenportParameter;
import cj.studio.openport.annotations.CjOpenports;

import java.util.List;
import java.util.Map;

@CjOpenports(usage = "余额类查询交易")
public interface IBucketPorts extends IOpenportService {
    @CjOpenportAppSecurity
    @CjOpenport(usage = "当前价格", tokenIn = AccessTokenIn.nope)
    PriceResult getPriceBucket(ISecuritySession securitySession,
                               @CjOpenportParameter(usage = "纹银银行号", name = "wenyBankID") String wenyBankID
    ) throws CircuitException;


    @CjOpenportAppSecurity
    @CjOpenport(usage = "当前资金余额", tokenIn = AccessTokenIn.nope)
    FundResult getFundBucket(ISecuritySession securitySession,
                             @CjOpenportParameter(usage = "纹银银行号", name = "wenyBankID") String wenyBankID
    ) throws CircuitException;


    @CjOpenportAppSecurity
    @CjOpenport(usage = "当前冻结资金余额", tokenIn = AccessTokenIn.nope)
    FreezenResult getFreezenBucket(ISecuritySession securitySession,
                                   @CjOpenportParameter(usage = "纹银银行号", name = "wenyBankID") String wenyBankID
    ) throws CircuitException;


    @CjOpenportAppSecurity
    @CjOpenport(usage = "当前自由资金余额", tokenIn = AccessTokenIn.nope)
    FreeResult getFreeBucket(ISecuritySession securitySession,
                             @CjOpenportParameter(usage = "纹银银行号", name = "wenyBankID") String wenyBankID
    ) throws CircuitException;


    @CjOpenportAppSecurity
    @CjOpenport(usage = "当前纹银存量余额", tokenIn = AccessTokenIn.nope)
    StockResult getStockBucket(ISecuritySession securitySession,
                               @CjOpenportParameter(usage = "纹银银行号", name = "wenyBankID") String wenyBankID
    ) throws CircuitException;

    @CjOpenportAppSecurity
    @CjOpenport(usage = "获取一个纹银银行的所有余额类，除了分账类余额", tokenIn = AccessTokenIn.nope)
    Map<String, Object> getAllBucketOfBank(
            ISecuritySession securitySession,
            @CjOpenportParameter(usage = "纹银银行号", name = "wenyBankID") String wenyBankID
    ) throws CircuitException;

    @CjOpenportAppSecurity
    @CjOpenport(usage = "当前纹银银行分账类余额", tokenIn = AccessTokenIn.nope, command = "post")
    List<ShuntResult> getAllShuntBucket(ISecuritySession securitySession,
                                        @CjOpenportParameter(usage = "纹银银行号", name = "wenyBankID") String wenyBankID
            , @CjOpenportParameter(usage = "分账器", name = "shunters", in = PKeyInRequest.content) String shunters
    ) throws CircuitException;


    @CjOpenportAppSecurity
    @CjOpenport(usage = "当前分账类余额", tokenIn = AccessTokenIn.nope)
    ShuntResult getShuntBucket(ISecuritySession securitySession,
                               @CjOpenportParameter(usage = "纹银银行号", name = "wenyBankID") String wenyBankID,
                               @CjOpenportParameter(usage = "分账类别", name = "shunter") String shunter
    ) throws CircuitException;
}
