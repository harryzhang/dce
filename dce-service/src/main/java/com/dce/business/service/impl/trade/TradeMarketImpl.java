package com.dce.business.service.impl.trade;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.dce.business.dao.trade.ITradeMarketDao;
import com.dce.business.entity.trade.TradeMarketDo;
import com.dce.business.service.trade.ITradeMarketService;

@Service("tradeMarker")
public class TradeMarketImpl implements ITradeMarketService {
    @Resource
    private ITradeMarketDao tradeMarketDao;

    @Override
    public TradeMarketDo getTradeMarketDo() {
        List<TradeMarketDo> list = tradeMarketDao.selectTradeMarket(new HashMap<String, Object>());
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list.get(0);
    }

}
