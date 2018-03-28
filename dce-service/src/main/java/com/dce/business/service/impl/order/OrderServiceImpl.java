package com.dce.business.service.impl.order;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dce.business.dao.order.IOrderDao;
import com.dce.business.entity.order.OrderDo;
import com.dce.business.service.order.IOrderService;

@Service("orderService")
public class OrderServiceImpl implements IOrderService {
    @Resource
    private IOrderDao orderDao;

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
    public void matchOrder(OrderDo newOrderDo) {
        addOrder(newOrderDo);
        Map<String, Object> paraMap = new HashMap<String, Object>();
        paraMap.put("newStatus", 2);
        paraMap.put("oldStatus", 1);
        paraMap.put("matchOrderId", newOrderDo.getOrderId());
        paraMap.put("orderId", newOrderDo.getMatchOrderId());
        //更改匹配的订单状态为交易中
        orderDao.updateOrderStatusByOldStatus(paraMap);
        
        //TODO 添加账户变更消息
        
    }
}
