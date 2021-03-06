package com.dce.business.dao.goods;

import java.util.List;
import java.util.Map;

import com.dce.business.entity.goods.CTGoodsDo;

public interface ICTGoodsDao {

	List<CTGoodsDo> selectByPage4App(Map<String,Object> params);
	
	CTGoodsDo selectByPrimaryKey(Long goodsId);
	
	int updateByPrimaryKeySelective(CTGoodsDo goods);
}
