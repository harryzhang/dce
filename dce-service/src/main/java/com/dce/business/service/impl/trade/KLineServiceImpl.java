package com.dce.business.service.impl.trade;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dce.business.common.enums.KLineTypeEnum;
import com.dce.business.common.redis.JedisClient;
import com.dce.business.common.util.DateUtil;
import com.dce.business.common.util.KLineUtil;
import com.dce.business.common.util.SpringBeanUtil;
import com.dce.business.dao.trade.IKLineDao;
import com.dce.business.entity.trade.KLineDo;
import com.dce.business.entity.trade.MADo;
import com.dce.business.service.trade.IKLineService;

@Service("kLineService")
public class KLineServiceImpl implements IKLineService {
    private final static String KLINE_PREFIX = "kline_";
    private final static int count = 180; //只计算180个周期

    @Resource
    private IKLineDao kLineDao;

    @Override
    public String getKLine(String type) {
        JedisClient jedisClient = SpringBeanUtil.getBean("jedisClient");
        return jedisClient.getString(KLINE_PREFIX + type);
    }

    @Override
    public List<KLineDo> calKLine(KLineTypeEnum kLineType) {
        List<KLineDo> list = new ArrayList<>();

        Date terminationDate = DateUtil.getDate(new Date(), 0, 0, -1000); //最长只计算1000天之前的
        Date endDate = KLineUtil.getStartDate(kLineType);
        while (terminationDate.before(endDate) && (list.size() < count)) {
            KLineDo kLineDo = new KLineDo();
            kLineDo.setDate(DateUtil.dateToString(endDate));

            Date startDate = KLineUtil.getNextDate(kLineType, endDate); //往前推时间

            //查询开始日期到结束日期以内最高价，最低价
            Map<String, Object> params = new HashMap<>();
            params.put("startDate", DateUtil.dateToString(startDate));
            params.put("endDate", DateUtil.dateToString(endDate));
            KLineDo volume = kLineDao.selectQty(params); //查询最高价、最低价、总交易量

            if (volume == null) {
                endDate = startDate;
                continue; //没有交易，不计算
            }

            params.put("open", 1);
            KLineDo open = kLineDao.selectPrice(params); //查询开盘价
            params.remove("open");
            params.put("close", 1);
            KLineDo close = kLineDao.selectPrice(params); //查询收盘价
            params.remove("close");

            kLineDo.setHigh(volume.getHigh());
            kLineDo.setLow(volume.getLow());
            kLineDo.setVolume(volume.getVolume());
            kLineDo.setOpen(open.getPrice());
            kLineDo.setClose(close.getPrice());
            kLineDo.setMa5(getMA(kLineType, endDate, 5));
            kLineDo.setMa10(getMA(kLineType, endDate, 10));
            kLineDo.setMa20(getMA(kLineType, endDate, 20));
            list.add(kLineDo);
            
            endDate = startDate; 
        }

        return list;
    }

    private MADo getMA(KLineTypeEnum kLineType, Date endDate, int maNum) {
        Date startDate = KLineUtil.getMADate(kLineType, endDate, maNum);
        Map<String, Object> params = new HashMap<>();
        params.put("startDate", DateUtil.dateToString(startDate));
        params.put("endDate", DateUtil.dateToString(endDate));
        return kLineDao.selectMA(params);
    }

}
