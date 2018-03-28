package com.dce.business.common.enums;

public enum AccountMsg {
	type_1(1, "激活豆购买证券;值"),
	type_3(3, "推荐用户购买证券;"),	
	type_2(2, "证券购买;值"),
	type_6(6, "用记提现减现金;值"),
	type_4(4, "静态分红;值"),
	type_7(7, "用户线下冲值;值"),
	type_10(10, "用户平台冲值;值"),
	type_11(11, "团队购买证金额;值"),
	type_12(12, "用户竞拍;值"),
	type_13(13, "用户竞猜;值"),
	type_14(14, "竞猜中奖;值"),
	type_8(8, "退本操作;值"),
	type_9(9, "用户退本减团队金额操作;值"),
	type_5(5, "团队奖金;值"), 
	type_20(20,"算力定存转出到算力钱包"), 
	type_21(21,"零钱包算力定存转入"),	
	type_30(30,"积分结转"),
	type_31(31,"积分转换"),
	type_32(32,"奖金豆转激活积分"),
	type_33(33,"现金豆转激活豆"),	
	type_34(34,"直推收入"),
	type_35(35,"趴点收入"),
	type_36(36, "市场卖出"),
	type_37(37, "市场卖入");

	
	
	
    AccountMsg(int accountType, String message) {
        this.accountType = accountType;
        this.message = message;
    }

    public int accountType;
    public String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getAccountType() {
        return accountType;
    }

    public void setAccountType(int accountType) {
        this.accountType = accountType;
    }
}
