package com.dce.business.service.award;

import java.math.BigDecimal;

public interface IAwardService {

    /**
     * 计算静态释放奖励   
     */
    void calStatic();
    
    /**
     * 计算推荐奖 
     * @param userId
     * @param amount  
     */
    void calRecommendAward(Integer userId, BigDecimal amount);
}
