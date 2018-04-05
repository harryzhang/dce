package com.dce.business.service.impl.user;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.dce.business.common.exception.BusinessException;
import com.dce.business.common.result.Result;
import com.dce.business.dao.user.IUserDao;
import com.dce.business.dao.user.IUserParentDao;
import com.dce.business.entity.user.UserDo;
import com.dce.business.entity.user.UserParentDo;
import com.dce.business.service.user.IUserService;

@Service("userService")
public class UserServiceImpl implements IUserService {
    @Resource
    private IUserDao userDao;
    @Resource
    private IUserParentDao userParentDao;

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

        //更新推荐人
        UserDo referee = getUser(userDo.getRefereeid());
        Integer refereeNumber = referee.getRefereeNumber() == null ? 0 : referee.getRefereeNumber();
        referee.setRefereeNumber(refereeNumber + 1);
        userDao.updateByPrimaryKeySelective(referee);
        //更新父节点表
        saveUserParent(userDo.getId());
        return result > 0;
    }

    private void saveUserParent(Integer userId) {
        Integer currentUserId = userId;
        Integer distance = 0;
        while (true) {
            UserDo userDo = userDao.selectByPrimaryKey(currentUserId);

            if (userDo == null || userDo.getParentid() <= 0) {
                break;
            }
            Integer parentId = userDo.getParentid();
            distance++;

            UserParentDo userParentDo = new UserParentDo();
            userParentDo.setParentid(userDo.getParentid());
            userParentDo.setUserid(userDo.getId());
            userParentDo.setDistance(distance);
            userParentDo.setPosition(null); //TODO 这个字段不知道是什么，待确认
            userParentDo.setNetwork(null);//TODO 这个字段不知道是什么，待确认
            userParentDo.setLrDistrict(userDo.getPos());
            userParentDao.insertSelective(userParentDo);

            currentUserId = parentId; //一直往上找
        }
    }

    @Override
    public List<UserDo> list(String userName) {
        Map<String, Object> params = new HashMap<>();
        params.put("userName", userName);
        return userDao.list(params);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void updateStatic(BigDecimal staticAmount, int userId) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("staticAmount", staticAmount);
        userDao.updateStatic(params);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void updateTouched(BigDecimal touchedAmount, int userId) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("touchedAmount", touchedAmount);
        userDao.updateTouched(params);
    }

    @Override
    public List<UserDo> selectUser(Map<String, Object> params) {
        return userDao.selectUser(params);
    }

	@Override
	public Result<?> update(UserDo userDo) {
		if(userDo == null || userDo.getId() == null){
			return Result.failureResult("修改用户信息参数错误!");
		}
		int flag = userDao.updateByPrimaryKeySelective(userDo);
		if(flag > 0){
			return Result.successResult("修改成功");
		}else{
			
			return Result.failureResult("修改失败");
		}
	}
}
