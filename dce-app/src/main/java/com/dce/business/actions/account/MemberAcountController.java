package com.dce.business.actions.account;

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
import com.dce.business.actions.order.OrderController;
import com.dce.business.common.result.Result;
import com.dce.business.common.util.OrderCodeUtil;
import com.dce.business.entity.order.OrderDo;
import com.dce.business.service.order.IOrderService;

/**
 * 会员充值、提现
 * 
 * @author zhangcymf
 * 
 */
@RestController
@RequestMapping("/memberAccount")
public class MemberAcountController extends BaseController {

	private final static Logger logger = Logger.getLogger(MemberAcountController.class);
	
	@Resource
    private IOrderService orderService;
	
	@RequestMapping(value = "/recharge", method = RequestMethod.POST)
	public Result<?> recharge() {
		Integer userId = getUserId();
		String price = getString("price");
		String qty = getString("qty");
		String password = getString("password");
		logger.info("会员充值, userId:" + userId + "; price:" + price + "; qty:"+ qty );
		Assert.hasText(price, "买入价格不能为空");
		Assert.hasText(qty, "买入数量不能为空");

		OrderDo orderDo = new OrderDo();
		orderDo.setOrderCode(OrderCodeUtil.genOrderCode(userId));
		orderDo.setOrderType(Integer.valueOf(1));
		orderDo.setUserId(userId);
		orderDo.setPrice(new BigDecimal(price));
		orderDo.setQty(new BigDecimal(qty));
		orderDo.setTotalPrice(orderDo.getQty().multiply(orderDo.getPrice()).setScale(4, RoundingMode.HALF_UP));
		orderDo.setOrderStatus(1); // 有效
		orderDo.setPayStatus(0); // 未成交
		orderDo.setCreateTime(new Date());
		Long orderId = orderService.addOrder(orderDo);

		return Result.successResult("充值成功", orderId);
	}

}
