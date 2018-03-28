package com.dce.business.service.impl.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.dce.business.common.exception.BusinessException;
import com.dce.business.dao.user.IUserDao;
import com.dce.business.entity.user.UserDo;
import com.dce.business.service.user.IUserService;

@Service("userService")
public class UserServiceImpl implements IUserService {
    @Resource
    private IUserDao userDao;

    @Override
    public UserDo getUser(String userName) {
        Map<String, Object> params = new HashMap<>();
        params.put("userName", userName);
        List<UserDo> list = userDao.selectUser(params);

        if (CollectionUtils.isEmpty(list)) {
            return null;
        }

        return list.get(0);
    }

    @Override
    public UserDo getUser(Integer userId) {
        return userDao.selectByPrimaryKey(userId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public boolean reg(UserDo userDo) {
        UserDo oldUser = getUser(userDo.getUserName());
        if (oldUser != null) {
            throw new BusinessException("用户已存在");
        }

        //用户注册
        int result = userDao.insertSelective(userDo);
        return result > 0;
    }

    @Override
    public List<UserDo> list(String userName) {
        Map<String, Object> params = new HashMap<>();
        params.put("userName", userName);
        return userDao.list(params);
    }
}
