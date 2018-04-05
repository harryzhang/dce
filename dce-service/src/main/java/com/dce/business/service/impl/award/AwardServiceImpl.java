package com.dce.business.service.impl.award;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.dce.business.common.enums.AccountType;
import com.dce.business.common.enums.IncomeType;
import com.dce.business.common.util.DateUtil;
import com.dce.business.dao.account.IUserAccountOrderDao;
import com.dce.business.entity.account.UserAccountDo;
import com.dce.business.entity.account.UserAccountOrderDo;
import com.dce.business.service.account.IAccountService;
import com.dce.business.service.award.IAwardService;
import com.dce.business.service.user.IUserService;

@Service("awardService")
public class AwardServiceImpl implements IAwardService {
    private final static Logger logger = Logger.getLogger(AwardServiceImpl.class);

    @Resource
    private IUserAccountOrderDao userAccountOrderDao;
    @Resource
    private IAccountService accountService;
    @Resource
    private IUserService userService;

    private static Map<BigDecimal, BigDecimal> map = new LinkedHashMap<>();
    static {
        map.put(new BigDecimal("300"), new BigDecimal("1.2")); //300以上，比例是1.2
        map.put(new BigDecimal("100"), new BigDecimal("1.1")); //100-300，比例是1.1
        map.put(new BigDecimal("0"), new BigDecimal("1")); //0-100，比例是1
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void calStatic() {
        Date date = DateUtil.getDate(new Date(), -365);
        logger.info("计算静态释放，起始日期：" + DateUtil.dateToString(date));

        //分页查询用户
        Map<String, Object> params = new HashMap<>();
        params.put("startDate", DateUtil.dateToString(date));
        params.put("rows", 1000); //每次查询1000条
        int offset = 0;
        while (true) {
            params.put("offset", offset);
            List<UserAccountOrderDo> list = userAccountOrderDao.select(params);
            if (CollectionUtils.isEmpty(list)) {
                break;
            }
            offset += 1000;

            for (UserAccountOrderDo order : list) {
                BigDecimal staticAward = calStatic(order.getAmount()); //静态奖励

                //写账户
                UserAccountDo accountDo = new UserAccountDo();
                accountDo.setUserId(order.getUserid());
                accountDo.setAmount(staticAward);
                accountDo.setAccountType(AccountType.current.getAccountType());
                accountService.updateUserAmountById(accountDo, IncomeType.TYPE_STATIC);
                userService.updateStatic(staticAward, order.getUserid());
            }
        }

        logger.info("静态释放计算完毕");
    }

    /** 
     * 计算静态变量金额
     * @param amount
     * @return  
     */
    private BigDecimal calStatic(BigDecimal amount) {
        BigDecimal rate = new BigDecimal("1");
        for (Map.Entry<BigDecimal, BigDecimal> entry : map.entrySet()) {
            if (amount.compareTo(entry.getKey()) >= 0) {
                rate = entry.getValue();
            }
        }

        BigDecimal result = amount.divide(new BigDecimal("365"), 6, RoundingMode.HALF_UP).multiply(rate).setScale(4, RoundingMode.HALF_UP);
        return result;
    }

}
