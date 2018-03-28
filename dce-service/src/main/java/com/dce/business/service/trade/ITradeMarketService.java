package com.dce.business.service.trade;

import com.dce.business.entity.trade.TradeMarketDo;

public interface ITradeMarketService {

    /**
     * 获取当前交易市场行情 
     * @return  
     */
    TradeMarketDo getTradeMarketDo();
}
