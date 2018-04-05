package com.dce.business.common.enums;

/** 
 * 账户流水类型
 * @author parudy
 * @date 2018年3月26日 
 * @version v1.0
 */
public enum IncomeType {
    /**
     * 静态释放
     */
    TYPE_STATIC("1", "静态释放"),
    /** 
     * 购买订单
     * @return  
     */
    TYPE_PURCHASE("11", "购买订单"),
    /** 
     * 卖出订单
     * @return  
     */
    TYPE_SELL("12", "卖出订单"),
    /** 
     * 充值
     * @return  
     */
    TYPE_RECHARGE("21", "充值"),
    /**
     * 提现
     */
    TYPE_WITHDRAW("22", "提现");

    private String incomeType;
    private String remark;

    IncomeType(String incomeType, String remark) {
        this.incomeType = incomeType;
        this.remark = remark;
    }

    public String getIncomeTypee() {
        return incomeType;
    }

    public void setIncomeType(String incomeType) {
        this.incomeType = incomeType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
