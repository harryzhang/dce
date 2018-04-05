package com.dce.business.service.message;

import java.util.List;

import com.dce.business.entity.message.MessageAndNewsDo;

public interface IMessageService {

	public List<MessageAndNewsDo> selectMessageByUseId(Integer userId,int pageNum,int rows);
	
	public List<MessageAndNewsDo> selectNewsList(int pageNum,int rows);
	
	public MessageAndNewsDo selectNewsDetail(Integer newsId);
}
