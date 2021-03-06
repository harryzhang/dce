package com.dce.business.service.impl.account;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.dce.business.common.enums.AccountType;
import com.dce.business.common.enums.IncomeType;
import com.dce.business.common.exception.BusinessException;
import com.dce.business.common.result.Result;
import com.dce.business.dao.account.IUserAccountDao;
import com.dce.business.dao.account.IUserAccountDetailDao;
import com.dce.business.dao.user.IUserDao;
import com.dce.business.entity.account.UserAccountDetailDo;
import com.dce.business.entity.account.UserAccountDo;
import com.dce.business.entity.user.UserDo;
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
    @Resource
    private IUserAccountDetailDao userAccountDetailDao;
    @Resource
    private IUserDao userDao;
    
    @Value("#{sysconfig['huishang.openAccount.url']}")
    private String ethereum_blance_url;
    
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
    @Override
    public UserAccountDo selectUserAccount(Integer userId, String accountType) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("accountType", accountType);

        List<UserAccountDo> list = userAccountDao.selectAccount(params);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }

        return list.get(0);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int updateUserAmountById(UserAccountDo bizUserAccountDo, IncomeType type) {
        //增加用户收益、消费金额
        BigDecimal amount = bizUserAccountDo.getAmount();
        if (amount.compareTo(BigDecimal.ZERO) >= 0) {
            bizUserAccountDo.setConsumeAmount(BigDecimal.ZERO);
            bizUserAccountDo.setIncomeAmount(amount);
        } else {
            bizUserAccountDo.setIncomeAmount(BigDecimal.ZERO);
            bizUserAccountDo.setConsumeAmount(amount.negate());
        }

        Integer userId = bizUserAccountDo.getUserId();
        String accountType = bizUserAccountDo.getAccountType();
        //判断用户是否存在此帐户 没有刚增加
        UserAccountDo udo = selectUserAccount(userId, accountType);
        int result = 0;
        bizUserAccountDo.setUpdateTime(new Date());
        if (null == udo && amount.compareTo(BigDecimal.ZERO) >= 0) {
            result = userAccountDao.insertSelective(bizUserAccountDo);
        } else {
            result = userAccountDao.updateUserAmountById(bizUserAccountDo);
        }

        if (result < 1) {
            throw new BusinessException("余额不足");
        }

        updateUserAmountDetail(amount, type, userId, accountType, bizUserAccountDo.getRemark());

        return result;
    }

    /**
     * 更新用户明细贪信息
     * 
     * @param amount
     * @param useType
     * @param userId
     * @return
     */
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public boolean updateUserAmountDetail(BigDecimal amount, IncomeType type, Integer userId, String accountType, String remark) {
        // 增加用户消费列表
        UserAccountDetailDo uaDetail = new UserAccountDetailDo();
        uaDetail.setAmount(amount);
        uaDetail.setCreateTime(new Date());
        uaDetail.setIncomeType(type.getIncomeType());
        uaDetail.setMoreOrLess(moreOrLessStr(amount));
        uaDetail.setAccountType(accountType);
        if (StringUtils.isBlank(remark)) {
            uaDetail.setRemark(type.getRemark());
        } else {
            uaDetail.setRemark(remark);
        }
        uaDetail.setUserId(userId);
        userAccountDetailDao.addUserAccountDetail(uaDetail);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void convertBetweenAccount(Integer sourceUserId, Integer targetUserId, BigDecimal amount, String fromAccount, String toAccount,
            IncomeType sourceMsg, IncomeType targetMsg) {

        UserAccountDo source = new UserAccountDo();
        source.setUserId(sourceUserId);
        source.setAmount(amount.negate());
        source.setAccountType(fromAccount);
        this.updateUserAmountById(source, sourceMsg);

        UserAccountDo target = new UserAccountDo();
        target.setUserId(targetUserId);
        target.setAmount(amount);
        target.setAccountType(toAccount);
        this.updateUserAmountById(target, targetMsg);

    }

    private String moreOrLessStr(BigDecimal amount) {
        String type = "+";
        if (BigDecimal.ZERO.compareTo(amount) > 0) {
            type = "-";
        }

        return type;

    }

    @Override
    public List<UserAccountDetailDo> selectUserAccountDetail(Map<String, Object> params) {
        return userAccountDetailDao.selectUserAccountDetail(params);
    }

	@Override
	public Result<?> selectEthereum(Integer userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Result<?> currentInit(Integer userId) {
		if(userId == null){
			return Result.failureResult("用户ID为空!");
		}
		
		Map<String,Object> resultMap = new HashMap<String,Object>();
		
		UserDo user = userDao.selectByPrimaryKey(userId);
		resultMap.put("userName", user.getUserName());
		resultMap.put("userLevelName", StringUtils.isBlank(user.getUserLevelName())?user.getUserLevel():user.getUserLevelName());
		
		UserAccountDo account = selectUserAccount(userId, AccountType.current.name());
		if(account != null){
			
			resultMap.put("amount", account.getAmount());
		}else{
			
			resultMap.put("amount", "0.0");
		}
		
		return Result.successResult("查询成功!", resultMap);
	}
}
