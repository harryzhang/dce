package com.dce.business.entity.account;

import java.math.BigDecimal;
import java.util.Date;

public class UserAccountDo {
    private Integer id;

    private Integer userId;

    private String accountType;

    private BigDecimal amount;

    private BigDecimal originalAmount;

    private BigDecimal pointAmount;

    private BigDecimal scoreAmount;

    private BigDecimal frozenDeposit;

    private BigDecimal totalConsumeAmount;

    private BigDecimal totalInocmeAmount;

    private BigDecimal incomeAmount;

    private BigDecimal consumeAmount;

    private String withdrawTotalDeposit;

    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getOriginalAmount() {
        return originalAmount;
    }

    public void setOriginalAmount(BigDecimal originalAmount) {
        this.originalAmount = originalAmount;
    }

    public BigDecimal getPointAmount() {
        return pointAmount;
    }

    public void setPointAmount(BigDecimal pointAmount) {
        this.pointAmount = pointAmount;
    }

    public BigDecimal getScoreAmount() {
        return scoreAmount;
    }

    public void setScoreAmount(BigDecimal scoreAmount) {
        this.scoreAmount = scoreAmount;
    }

    public BigDecimal getFrozenDeposit() {
        return frozenDeposit;
    }

    public void setFrozenDeposit(BigDecimal frozenDeposit) {
        this.frozenDeposit = frozenDeposit;
    }

    public BigDecimal getTotalConsumeAmount() {
        return totalConsumeAmount;
    }

    public void setTotalConsumeAmount(BigDecimal totalConsumeAmount) {
        this.totalConsumeAmount = totalConsumeAmount;
    }

    public BigDecimal getTotalInocmeAmount() {
        return totalInocmeAmount;
    }

    public void setTotalInocmeAmount(BigDecimal totalInocmeAmount) {
        this.totalInocmeAmount = totalInocmeAmount;
    }

    public BigDecimal getIncomeAmount() {
        return incomeAmount;
    }

    public void setIncomeAmount(BigDecimal incomeAmount) {
        this.incomeAmount = incomeAmount;
    }

    public BigDecimal getConsumeAmount() {
        return consumeAmount;
    }

    public void setConsumeAmount(BigDecimal consumeAmount) {
        this.consumeAmount = consumeAmount;
    }

    public String getWithdrawTotalDeposit() {
        return withdrawTotalDeposit;
    }

    public void setWithdrawTotalDeposit(String withdrawTotalDeposit) {
        this.withdrawTotalDeposit = withdrawTotalDeposit;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}