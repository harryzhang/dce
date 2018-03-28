package com.dce.business.dao.account;

import java.util.List;
import java.util.Map;

import com.dce.business.entity.account.UserAccountDo;

public interface IUserAccountDao {
    int deleteByPrimaryKey(Integer id);

    int insert(UserAccountDo record);

    int insertSelective(UserAccountDo record);

    UserAccountDo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserAccountDo record);

    int updateByPrimaryKey(UserAccountDo record);

    List<UserAccountDo> selectAccount(Map<String, Object> params);
}