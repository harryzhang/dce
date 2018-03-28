package com.dce.business.dao.user;

import com.dce.business.entity.user.UserParentDo;

public interface IUserParentDao {
    int deleteByPrimaryKey(Integer id);

    int insert(UserParentDo record);

    int insertSelective(UserParentDo record);

    UserParentDo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserParentDo record);

    int updateByPrimaryKey(UserParentDo record);
}