package com.dce.business.dao.order;

import java.util.Map;

import com.dce.business.entity.order.OrderDo;

public interface IOrderDao {
    int deleteByPrimaryKey(Long orderId);

    int insert(OrderDo record);

    int insertSelective(OrderDo record);

    OrderDo selectByPrimaryKey(Long orderId);

    int updateByPrimaryKeySelective(OrderDo record);

    int updateByPrimaryKey(OrderDo record);
    
    void updateOrderStatusByOldStatus(Map<String, Object> paraMap);
}