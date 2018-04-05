package com.dce.business.service.impl.message;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.dce.business.dao.IMessageDao;
import com.dce.business.entity.message.MessageAndNewsDo;
import com.dce.business.service.message.IMessageService;

@Service("messageService")
public class MessageServiceImpl implements IMessageService {

	private static Logger logger = Logger.getLogger(MessageServiceImpl.class); 
	
	@Resource
	private IMessageDao messageDao;
	
	@Override
	public List<MessageAndNewsDo> selectMessageByUseId(Integer userId,int pageNum,int rows) {
		if(userId == null){
			logger.error("根据用户ID查询用户消息传入的用户id为空!");
			return null;
		}
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("to_uid", userId);
		int offset = (pageNum - 1) * rows;
		params.put("offset", offset);
		params.put("rows", rows);
		return messageDao.select(params);
	}

	@Override
	public List<MessageAndNewsDo> selectNewsList(int pageNum,int rows) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("type", 4); //消息表中定义类型4为新闻
		int offset = (pageNum - 1) * rows;
		params.put("offset", offset);
		params.put("rows", rows);
		return messageDao.select(params);
	}

	@Override
	public MessageAndNewsDo selectNewsDetail(Integer newsId) {
		return messageDao.selectByPrimaryKey(newsId);
	}

}
