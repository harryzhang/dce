package com.dce.business.actions.mall;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

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

/** 
 * 商城
 * @author parudy
 * @date 2018年4月4日 
 * @version v1.0
 */
@RestController
@RequestMapping("/mall")
public class MallController extends BaseController {
    private final static Logger logger = Logger.getLogger(MallController.class);

    /** 
     * 商品列表
     * @return  
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result<?> flow() {
        Integer userId = getUserId();

        //分页查询 每次10页
        //TODO 此接口需要完善

        List<Map<String, Object>> result = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("imgUrl", "https://images.qianshenghua.com/s_1/static/article/images/201804/article_1522824240130528.png"); //用户名
            map.put("productId", i);
            map.put("productName", "加斯链" + i);
            map.put("soldQty", random.nextInt(10000));
            map.put("price", 998.6685);
            map.put("barginPrice", 98.6698);
            result.add(map);
        }

        return Result.successResult("查询成功", result);
    }
    
    
    
    /** 
     * 设置收货地址
     * @return  
     */
    @RequestMapping(value = "/address", method = RequestMethod.POST)
    public Result<?> setAddress() {
        Integer userId = getUserId();

        //TODO 此接口需要完善

         

        return Result.successResult("成功");
    }
    
    /** 
     * 查看商品详情
     * @return  
     */
    @RequestMapping(value = "/product/detail", method = RequestMethod.POST)
    public Result<?> getProductDetail() {
        Integer userId = getUserId();

        //TODO 此接口需要完善
        Random random = new Random();
        Map<String, Object> map = new HashMap<>();
        map.put("imgUrl", "https://images.qianshenghua.com/s_1/static/article/images/201804/article_1522824240130528.png"); //用户名
        map.put("productName", "加斯链");
        map.put("soldQty", random.nextInt(10000));
        map.put("price", 998.6685);
        map.put("barginPrice", 98.6698);
         

        return Result.successResult("成功", map);
    }
    
    /** 
     * 查看商品详情
     * @return  
     */
    @RequestMapping(value = "/product/buy", method = RequestMethod.POST)
    public Result<?> buyProduct() {
        Integer userId = getUserId();

        //TODO 此接口需要完善
         

        return Result.successResult("成功");
    }

}
