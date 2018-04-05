package com.dce.business.actions.news;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dce.business.actions.common.BaseController;
import com.dce.business.common.result.Result;
import com.dce.business.service.award.IAwardService;

/** 
 * 新闻公告、消息列表
 * @author parudy
 * @date 2018年4月4日 
 * @version v1.0
 */
@RestController
@RequestMapping("/news")
public class NewsController extends BaseController {
    private final static Logger logger = Logger.getLogger(NewsController.class);
    @Resource
    private IAwardService awardService;

    /** 
     * 新闻公告列表
     * @return  
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result<?> flow() {
        Integer userId = getUserId();

        //分页查询 每次10页
        //TODO 此接口需要完善

        List<Map<String, Object>> result = new ArrayList<>();

        Map<String, Object> map = new HashMap<>();
        map.put("title", "网络环境的不稳定性");
        map.put("content", "大部分网络公开投资项目和电子交易的设施是通过以电脑为基础的系统来进行交易指令的传递、执行、匹配和交易。与所有的设施和系统一样，他们易受到临时故障的影响，包括硬体和软体的故障，系统故障可能造成客户的操作难以按照客户的指示执行或根本不能执行");
        result.add(map);
        map = new HashMap<>();
        map.put("title", "交易的条件与条款");
        map.put("content", "客户在钱生花上交易必须接受钱生花的条件与条款，客户在开始交易前应仔细阅读相关条件与条款。");
        result.add(map);

        return Result.successResult("查询成功", result);
    }

    /** 
     * 测试
     * @return  
     */
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public Result<?> test() {
        Integer userId = getUserId();
        awardService.calTouchAward(userId);
        return Result.successResult("查询成功");
    }
}