package com.dce.business.dao.trade;

import java.util.List;
import java.util.Map;

import com.dce.business.entity.trade.TradeMarketDo;

public interface ITradeMarketDao {
    int deleteByPrimaryKey(Integer id);

    int insert(TradeMarketDo record);

    int insertSelective(TradeMarketDo record);

    TradeMarketDo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TradeMarketDo record);

    int updateByPrimaryKey(TradeMarketDo record);

    List<TradeMarketDo> selectTradeMarket(Map<String, Object> params);
}