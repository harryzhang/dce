package com.dce.business.common.enums;

/** 
 * 账户类型
 * @author parudy
 * @date 2018年3月26日 
 * @version v1.0
 */
public enum AccountType {
    /**
     * 原始仓账户
     */
    original("original", "原始仓账户"),
    /** 
     * 现持仓账户
     * @return  
     */
    current("current", "现持仓账户"),
    /** 
     * 美元点账户
     * @return  
     */
    point("point", "美元点账户"),

    /**
     * 积分账户
     */
    score("score", "积分账户");

    private String accountType;
    private String remark;

    AccountType(String accountType, String remark) {
        this.accountType = accountType;
        this.remark = remark;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}