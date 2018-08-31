package com.distribute.message.task;

import com.alibaba.fastjson.JSONObject;
import com.distribute.common.utils.Constant;
import com.distribute.message.domain.TransactionMessage;
import com.distribute.service.TransactionMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by wly on 2018/8/31.
 */
@Component
public class Task {
	private Logger log = LoggerFactory.getLogger( this.getClass() );
	@Autowired
	private TransactionMessageService transactionMessageService;


	@Value("${retry.maxTimeOut}")
	private Long maxTimeOut;

	/**
	 * 定时重发消息（每分钟）
	 */
	@Scheduled(cron = "0 */1 * * * ?")
	public void	handler(){
		//查询transaction_message表中已发送但未被删除的消息
		List<TransactionMessage> list = transactionMessageService.queryRetryList( Constant.MESSAGE_UNDEAD, maxTimeOut, Constant.MESSAGE_SENDING );
		if(list!=null && list.size() > 0){
			for (TransactionMessage message:list){
				try {
					transactionMessageService.retry( message.getMessageId() );
				} catch (Exception e) {
					log.warn(">>> 消息不存在,可能已经被消费,消息编号:{}", message.getMessageId());
				}
			}
		}
	}

	/**
	 * 定时通知工作人员（每隔5分钟）
	 */
	@Scheduled(cron = "0 */5 * * * ?")
	public void	advance(){
		List<Long> messages = transactionMessageService.queryDeadList();
		log.warn(">>> 共有:{}条消息需要人工处理", messages.size());
		String ids = JSONObject.toJSONString( messages );
		//发邮件或者是发送短信通知工作人员处理

	}
}
