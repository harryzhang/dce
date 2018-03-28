package com.dce.business.service.order;

import com.dce.business.entity.order.OrderDo;

public interface IOrderService {

    /** 
     * 添加订单
     * @param orderDo  
     */
    Long addOrder(OrderDo orderDo);
    
    OrderDo getOrderDo(Long orderId);
    
    void matchOrder(Integer userId, OrderDo matchOrderDo);
}

