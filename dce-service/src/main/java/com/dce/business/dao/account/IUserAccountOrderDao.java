package com.dce.business.dao.account;

import java.util.List;
import java.util.Map;

import com.dce.business.entity.account.UserAccountOrderDo;

public interface IUserAccountOrderDao {
    int deleteByPrimaryKey(Long id);

    int insert(UserAccountOrderDo record);

    int insertSelective(UserAccountOrderDo record);

    UserAccountOrderDo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserAccountOrderDo record);

    int updateByPrimaryKey(UserAccountOrderDo record);
    
    List<UserAccountOrderDo> select(Map<String, Object> params);
}