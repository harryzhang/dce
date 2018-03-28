package com.dce.business.dao.user;

import com.dce.business.entity.user.UserRefereeDo;

public interface IUserRefereeDao {
    int deleteByPrimaryKey(Integer id);

    int insert(UserRefereeDo record);

    int insertSelective(UserRefereeDo record);

    UserRefereeDo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserRefereeDo record);

    int updateByPrimaryKey(UserRefereeDo record);
}