package com.dce.business.entity.trade;

import java.math.BigDecimal;

public class TradeMarketDo {
    private Integer id;

    private BigDecimal latestprice;

    private BigDecimal lowestprice;

    private BigDecimal highestprice;

    private BigDecimal feerate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getLatestprice() {
        return latestprice;
    }

    public void setLatestprice(BigDecimal latestprice) {
        this.latestprice = latestprice;
    }

    public BigDecimal getLowestprice() {
        return lowestprice;
    }

    public void setLowestprice(BigDecimal lowestprice) {
        this.lowestprice = lowestprice;
    }

    public BigDecimal getHighestprice() {
        return highestprice;
    }

    public void setHighestprice(BigDecimal highestprice) {
        this.highestprice = highestprice;
    }

    public BigDecimal getFeerate() {
        return feerate;
    }

    public void setFeerate(BigDecimal feerate) {
        this.feerate = feerate;
    }
}