package com.dce.business.service.impl.order;

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
    public void matchOrder(OrderDo newOrderDo) {
    }
}
