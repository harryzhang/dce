package com.dce.business.service.account;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.dce.business.common.enums.IncomeType;
import com.dce.business.entity.account.UserAccountDetailDo;
import com.dce.business.entity.account.UserAccountDo;

public interface IAccountService {

    /** 
     * 新建账户
     * @param userAccountDo
     * @return  
     */
    boolean createAccount(UserAccountDo userAccountDo);

    UserAccountDo getUserAccount(Integer userId);

    UserAccountDo selectUserAccount(Integer userId, String accountType);

    int updateUserAmountById(UserAccountDo userAccountDo, IncomeType type);

    void convertBetweenAccount(Integer sourceUserId, Integer targetUserId, BigDecimal amount, String fromAccount, String toAccount, IncomeType sourceMsg,
            IncomeType targetMsg);
    
    List<UserAccountDetailDo> selectUserAccountDetail(Map<String, Object> params);
    
    
}
