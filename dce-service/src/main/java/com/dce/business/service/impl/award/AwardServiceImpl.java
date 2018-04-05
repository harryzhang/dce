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
import com.dce.business.entity.user.UserDo;
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

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void calRecommendAward(Integer userId, BigDecimal amount) {
        UserDo userDo = userService.getUser(userId);

        BigDecimal award = calRecommendAward(userDo, amount);
        UserAccountDo accountDo = new UserAccountDo();
        accountDo.setUserId(userDo.getRefereeid());
        accountDo.setAmount(award);
        accountDo.setAccountType(AccountType.current.getAccountType());
        accountService.updateUserAmountById(accountDo, IncomeType.TYPE_AWARD_REFEREE);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void calLeaderAward(Integer userId, BigDecimal amount) {
        UserDo userDo = userService.getUser(userId);

        Integer refereeId = userDo.getRefereeid(); //推荐人

        //推荐奖
        BigDecimal award = calRecommendAward(userDo, amount);
        //领导奖
        BigDecimal leaderAward = award.multiply(new BigDecimal("0.03")).setScale(4, RoundingMode.HALF_UP);

        UserDo refereeDO = userService.getUser(refereeId);
        Integer parentId = refereeDO.getParentid();
        //计算推荐人的五代领导奖
        for (int i = 0; i < 5; i++) {
            UserDo parentDo = userService.getUser(parentId);
            if (parentDo == null) {
                break;
            }

            UserAccountDo accountDo = new UserAccountDo();
            accountDo.setUserId(parentId);
            accountDo.setAmount(leaderAward);
            accountDo.setAccountType(AccountType.current.getAccountType());
            accountService.updateUserAmountById(accountDo, IncomeType.TYPE_AWARD_LEADER);

            parentId = parentDo.getParentid();
        }
    }

    private BigDecimal calRecommendAward(UserDo userDo, BigDecimal amount) {
        Integer refereeId = userDo.getRefereeid(); //推荐人
        UserDo refereeDo = userService.getUser(refereeId);

        Integer count = refereeDo.getRefereeNumber();//推荐人数
        BigDecimal award = BigDecimal.ZERO;
        if (count == 1) { //第1个5%
            award = amount.multiply(new BigDecimal("0.05")).setScale(4, RoundingMode.HALF_UP);
        } else if (count == 2) { //第2个6%
            award = amount.multiply(new BigDecimal("0.06")).setScale(4, RoundingMode.HALF_UP);
        } else if (count == 3) { //第3个7%
            award = amount.multiply(new BigDecimal("0.07")).setScale(4, RoundingMode.HALF_UP);
        } else if (count == 4) { //第4个8%
            award = amount.multiply(new BigDecimal("0.08")).setScale(4, RoundingMode.HALF_UP);
        } else if (count >= 5) { //第5个或以上10%
            award = amount.multiply(new BigDecimal("0.1")).setScale(4, RoundingMode.HALF_UP);
        }

        return award;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void calTouchAward(Integer userId) {
        recursiveCalTouchAward(userId);
    }

    private void recursiveCalTouchAward(Integer userId) {
        UserDo userDo = userService.getUser(userId);

        //用户的父节点
        UserDo parentDo = userService.getUser(userDo.getParentid());
        if (parentDo == null) { //没有父节点，说明已到顶，递归终止
            return;
        }

        //兄弟节点
        Map<String, Object> params = new HashMap<>();
        params.put("parentid", parentDo.getId());
        List<UserDo> list = userService.selectUser(params);
        UserDo brotherDo = null;
        for (UserDo temp : list) {
            if (!temp.getId().equals(userId)) {
                brotherDo = temp;
                break;
            }
        }
        if (brotherDo == null) {
            return; //没有兄弟接点，递归终止
        }

        //用户总收益
        BigDecimal totalPerformance = userDo.getTotalPerformance();
        BigDecimal brotherTotalPerformance = brotherDo.getTotalPerformance();
        if (totalPerformance == null || brotherTotalPerformance == null) {
            return; //有一边没有收益，不需要量碰，递归终止
        }

        BigDecimal touchedPerformance = userDo.getTouchedPerformance() == null ? BigDecimal.ZERO : userDo.getTouchedPerformance();
        BigDecimal brotherTouchedPerformanc = brotherDo.getTouchedPerformance() == null ? BigDecimal.ZERO : brotherDo.getTouchedPerformance();

        //未量碰收益
        BigDecimal unTouchedPerformance = totalPerformance.compareTo(touchedPerformance) > 0 ? (totalPerformance.subtract(touchedPerformance))
                : BigDecimal.ZERO;
        BigDecimal brotherUnTouchedPerformance = brotherTotalPerformance.compareTo(brotherTouchedPerformanc) > 0
                ? (brotherTotalPerformance.subtract(brotherTouchedPerformanc))
                : BigDecimal.ZERO;
        if (unTouchedPerformance.compareTo(BigDecimal.ZERO) <= 0 || brotherUnTouchedPerformance.compareTo(BigDecimal.ZERO) <= 0) {
            //没有需要量碰的
            return;
        }

        //本次量碰数量
        BigDecimal minTouchedPerformance = unTouchedPerformance.compareTo(brotherUnTouchedPerformance) > 0 ? brotherUnTouchedPerformance
                : unTouchedPerformance;

        //更新用户已量碰数量
        userService.updateTouched(minTouchedPerformance, userDo.getId());
        userService.updateTouched(minTouchedPerformance, brotherDo.getId());
        BigDecimal award = calTouchAward(parentDo, minTouchedPerformance);

        UserAccountDo accountDo = new UserAccountDo();
        accountDo.setUserId(parentDo.getId());
        accountDo.setAmount(award);
        accountDo.setAccountType(AccountType.current.getAccountType());
        accountService.updateUserAmountById(accountDo, IncomeType.TYPE_AWARD_TOUCH);

        //递归计算
        recursiveCalTouchAward(parentDo.getId());
    }

    private BigDecimal calTouchAward(UserDo parentDo, BigDecimal minTouchedPerformance) {
        Integer level = parentDo.getUserLevel().intValue();
        BigDecimal award = BigDecimal.ZERO;
        if (level == 1) {
            award = minTouchedPerformance.multiply(new BigDecimal("0.06")).setScale(4, RoundingMode.HALF_UP);
        } else if (level == 2) {
            award = minTouchedPerformance.multiply(new BigDecimal("0.07")).setScale(4, RoundingMode.HALF_UP);
        } else if (level == 3) {
            award = minTouchedPerformance.multiply(new BigDecimal("0.08")).setScale(4, RoundingMode.HALF_UP);
        } else if (level == 4) {
            award = minTouchedPerformance.multiply(new BigDecimal("0.09")).setScale(4, RoundingMode.HALF_UP);
        } else if (level >= 5) {
            award = minTouchedPerformance.multiply(new BigDecimal("0.1")).setScale(4, RoundingMode.HALF_UP);
        }

        return award;
    }
}
