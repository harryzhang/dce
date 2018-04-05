package com.dce.business.service.user;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.dce.business.common.result.Result;
import com.dce.business.entity.user.UserDo;

public interface IUserService {

    /** 
     * 根据用户名查用户
     * @param userName
     * @return  
     */
    UserDo getUser(String userName);

    /**
     * 查询用户 
     * @param userId
     * @return  
     */
    UserDo getUser(Integer userId);

    /**
     * 用户注册 
     * @param userDo
     * @return  
     */
    boolean reg(UserDo userDo);

    /**
     * 模糊查询用户 
     * @param userName
     * @return  
     */
    List<UserDo> list(String userName);

    /** 
     * 更新用户的总释放静态
     * @param staticAmount
     * @param userId  
     */
    void updateStatic(BigDecimal staticAmount, int userId);

    /**
     * 更新量碰数量 
     * @param touchedAmount
     * @param userId  
     */
    void updateTouched(BigDecimal touchedAmount, int userId);

    List<UserDo> selectUser(Map<String, Object> params);
    
    Result<?> update(UserDo userDo);
}
