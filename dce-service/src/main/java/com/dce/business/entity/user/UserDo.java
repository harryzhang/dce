package com.dce.business.entity.user;

import java.math.BigDecimal;

import org.hibernate.validator.constraints.NotBlank;

public class UserDo {
    private Integer id;

    @NotBlank(message = "验证编号不能为空")
    private String userName;

    @NotBlank(message = "姓名不能为空")
    private String trueName;

    private String email;

    private String mobile;

    @NotBlank(message = "登录密码不能为空")
    private String userPassword;

    @NotBlank(message = "安全密码不能为空")
    private String twoPassword;

    private Integer userFace;

    private BigDecimal balanceIntegral;

    private String sex;

    private Integer loginTimes;

    private Integer lastLoginTime;

    private String lastLoginIp;

    private String regIp;

    private Integer regTime;

    private Byte status;

    private BigDecimal balanceBonus;

    private BigDecimal balanceRepeat;

    private BigDecimal balanceCash;

    private BigDecimal balanceDongjie;

    private BigDecimal balanceShopping;

    private BigDecimal forzenShopping;

    private BigDecimal totalBonus;

    private BigDecimal totalRepeat;

    private BigDecimal totalCash;

    private BigDecimal totalIntegral;

    private BigDecimal totalShopping;

    @NotBlank(message = "推荐人不能为空")
    private Integer refereeid;

    @NotBlank(message = "接点人不能为空")
    private Integer parentid;

    private String refereeUserName; //推荐人用户名

    private String parentUserName; //接点人用户名

    private Integer refereeNumber;

    private Byte refereeStatus;

    private Byte sonNumber;

    private Byte isServerCenter;

    private Integer myServerCenter;

    private Integer userscore;

    private Byte userType;

    private Byte userGroup;

    private Byte userLevel;

    private Byte userPost;

    private BigDecimal regMoney;

    private BigDecimal yfMoney;

    private BigDecimal fdMoney;

    private BigDecimal totalPerformance;

    private BigDecimal touchedPerformance;

    private Integer activationTime;

    private Integer userQq;

    private String userWechat;

    private String openid;

    private String register;

    private String expressPassword;

    private BigDecimal totalDividends;

    private Integer dividendsDays;

    private Integer country;

    private Integer province;

    private Integer city;

    private Integer darea;

    private BigDecimal balanceEt;

    private String idnumber;

    private Byte banktype;

    private String bankUserName;

    private String banknumber;

    private String bankContent;

    private String question1;

    private String question2;

    private String question3;

    private String answer1;

    private String answer2;

    private String answer3;

    private Integer groupid;

    private String ticket;

    private BigDecimal balanceGouwu;

    private Byte qrcodeStatus;

    private Integer bonusTime;

    private Byte isEmpty;

    private BigDecimal balanceHeart;

    private Integer kuoLevel;

    private Byte backfillStatus;

    private BigDecimal backfillMoney;

    private BigDecimal backfillSheng;

    private Integer guadanNum;

    private BigDecimal allstatic;

    private Integer dis;

    private Integer releaseTime;

    private Byte isshop;

    private Byte isimport;

    private Byte pos;

    private Byte isout;

    private String touchDistance;
    
