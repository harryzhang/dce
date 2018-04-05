package com.dce.business.actions.account;

import java.util.ArrayList;
import java.util.Date;
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
import com.dce.business.common.enums.AccountType;
import com.dce.business.common.result.Result;
import com.dce.business.common.util.DateUtil;
import com.dce.business.entity.account.UserAccountDetailDo;
import com.dce.business.entity.account.UserAccountDo;
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
            map.put("remark", detail.getRemark());
            result.add(map);
        }
        
        return Result.successResult("查询成功", result);
    }

    
    @RequestMapping(value = "/ethereum", method = {RequestMethod.POST,RequestMethod.GET})
    public Result<?> ethereum(){
    	//TODO
    	//调用以太坊接口获取以太坊账户余额
    	//调用用户账户获取累计收益
    	Map<String,Object> resultMap = new HashMap<String,Object>();
    	resultMap.put("ethereumNum", "21.29");
    	resultMap.put("totalIncome", "66.89");
    	return Result.successResult("查询成功!",resultMap);
    }
    
    /**
     * 以太坊转出
     * @return
     */
    @RequestMapping(value = "/ethereumOut", method = {RequestMethod.POST,RequestMethod.GET})
    public Result<?> ethereumOut(){
    	String receiver = getString("receiver");
    	String qty = getString("qty");
    	logger.info("转出以太坊: receiver=" + receiver + ",qty=" + qty);
    	//TODO
    	//调用用户服务查询receiver是否存在
    	//调用以太坊接口获取以太坊余额 ，并与qty比较 ，qty不能大于余额
    	//根据定义的以太坊转美元点比例将要转的以太坊转成美元点
    	//修改receiver的美元点账户,添加转换后的美元点数
    	//调用以太坊接口修改以太坊的账户余额(=原有余额-转出数) ,这个接口好像以太坊的文档里面没有
    	//记录流水
    	return Result.successResult("转出成功");
    }
    
    @RequestMapping(value = "/ethereumToCash", method = {RequestMethod.POST,RequestMethod.GET})
    public Result<?> ethereumToCash(){
    	String transPassWord = getString("transPassWord");
    	String qty = getString("qty");
    	logger.info("以太坊提现: transPassWord=" + transPassWord + ",qty=" + qty);
    	//TODO
    	//调用以太坊接口获取以太坊余额 ，并与qty比较 ，qty不能大于余额
    	//根据定义的以太坊转美元点比例将要转的以太坊转成美元点
    	//给自己的美元点账户添加美元点
    	//调用以太坊接口修改以太坊的账户余额(=原有余额-转出数) ,这个接口好像以太坊的文档里面没有
    	//记录流水
    	return Result.successResult("提现成功");
    }
    
    /** 
     * 账户基本信息
     * @return  
     */
    @RequestMapping(value = "/baseInfo", method = RequestMethod.GET)
    public Result<?> getTradeBaseInfo() {
        Integer userId = getUserId();
        logger.info("账户基本信息, userId:" + userId);

        UserAccountDo account = accountService.selectUserAccount(userId, AccountType.point.name());
        Map<String, Object> map = new HashMap<>();
        if(account != null){
        	
        	map.put("pointAmount", account.getAmount()); //美元点余额
        }else{
        	
        	map.put("pointAmount", "0.0"); //美元点余额
        }
        
        //TODO 此接口需要完善
        map.put("coinAmount", 121.09); //奖金币余额
         
        return Result.successResult("查询成功", map);
    }
    
    /**
     * 查询 现持仓、原始仓、美元点 余额
     * @return
     */
    @RequestMapping(value = "/amount", method = RequestMethod.GET)
    public Result<?> amount(){
    	
    	String accountType = getString("accountType");
    	
    	Integer userId = getUserId();
    	
    	logger.info("查询账户余额:accountType=" + accountType);
    	
    	UserAccountDo account = accountService.selectUserAccount(userId, accountType);
    	
    	if(account == null){
    		return Result.failureResult("未查询到用户当前账户信息!");
    	}else{
    		Map<String,Object> result = new HashMap<String,Object>();
    		result.put("totalIncome", account.getTotalInocmeAmount());
    		result.put("amount", account.getAmount());
    		return Result.successResult("获取账户信息成功!", result);
    	}
    }
    
    /**
     * 原始仓加金、复投 | 报单初始化
     * @return
     */
    @RequestMapping(value = "/currentInit", method = RequestMethod.GET)
    public Result<?> currentInit(){
    	Integer userId = getUserId();
    	return accountService.currentInit(userId);
    }
    /**
     * 现持仓加金
     * @return
     */
    @RequestMapping(value = "/currentAddMoney", method = RequestMethod.POST)
    public Result<?> currentAddMoney(){
    	
    	String qty = getString("qty");
    	
    	logger.info("现持仓加金:qty=" + qty);
    	
    	//TODO 
    	return Result.successResult("加金成功");
    }
    
    /**
     * 现持仓复投
     * @return
     */
    @RequestMapping(value = "/reCast", method = RequestMethod.POST)
    public Result<?> reCast(){
    	
    	String qty = getString("qty");
    	String buyType = getString("buyType");
    	
    	logger.info("现持仓复投:qty=" + qty + ",buyType=" + buyType);
    	
    	//TODO 
    	return Result.successResult("复投成功");
    }
    /**
     * 美元点转出
     * @return
     */
    @RequestMapping(value = "/pointOut", method = RequestMethod.POST)
    public Result<?> pointOut(){
    	
    	String qty = getString("qty");
    	String receiver = getString("receiver");
    	
    	logger.info("美元点转出:qty=" + qty + ",receiver=" + receiver);
    	
    	//TODO 
    	return Result.successResult("转出成功");
    }
    /**
     * 美元点提现
     * @return
     */
    @RequestMapping(value = "/pointToCash", method = RequestMethod.POST)
    public Result<?> pointToCash(){
    	
    	String qty = getString("qty");
    	String transPassWord = getString("transPassWord");
    	
    	logger.info("美元点提现:qty=" + qty + ",transPassWord=" + transPassWord);
    	
    	//TODO 
    	return Result.successResult("提现成功");
    }
    public static void main(String[] args) {
		System.out.println(new Date().getTime());
	}
}
