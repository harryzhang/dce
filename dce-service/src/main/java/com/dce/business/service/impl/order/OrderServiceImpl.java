package com.dce.business.service.impl.order;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dce.business.common.enums.AccountMsg;
import com.dce.business.common.enums.AccountType;
import com.dce.business.dao.order.IOrderDao;
import com.dce.business.dao.trade.IKLineDao;
import com.dce.business.entity.order.OrderDo;
import com.dce.business.entity.trade.KLineDo;
import com.dce.business.service.account.IAccountService;
import com.dce.business.service.order.IOrderService;

@Service("orderService")
public class OrderServiceImpl implements IOrderService {
    @Resource
    private IOrderDao orderDao;
    @Resource
    private IAccountService accountService;
    @Resource
    private IKLineDao kLineDao;

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public Long addOrder(OrderDo orderDo) {
        orderDao.insertSelective(orderDo);
        return orderDo.getOrderId();
    }

    @Override
    public OrderDo getOrderDo(Long orderId) {
        return orderDao.selectByPrimaryKey(orderId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void matchOrder(Integer userId, OrderDo matchOrder) {
        OrderDo newOrder = new OrderDo();
        newOrder.setCreateTime(new Date());
        newOrder.setGoodsId(matchOrder.getGoodsId());
        newOrder.setOrderStatus(2);
        newOrder.setQty(matchOrder.getQty());
        newOrder.setPayStatus(1);
        newOrder.setUserId(userId);
        newOrder.setOrderType(matchOrder.getOrderType().intValue() == 1 ? 2 : 1);
        newOrder.setPrice(matchOrder.getPrice());
        newOrder.setTotalPrice(matchOrder.getTotalPrice());
        newOrder.setOrderCode(matchOrder.getOrderCode());
        newOrder.setMatchOrderId(matchOrder.getOrderId());
        addOrder(newOrder);

        Map<String, Object> paraMap = new HashMap<String, Object>();
        paraMap.put("newStatus", 2);
        paraMap.put("oldStatus", 1);
        paraMap.put("matchOrderId", newOrder.getOrderId());
        paraMap.put("orderId", newOrder.getMatchOrderId());
        orderDao.updateOrderStatusByOldStatus(paraMap);

        //更新账户金额
        Integer orderType = newOrder.getOrderType(); //如果是买，则购买者减少美元点账户，添加币账户
        if (orderType == 1) {
            accountService.convertBetweenAccount(newOrder.getUserId(), matchOrder.getUserId(), newOrder.getTotalPrice(),
                    AccountType.point.getAccountType(), AccountType.point.getAccountType(), AccountMsg.type_1, AccountMsg.type_1);
            accountService.convertBetweenAccount(matchOrder.getUserId(), newOrder.getUserId(), newOrder.getQty(),
                    AccountType.current.getAccountType(), AccountType.current.getAccountType(), AccountMsg.type_1, AccountMsg.type_1);
        } else {
            accountService.convertBetweenAccount(matchOrder.getUserId(), newOrder.getUserId(), newOrder.getTotalPrice(),
                    AccountType.point.getAccountType(), AccountType.point.getAccountType(), AccountMsg.type_1, AccountMsg.type_1);
            accountService.convertBetweenAccount(newOrder.getUserId(), matchOrder.getUserId(), newOrder.getQty(),
                    AccountType.current.getAccountType(), AccountType.current.getAccountType(), AccountMsg.type_1, AccountMsg.type_1);
        }

        //记录K线数据
        KLineDo kLineDo = new KLineDo();
        kLineDo.setPrice(matchOrder.getPrice());
        kLineDo.setQty(matchOrder.getQty());
        kLineDo.setTotalAmount(matchOrder.getTotalPrice());
        kLineDo.setCtime(new Date());
        kLineDao.insertSelective(kLineDo);
    }

    @Override
    public List<OrderDo> selectOrder(Map<String, Object> params) {
        return orderDao.selectOrder(params);
    }
}
