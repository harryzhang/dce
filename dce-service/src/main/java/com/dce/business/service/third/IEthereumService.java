package com.dce.business.service.third;

import com.dce.business.common.result.Result;

public interface IEthereumService {

    /** 
     * 以太坊开户
     * @param userId 用户id
     * @param password 以太坊密码
     * @return  
     */
    Result<?> creatAccount(Integer userId, String password);
}
