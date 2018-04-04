package com.dce.business.actions.trade;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.dce.business.actions.common.BaseController;
import com.dce.business.common.result.Result;
import com.dce.business.common.util.DateUtil;
import com.dce.business.entity.account.UserAccountDo;
import com.dce.business.entity.trade.TradeDo;
import com.dce.business.entity.trade.TradeMarketDo;
import com.dce.business.service.account.IAccountService;
import com.dce.business.service.trade.ITradeMarketService;
import com.dce.business.service.trade.ITradeService;

/** 
 * 交易
 * @author parudy
 * @date 2018年3月25日 
 * @version v1.0
 */
@RestController
@RequestMapping("trade")
public class TradeController extends BaseController {
    private final static Logger logger = Logger.getLogger(TradeController.class);

    @Resource
    private ITradeService tradeService;
    @Resource
    private IAccountService accountService;
    @Resource
    private ITradeMarketService tradeMarketService;

    /** 
     * 查询交易明细
     * @return  
     */
    @RequestMapping(value = "/records", method = RequestMethod.POST)
    public Result<?> getTradeRecords() {
        Integer userId = getUserId();
        logger.info("查询交易明细, userId:" + userId);

        List<TradeDo> list = tradeService.getTradeRecords(userId);

        return Result.successResult("查询成功", convertTradeInfo(list));
    }

    /** 
     * 进入交易页面，买入时，查询用户基本信息、包括当前价格等
     * @return  
     */
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public Result<?> getTradeInfo() {
        Integer userId = getUserId();
        logger.info("进入交易页面, userId:" + userId);

        UserAccountDo userAccountDo = accountService.getUserAccount(userId);
        TradeMarketDo tradeMarketDo = tradeMarketService.getTradeMarketDo();

        Map<String, Object> map = new HashMap<>();
        map.put("pointAmount", userAccountDo.getPointAmount()); //美元点余额
        map.put("latestPrice", tradeMarketDo.getLatestprice()); //最新价格
        map.put("lowestPrice", tradeMarketDo.getLowestprice());//挂单范围最低价格
        map.put("highestPrice", tradeMarketDo.getHighestprice());//挂单范围最高价格
        map.put("feeRate", tradeMarketDo.getFeerate());//交易费率

        return Result.successResult("查询成功", map);
    }

    /** 
     * 交易页面基本信息
     * @return  
     */
    @RequestMapping(value = "/baseInfo", method = RequestMethod.GET)
    public Result<?> getTradeBaseInfo() {
        Integer userId = getUserId();
        logger.info("进入交易页面, userId:" + userId);

        //TODO 此接口需要完善
        Map<String, Object> map = new HashMap<>();
        map.put("latestPrice", 4.3606); //当前价格
        map.put("24hourHighestPrice", 4.3693); //24小时最高价
        map.put("24hourLowestPrice", 4.3693);//24小时最低价
        map.put("24hourVolume", 2088888);//24小时成交量
        map.put("rose", -0.18);//涨幅 
        map.put("roseRate", -5.29);//涨幅比例
        return Result.successResult("查询成功", map);
    }

    /** 
     * 以美元点购买DCE币
     * @return  
     */
    @RequestMapping(value = "/buy", method = RequestMethod.POST)
    public Result<?> buyCoin(@Valid TradeDo tradeDo, BindingResult bindingResult) {
        logger.info("用户交易：" + JSON.toJSONString(tradeDo));

        //参数校验
        if (bindingResult.hasErrors()) {
            List<ObjectError> errors = bindingResult.getAllErrors();
            logger.info("用户交易，参数校验错误：" + JSON.toJSONString(errors));
            return Result.failureResult(errors.get(0).getDefaultMessage());
        }

        return Result.successResult("购买成功");
    }

    private List<Map<String, Object>> convertTradeInfo(List<TradeDo> list) {
        List<Map<String, Object>> result = new ArrayList<>();
        for (TradeDo tradeDo : list) {
            Map<String, Object> map = new HashMap<>();
            if (tradeDo.getCtime() != null) {
                String time = tradeDo.getCtime().toString();
                if (time.length() == 10) {
                    time = time + "000";
                }
                map.put("time", DateUtil.dateToString(new Date(Long.valueOf(time)))); //成交时间
            }
            map.put("price", tradeDo.getPrice()); //单价
            map.put("num", tradeDo.getNum()); //成交量
            map.put("totalmoney", tradeDo.getTotalmoney()); //总金额
            result.add(map);
        }

        return result;
    }

    public static void main(String[] args) {
        Long time = 1517466887000L;
        System.out.println(new Date(time));

        java.sql.Date a = new java.sql.Date(1517466887);
        System.out.println(DateUtil.dateToString(a));
        System.out.println();
    }
}
