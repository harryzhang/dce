package com.dce.business.actions.account;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dce.business.actions.common.BaseController;
import com.dce.business.common.result.Result;
import com.dce.business.common.util.DateUtil;
import com.dce.business.entity.account.UserAccountDetailDo;
import com.dce.business.service.account.IAccountService;

@RestController
@RequestMapping("/account")
public class AccountController extends BaseController {
    private final static Logger logger = Logger.getLogger(AccountController.class);
    @Resource
    private IAccountService accountService;

    /** 
     * 财务流水
     * @param userDo
     * @param bindingResult
     * @return  
     */
    @RequestMapping(value = "/flow", method = RequestMethod.POST)
    public Result<?> flow() {
        Integer userId = getUserId();
        String accountType = getString("accountType");

        Assert.hasText(accountType, "账户类型不能为空");
        logger.info("财务流水, userId:" + userId);

        String startTime = getString("startTime");
        String endTime = getString("endTime");

        Map<String, Object> params = new HashMap<>();
        params.put("accountType", accountType);
        params.put("userId", userId);
        if (StringUtils.isNotBlank(startTime)) {
            params.put("startTime", startTime);
        }
        if (StringUtils.isNotBlank(endTime)) {
            params.put("endTime", endTime);
        }
        List<UserAccountDetailDo> list = accountService.selectUserAccountDetail(params);
        List<Map<String, Object>> result = new ArrayList<>();
        for (UserAccountDetailDo detail : list) {
            Map<String, Object> map = new HashMap<>();
            map.put("userId", detail.getUserId()); //用户名
            map.put("userName", detail.getUserName()); //用户名
            map.put("flowType", "静态释放"); //流水类型 TODO
            map.put("amount", detail.getAmount().abs()); //变更数量
            map.put("blanceAmount", ""); //余额
            map.put("createTime", DateUtil.dateToString(detail.getCreateTime()));
            result.add(map);
        }
        
        return Result.successResult("查询成功", result);
    }

    /** 
     * 账户基本信息
     * @return  
     */
    @RequestMapping(value = "/baseInfo", method = RequestMethod.GET)
    public Result<?> getTradeBaseInfo() {
        Integer userId = getUserId();
        logger.info("账户基本信息, userId:" + userId);

        //TODO 此接口需要完善
        Map<String, Object> map = new HashMap<>();
        map.put("pointAmount", 2011.06); //美元点余额
        map.put("coinAmount", 121.09); //奖金币余额
         
        return Result.successResult("查询成功", map);
    }
}
