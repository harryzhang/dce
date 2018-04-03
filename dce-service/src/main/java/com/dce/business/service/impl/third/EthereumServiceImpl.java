package com.dce.business.service.impl.third;

import org.apache.log4j.Logger;

import com.dce.business.common.result.Result;
import com.dce.business.service.third.IEthereumService;

/** 
 * 以太坊接口
 * @author parudy
 * @date 2018年4月2日 
 * @version v1.0
 */ 
public class EthereumServiceImpl implements IEthereumService {
    private final static Logger logger = Logger.getLogger(EthereumServiceImpl.class);

    @Override
    public Result<?> creatAccount(Integer userId, String password) {
        return null;
    }
}
