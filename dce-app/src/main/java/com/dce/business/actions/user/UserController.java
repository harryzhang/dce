package com.dce.business.actions.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.dce.business.actions.common.BaseController;
import com.dce.business.common.result.Result;
import com.dce.business.common.token.TokenUtil;
import com.dce.business.entity.account.UserAccountDo;
import com.dce.business.entity.user.UserDo;
import com.dce.business.service.account.IAccountService;
import com.dce.business.service.award.IAwardService;
import com.dce.business.service.user.IUserService;

/** 
 * 账户处理器，注册、登录等
 * @author parudy
 * @date 2018年3月24日 
 * @version v1.0
 */
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {
    private final static Logger logger = Logger.getLogger(UserController.class);

    @Resource
    private IUserService userService;
    @Resource
    private IAccountService accountService;
    @Resource
    private IAwardService awardService;

    /** 
     * 用户注册
     * @param userDo
     * @param bindingResult
     * @return  
     */
    @RequestMapping(value = "/reg", method = RequestMethod.POST)
    public Result<?> reg(@Valid UserDo userDo, BindingResult bindingResult) {
        logger.info("用户注册：" + JSON.toJSONString(userDo));

        //参数校验
        if (bindingResult.hasErrors()) {
            List<ObjectError> errors = bindingResult.getAllErrors();
            logger.info("用户注册，参数校验错误：" + JSON.toJSONString(errors));
            return Result.failureResult(errors.get(0).getDefaultMessage());
        }

        boolean isSuccess = userService.reg(userDo);
        if (isSuccess) {
            return Result.successResult("注册成功");
        } else {
            return Result.failureResult("注册失败");
        }
    }

    /**
     * 登录 
     * @param request
     * @param response
     * @return  
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Result<?> login() {
        //String mobile = getString("mobile");
        String userName = getString("userName");
        String password = getString("password");
        logger.info("用户登录, userName:" + userName + "; password:" + password);

        Assert.hasText(userName, "请输入用户名");
        Assert.hasText(password, "请输入密码");

        UserDo userDo = userService.getUser(userName);
        Assert.notNull(userDo, "用户不存在");

        if (!userName.equals(userDo.getUserName()) || !password.equals(userDo.getUserPassword())) {
            return Result.failureResult("用户名或者密码不正确");
        }

        String token = TokenUtil.createToken(userDo.getId());
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        map.put("userId", userDo.getId());
        return Result.successResult("登录成功", map);
    }

    /** 
     * 模糊搜索用户列表
     * @return  
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public Result<?> list() {
        String userName = getString("userName");
        String type = getString("type"); //查询类型，type=1表示推荐人、type=2表示接点人
        logger.info("模糊查询, userName:" + userName + "; type:" + type);

        Assert.hasText(userName, "请输入用户名");

        List<UserDo> list = userService.list(userName);

        return Result.successResult("查询成功", list);
    }

    /** 
     * 首页，查询用户基本信息
     * @return  
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Result<?> getUserInfo() {
        Integer userId = getUserId();

        logger.info("查询用户基本信息，userId:" + userId);

        //用户信息
        UserDo userDo = userService.getUser(userId);
        UserDo newUserDo = new UserDo();
        newUserDo.setId(userDo.getId());
        newUserDo.setUserName(userDo.getUserName());
        newUserDo.setTrueName(userDo.getTrueName());
        newUserDo.setUserLevel(userDo.getUserLevel());
        newUserDo.setReleaseTime(userDo.getReleaseTime()); //释放时间
        newUserDo.setUserFace(userDo.getUserFace());

        //推荐人
        if (userDo.getRefereeid() != null) {
            UserDo referee = userService.getUser(userDo.getRefereeid());
            if (referee != null) {
                newUserDo.setRefereeUserName(referee.getUserName());
            }
        }

        //接点人
        if (userDo.getParentid() != null) {
            UserDo parent = userService.getUser(userDo.getParentid());
            if (parent != null) {
                newUserDo.setParentUserName(parent.getUserName());
            }
        }

        //财务信息
        UserAccountDo userAccountDo = accountService.getUserAccount(userId);
        UserAccountDo newUserAccountDo = new UserAccountDo();
        if (userAccountDo != null) {
            newUserAccountDo.setAmount(userAccountDo.getAmount());
            newUserAccountDo.setOriginalAmount(userAccountDo.getOriginalAmount());
            newUserAccountDo.setFrozenDeposit(userAccountDo.getFrozenDeposit());
            newUserAccountDo.setPointAmount(userAccountDo.getPointAmount());
            newUserAccountDo.setScoreAmount(userAccountDo.getScoreAmount());
        }

        Map<String, Object> map = new HashMap<>();
        map.put("userInfo", newUserDo);
        map.put("userAccountDo", newUserAccountDo);
        return Result.successResult("查询成功", map);
    }

    /** 
     * 个人中心查询个人信息
     * @return  
     */
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public Result<?> getUser() {
        Integer userId = getUserId();

        logger.info("查询用户基本信息，userId:" + userId);

        //用户信息
        UserDo userDo = userService.getUser(userId);

        Map<String, Object> map = new HashMap<>();
        map.put("trueName", userDo.getTrueName()); //姓名
        map.put("mobile", userDo.getMobile()); //手机号码
        map.put("email", userDo.getEmail());
        map.put("idnumber", userDo.getIdnumber());
        map.put("banktype", userDo.getBanktype());
        map.put("bankUserName", userDo.getBankUserName());
        map.put("banknumber", userDo.getBanknumber());
        map.put("bankContent", userDo.getBankContent());
        return Result.successResult("查询成功", map);
    }

    /** 
     * 修改用户信息
     * @return  
     */
    @RequestMapping(value = "/info", method = RequestMethod.POST)
    public Result<?> updateUser() {
        Integer userId = getUserId();
        String trueName = getString("trueName");
        String mobile = getString("mobile");
        String email = getString("email");
        String idnumber = getString("idnumber");
        String banktype = getString("banktype");
        String bankUserName = getString("bankUserName");
        String banknumber = getString("banknumber");
        String bankContent = getString("bankContent");
        logger.info("修改用户信息，userId:" + userId);

        Assert.hasText(trueName, "姓名不能为空");
        Assert.hasText(mobile, "手机号码不能为空");
        Assert.hasText(email, "邮箱不能为空");
        Assert.hasText(idnumber, "身份证不能为空");
        Assert.hasText(banktype, "银行不能为空");
        Assert.hasText(bankUserName, "开户名不能为空");
        Assert.hasText(banknumber, "银行卡号不能为空");
        Assert.hasText(bankContent, "支行不能为空");
        
        //用户信息
        UserDo userDo = new UserDo();
        userDo.setId(userId);
        userDo.setTrueName(trueName);
        userDo.setMobile(mobile);
        userDo.setEmail(email);
        userDo.setIdnumber(idnumber);
        userDo.setBanktype(Byte.parseByte(banktype));
        userDo.setBankUserName(bankUserName);
        userDo.setBanknumber(banknumber);
        userDo.setBankContent(bankContent);
        
        return userService.update(userDo);
    }

    /**   
     * 每天0点计算
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void calKLine() {
        awardService.calStatic();
    }
}
