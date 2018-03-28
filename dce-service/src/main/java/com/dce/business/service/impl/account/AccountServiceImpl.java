package com.dce.business.service.impl.account;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.dce.business.dao.account.IUserAccountDao;
import com.dce.business.entity.account.UserAccountDo;
import com.dce.business.service.account.IAccountService;

/** 
 * 用户资金账户 
 * @author parudy
 * @date 2018年3月25日 
 * @version v1.0
 */
@Service("accountService")
public class AccountServiceImpl implements IAccountService {
    @Resource
    private IUserAccountDao userAccountDao;

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public boolean createAccount(UserAccountDo userAccountDo) {
        userAccountDo.setUpdateTime(new Date());

        int i = userAccountDao.insertSelective(userAccountDo);
        return i > 0;
    }

    @Override
    public UserAccountDo getUserAccount(Integer userId) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);

        List<UserAccountDo> list = userAccountDao.selectAccount(params);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }

        return list.get(0);
    }
    
    //1、原始仓账户；2、美元点账户
}