    private String userLevelName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTrueName() {
        return trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getTwoPassword() {
        return twoPassword;
    }

    public void setTwoPassword(String twoPassword) {
        this.twoPassword = twoPassword;
    }

    public Integer getUserFace() {
        return userFace;
    }

    public void setUserFace(Integer userFace) {
        this.userFace = userFace;
    }

    public BigDecimal getBalanceIntegral() {
        return balanceIntegral;
    }

    public void setBalanceIntegral(BigDecimal balanceIntegral) {
        this.balanceIntegral = balanceIntegral;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getLoginTimes() {
        return loginTimes;
    }

    public void setLoginTimes(Integer loginTimes) {
        this.loginTimes = loginTimes;
    }

    public Integer getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Integer lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    public String getRegIp() {
        return regIp;
    }

    public void setRegIp(String regIp) {
        this.regIp = regIp;
    }

    public Integer getRegTime() {
        return regTime;
    }

    public void setRegTime(Integer regTime) {
        this.regTime = regTime;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public BigDecimal getBalanceBonus() {
        return balanceBonus;
    }

    public void setBalanceBonus(BigDecimal balanceBonus) {
        this.balanceBonus = balanceBonus;
    }

    public BigDecimal getBalanceRepeat() {
        return balanceRepeat;
    }

    public void setBalanceRepeat(BigDecimal balanceRepeat) {
        this.balanceRepeat = balanceRepeat;
    }

    public BigDecimal getBalanceCash() {
        return balanceCash;
    }

    public void setBalanceCash(BigDecimal balanceCash) {
        this.balanceCash = balanceCash;
    }

    public BigDecimal getBalanceDongjie() {
        return balanceDongjie;
    }

    public void setBalanceDongjie(BigDecimal balanceDongjie) {
        this.balanceDongjie = balanceDongjie;
    }

    public BigDecimal getBalanceShopping() {
        return balanceShopping;
    }

    public void setBalanceShopping(BigDecimal balanceShopping) {
        this.balanceShopping = balanceShopping;
    }

    public BigDecimal getForzenShopping() {
        return forzenShopping;
    }

    public void setForzenShopping(BigDecimal forzenShopping) {
        this.forzenShopping = forzenShopping;
    }

    public BigDecimal getTotalBonus() {
        return totalBonus;
    }

    public void setTotalBonus(BigDecimal totalBonus) {
        this.totalBonus = totalBonus;
    }

    public BigDecimal getTotalRepeat() {
        return totalRepeat;
    }

    public void setTotalRepeat(BigDecimal totalRepeat) {
        this.totalRepeat = totalRepeat;
    }

    public BigDecimal getTotalCash() {
        return totalCash;
    }

    public void setTotalCash(BigDecimal totalCash) {
        this.totalCash = totalCash;
    }

    public BigDecimal getTotalIntegral() {
        return totalIntegral;
    }

    public void setTotalIntegral(BigDecimal totalIntegral) {
        this.totalIntegral = totalIntegral;
    }

    public BigDecimal getTotalShopping() {
        return totalShopping;
    }

    public void setTotalShopping(BigDecimal totalShopping) {
        this.totalShopping = totalShopping;
    }

    public Integer getRefereeid() {
        return refereeid;
    }

    public void setRefereeid(Integer refereeid) {
        this.refereeid = refereeid;
    }

    public Integer getParentid() {
        return parentid;
    }

    public void setParentid(Integer parentid) {
        this.parentid = parentid;
    }

    public Integer getRefereeNumber() {
        return refereeNumber;
    }

    public void setRefereeNumber(Integer refereeNumber) {
        this.refereeNumber = refereeNumber;
    }

    public Byte getRefereeStatus() {
        return refereeStatus;
    }

    public void setRefereeStatus(Byte refereeStatus) {
        this.refereeStatus = refereeStatus;
    }

    public Byte getSonNumber() {
        return sonNumber;
    }

    public void setSonNumber(Byte sonNumber) {
        this.sonNumber = sonNumber;
    }

    public Byte getIsServerCenter() {
        return isServerCenter;
    }

    public void setIsServerCenter(Byte isServerCenter) {
        this.isServerCenter = isServerCenter;
    }

    public Integer getMyServerCenter() {
        return myServerCenter;
    }

    public void setMyServerCenter(Integer myServerCenter) {
        this.myServerCenter = myServerCenter;
    }

    public Integer getUserscore() {
        return userscore;
    }

    public void setUserscore(Integer userscore) {
        this.userscore = userscore;
    }

    public Byte getUserType() {
        return userType;
    }

    public void setUserType(Byte userType) {
        this.userType = userType;
    }

    public Byte getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(Byte userGroup) {
        this.userGroup = userGroup;
    }

    public Byte getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(Byte userLevel) {
        this.userLevel = userLevel;
    }

    public Byte getUserPost() {
        return userPost;
    }

    public void setUserPost(Byte userPost) {
        this.userPost = userPost;
    }

    public BigDecimal getRegMoney() {
        return regMoney;
    }

    public void setRegMoney(BigDecimal regMoney) {
        this.regMoney = regMoney;
    }

    public BigDecimal getYfMoney() {
        return yfMoney;
    }

    public void setYfMoney(BigDecimal yfMoney) {
        this.yfMoney = yfMoney;
    }

    public BigDecimal getFdMoney() {
        return fdMoney;
    }

    public void setFdMoney(BigDecimal fdMoney) {
        this.fdMoney = fdMoney;
    }

    public BigDecimal getTotalPerformance() {
        return totalPerformance;
    }

    public void setTotalPerformance(BigDecimal totalPerformance) {
        this.totalPerformance = totalPerformance;
    }

    public Integer getActivationTime() {
        return activationTime;
    }

    public void setActivationTime(Integer activationTime) {
        this.activationTime = activationTime;
    }

    public Integer getUserQq() {
        return userQq;
    }

    public void setUserQq(Integer userQq) {
        this.userQq = userQq;
    }

    public String getUserWechat() {
        return userWechat;
    }

    public void setUserWechat(String userWechat) {
        this.userWechat = userWechat;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getRegister() {
        return register;
    }

    public void setRegister(String register) {
        this.register = register;
    }

    public String getExpressPassword() {
        return expressPassword;
    }

    public void setExpressPassword(String expressPassword) {
        this.expressPassword = expressPassword;
    }

    public BigDecimal getTotalDividends() {
        return totalDividends;
    }

    public void setTotalDividends(BigDecimal totalDividends) {
        this.totalDividends = totalDividends;
    }

    public Integer getDividendsDays() {
        return dividendsDays;
    }

    public void setDividendsDays(Integer dividendsDays) {
        this.dividendsDays = dividendsDays;
    }

    public Integer getCountry() {
        return country;
    }

    public void setCountry(Integer country) {
        this.country = country;
    }

    public Integer getProvince() {
        return province;
    }

    public void setProvince(Integer province) {
        this.province = province;
    }

    public Integer getCity() {
        return city;
    }

    public void setCity(Integer city) {
        this.city = city;
    }

    public Integer getDarea() {
        return darea;
    }

    public void setDarea(Integer darea) {
        this.darea = darea;
    }

    public BigDecimal getBalanceEt() {
        return balanceEt;
    }

    public void setBalanceEt(BigDecimal balanceEt) {
        this.balanceEt = balanceEt;
    }

    public String getIdnumber() {
        return idnumber;
    }

    public void setIdnumber(String idnumber) {
        this.idnumber = idnumber;
    }

    public Byte getBanktype() {
        return banktype;
    }

    public void setBanktype(Byte banktype) {
        this.banktype = banktype;
    }

    public String getBankUserName() {
        return bankUserName;
    }

    public void setBankUserName(String bankUserName) {
        this.bankUserName = bankUserName;
    }

    public String getBanknumber() {
        return banknumber;
    }

    public void setBanknumber(String banknumber) {
        this.banknumber = banknumber;
    }

    public String getBankContent() {
        return bankContent;
    }

    public void setBankContent(String bankContent) {
        this.bankContent = bankContent;
    }

    public String getQuestion1() {
        return question1;
    }

    public void setQuestion1(String question1) {
        this.question1 = question1;
    }

    public String getQuestion2() {
        return question2;
    }

    public void setQuestion2(String question2) {
        this.question2 = question2;
    }

    public String getQuestion3() {
        return question3;
    }

    public void setQuestion3(String question3) {
        this.question3 = question3;
    }

    public String getAnswer1() {
        return answer1;
    }

    public void setAnswer1(String answer1) {
        this.answer1 = answer1;
    }

    public String getAnswer2() {
        return answer2;
    }

    public void setAnswer2(String answer2) {
        this.answer2 = answer2;
    }

    public String getAnswer3() {
        return answer3;
    }

    public void setAnswer3(String answer3) {
        this.answer3 = answer3;
    }

    public Integer getGroupid() {
        return groupid;
    }

    public void setGroupid(Integer groupid) {
        this.groupid = groupid;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public BigDecimal getBalanceGouwu() {
        return balanceGouwu;
    }

    public void setBalanceGouwu(BigDecimal balanceGouwu) {
        this.balanceGouwu = balanceGouwu;
    }

    public Byte getQrcodeStatus() {
        return qrcodeStatus;
    }

    public void setQrcodeStatus(Byte qrcodeStatus) {
        this.qrcodeStatus = qrcodeStatus;
    }

    public Integer getBonusTime() {
        return bonusTime;
    }

    public void setBonusTime(Integer bonusTime) {
        this.bonusTime = bonusTime;
    }

    public Byte getIsEmpty() {
        return isEmpty;
    }

    public void setIsEmpty(Byte isEmpty) {
        this.isEmpty = isEmpty;
    }

    public BigDecimal getBalanceHeart() {
        return balanceHeart;
    }

    public void setBalanceHeart(BigDecimal balanceHeart) {
        this.balanceHeart = balanceHeart;
    }

    public Integer getKuoLevel() {
        return kuoLevel;
    }

    public void setKuoLevel(Integer kuoLevel) {
        this.kuoLevel = kuoLevel;
    }

    public Byte getBackfillStatus() {
        return backfillStatus;
    }

    public void setBackfillStatus(Byte backfillStatus) {
        this.backfillStatus = backfillStatus;
    }

    public BigDecimal getBackfillMoney() {
        return backfillMoney;
    }

    public void setBackfillMoney(BigDecimal backfillMoney) {
        this.backfillMoney = backfillMoney;
    }

    public BigDecimal getBackfillSheng() {
        return backfillSheng;
    }

    public void setBackfillSheng(BigDecimal backfillSheng) {
        this.backfillSheng = backfillSheng;
    }

    public Integer getGuadanNum() {
        return guadanNum;
    }

    public void setGuadanNum(Integer guadanNum) {
        this.guadanNum = guadanNum;
    }

    public BigDecimal getAllstatic() {
        return allstatic;
    }

    public void setAllstatic(BigDecimal allstatic) {
        this.allstatic = allstatic;
    }

    public Integer getDis() {
        return dis;
    }

    public void setDis(Integer dis) {
        this.dis = dis;
    }

    public Integer getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(Integer releaseTime) {
        this.releaseTime = releaseTime;
    }

    public Byte getIsshop() {
        return isshop;
    }

    public void setIsshop(Byte isshop) {
        this.isshop = isshop;
    }

    public Byte getIsimport() {
        return isimport;
    }

    public void setIsimport(Byte isimport) {
        this.isimport = isimport;
    }

    public Byte getPos() {
        return pos;
    }

    public void setPos(Byte pos) {
        this.pos = pos;
    }

    public Byte getIsout() {
        return isout;
    }

    public void setIsout(Byte isout) {
        this.isout = isout;
    }

    public String getTouchDistance() {
        return touchDistance;
    }

    public void setTouchDistance(String touchDistance) {
        this.touchDistance = touchDistance;
    }

    public String getRefereeUserName() {
        return refereeUserName;
    }

    public void setRefereeUserName(String refereeUserName) {
        this.refereeUserName = refereeUserName;
    }

    public String getParentUserName() {
        return parentUserName;
    }

    public void setParentUserName(String parentUserName) {
        this.parentUserName = parentUserName;
    }

    public BigDecimal getTouchedPerformance() {
        return touchedPerformance;
    }

    public void setTouchedPerformance(BigDecimal touchedPerformance) {
        this.touchedPerformance = touchedPerformance;
    }

	public String getUserLevelName() {
		return userLevelName;
	}

	public void setUserLevelName(String userLevelName) {
		this.userLevelName = userLevelName;
	}
    
}