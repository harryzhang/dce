package com.dce.business.service.account;

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
}
