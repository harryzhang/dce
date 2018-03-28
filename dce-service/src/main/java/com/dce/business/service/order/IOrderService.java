package com.dce.business.service.order;

import java.util.List;
import java.util.Map;

import com.dce.business.entity.order.OrderDo;

public interface IOrderService {

    /** 
     * 添加订单
     * @param orderDo  
     */
    Long addOrder(OrderDo orderDo);

    OrderDo getOrderDo(Long orderId);

    void matchOrder(Integer userId, OrderDo matchOrderDo);

    List<OrderDo> selectOrder(Map<String, Object> params);
}
