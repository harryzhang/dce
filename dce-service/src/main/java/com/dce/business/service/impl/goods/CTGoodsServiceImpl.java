package com.dce.business.service.impl.goods;

import java.math.RoundingMode;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.dce.business.common.result.Result;
import com.dce.business.common.util.OrderCodeUtil;
import com.dce.business.dao.goods.ICTGoodsDao;
import com.dce.business.dao.goods.ICTUserAddressDao;
import com.dce.business.dao.order.IOrderDao;
import com.dce.business.entity.goods.CTGoodsDo;
import com.dce.business.entity.goods.CTUserAddressDo;
import com.dce.business.entity.order.OrderDo;
import com.dce.business.service.goods.ICTGoodsService;

@Service("ctGoodsService")
public class CTGoodsServiceImpl implements ICTGoodsService {
	
	private final static Logger logger = Logger.getLogger(CTGoodsServiceImpl.class);
	
	@Resource
    private ICTGoodsDao ctGoodsDao;
	@Resource
	private ICTUserAddressDao ctUserAddressDao;
	@Resource
    private IOrderDao orderDao;
	
	@Override
	public List<CTGoodsDo> selectByPage(int pageNum,int pageCount) {
		Map<String,Object> params = new HashMap<String,Object>();
		pageNum = pageNum > 0?pageNum - 1:pageNum;
		int offset = pageNum * pageCount;
		int rows = pageCount;
		params.put("offset", offset);
		params.put("rows", rows);
		return null;
	}

	@Override
	public CTGoodsDo selectById(Long id) {
		return ctGoodsDao.selectByPrimaryKey(id);
	}

	@Override
	public Result<?> buyGoods(OrderDo order,Long addressId) {
		if(order.getGoodsId() == null || order.getQty().intValue() <= 0){
			
			logger.error("购买商品参数错误:goodsId=" + order.getGoodsId() + ",qty=" + order.getQty());
			return Result.failureResult("购买商品参数错误");
		}
		
		CTGoodsDo goods = selectById(order.getGoodsId());
		//判断购买数量是否大于库存量
//		if(order.getQty().intValue() > goods.getGoodsStock()){
//			return Result.failureResult("购买商品数量大于库存量");
//		}
		
		//如果没传收货地址,则查询默认且有效的收货地址
		if(addressId == null){
			
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("userId", order.getUserId());
			params.put("isDefault", 1);
			params.put("addressFlag", 1);
			
			List<CTUserAddressDo> addressList = ctUserAddressDao.select(params);
			if(!CollectionUtils.isEmpty(addressList)){
				order.setRecAddress(addressList.get(0).getAddress());
			}
		}else{
			CTUserAddressDo address = ctUserAddressDao.selectByPrimaryKey(addressId);
			order.setRecAddress(address.getAddress());
		}
		
		order.setCreateTime(new Date());
		order.setOrderCode(OrderCodeUtil.genOrderCode(order.getUserId()));
		order.setOrderStatus(1);
		order.setOrderType(1);
		order.setPayStatus(0);
		order.setPrice(goods.getShopPrice());
		order.setTotalPrice(order.getQty().multiply(order.getPrice()).setScale(4, RoundingMode.HALF_UP));
		
		int flag = orderDao.insertSelective(order);
		
		if(flag > 0){
			goods.setGoodsStock(goods.getGoodsStock() - order.getQty().longValue());
			//修改库存
//			ctGoodsDao.updateByPrimaryKeySelective(goods);
			return Result.successResult("购买商品成功");
		}else{
			return Result.failureResult("购买商品失败");
		}
	}

}
