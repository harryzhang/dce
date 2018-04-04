package com.dce.business.actions.account;

import org.apache.log4j.Logger;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dce.business.actions.common.BaseController;
import com.dce.business.common.result.Result;

@RestController
@RequestMapping("/recharge")
public class RechargeController extends BaseController {
    private final static Logger logger = Logger.getLogger(RechargeController.class);

    /** 
     * 用户充值
     * @return  
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Result<?> flow() {
        Integer userId = getUserId();
        String password = getString("password");
        String qty = getString("qty");

        Assert.hasText(password, "充值密码不能为空");
        Assert.hasText(qty, "充值以太坊数量不能为空");
        logger.info("用户充值, userId:" + userId + "; qty:" + qty);

        //TODO 
        //1、调用以太坊接口充值
        //2、给美元点账户添加金额
        //3、记录流水
        return Result.successResult("充值成功");
    }

}
