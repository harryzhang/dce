package com.dce.business.actions.trade;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dce.business.actions.common.BaseController;
import com.dce.business.common.result.Result;
import com.dce.business.entity.trade.TradeDo;

/** 
 * k线图 MA线 
 * @author parudy
 * @date 2018年3月29日 
 * @version v1.0
 */
@RestController
@RequestMapping("kline")
public class KLineController extends BaseController {
    private final static Logger logger = Logger.getLogger(KLineController.class);

    /** 
     * 
     * @return  
     */
//    @RequestMapping(value = "/records", method = RequestMethod.GET)
//    public Result<?> getTradeRecords() {
//        Integer userId = getUserId();
//        logger.info("查询交易明细, userId:" + userId);
//
//        List<TradeDo> list = tradeService.getTradeRecords(userId);
//
//        return Result.successResult("查询成功", convertTradeInfo(list));
//    }
}
