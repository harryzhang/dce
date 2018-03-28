package com.dce.business.actions.order;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dce.business.actions.common.BaseController;
import com.dce.business.common.enums.AccountType;
import com.dce.business.common.result.Result;
import com.dce.business.common.util.OrderCodeUtil;
import com.dce.business.entity.account.UserAccountDo;
import com.dce.business.entity.order.OrderDo;
import com.dce.business.service.account.IAccountService;
import com.dce.business.service.order.IOrderService;

@RestController
@RequestMapping("order")
public class OrderController extends BaseController {
    private final static Logger logger = Logger.getLogger(OrderController.class);

    @Resource
    private IOrderService orderService;
    @Resource
    private IAccountService accountService;

    /** 
     * 挂单
     * @return  
     */
    @RequestMapping(value = "/guadan", method = RequestMethod.POST)
    public Result<?> guadan() {
        Integer userId = getUserId();
        String price = getString("price");
        String qty = getString("qty");
        String orderType = getString("orderType");//1--买入；2--卖出
        logger.info("用户挂单, userId:" + userId + "; price:" + price + "; qty:" + qty + "; orderType:" + orderType);
        Assert.hasText(price, "买入价格不能为空");
        Assert.hasText(qty, "买入数量不能为空");
        Assert.hasText(orderType, "挂单类型不能为空");

        OrderDo orderDo = new OrderDo();
        orderDo.setOrderCode(OrderCodeUtil.genOrderCode(userId));
        orderDo.setOrderType(Integer.valueOf(orderType));
        orderDo.setUserId(userId);
        orderDo.setPrice(new BigDecimal(price));
        orderDo.setQty(new BigDecimal(qty));
        orderDo.setTotalPrice(orderDo.getQty().multiply(orderDo.getPrice()).setScale(4, RoundingMode.HALF_UP));
        orderDo.setOrderStatus(1); //有效
        orderDo.setPayStatus(0); //未成交
        orderDo.setCreateTime(new Date());
        Long orderId = orderService.addOrder(orderDo);

        return Result.successResult("挂单成功", orderId);
    }

    /** 
     * 买入、卖出
     * @return  
     */
    @RequestMapping(value = "/matchOrder", method = RequestMethod.POST)
    public Result<?> matchOrder() {
        Integer userId = getUserId();
        String orderId = getString("orderId"); //订单id

        Assert.notNull(orderId, "请选择要交易的订单");
        OrderDo matchOrder = orderService.getOrderDo(Long.valueOf(orderId));

        //不能给自己匹配
        Assert.isTrue(userId.intValue() != matchOrder.getUserId().intValue(), "不能匹配自己挂的单");
        Assert.isTrue(matchOrder.getPayStatus() == 0, "订单已被匹配");

        //买单,计算账号余额
        if (matchOrder.getOrderType().intValue() == 2) {
            UserAccountDo userAccount = accountService.selectUserAccount(userId, AccountType.point.getAccountType());
            boolean canBuy = true;
            if (null == userAccount || userAccount.getAmount() == null) {
                canBuy = false;
            } else {
                canBuy = userAccount.getAmount().compareTo(matchOrder.getTotalPrice()) >= 0 ? true : false;
            }
            Assert.isTrue(canBuy, "账号余额不足");
        }

        orderService.matchOrder(userId, matchOrder);

        return Result.successResult("成功", orderId);
    }
}
