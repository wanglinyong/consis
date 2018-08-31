package com.distribute.message.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.distribute.common.enums.MessageStatus;
import com.distribute.common.utils.Constant;
import com.distribute.message.dao.DbLogDao;
import com.distribute.message.dao.TransactionMessageDao;
import com.distribute.message.domain.DbLog;
import com.distribute.message.domain.TransactionMessage;
import com.distribute.service.MessageQueueService;
import com.distribute.service.TransactionMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;
import java.util.List;

/**
 * Created by wly on 2018/8/23.
 */
@Service
public class TransactionMessageServiceImpl implements TransactionMessageService {
	private static final Logger log = LoggerFactory.getLogger( TransactionMessageServiceImpl.class );
	@Autowired
	private TransactionMessageDao transactionMessageDao;

	@Autowired
	private MessageQueueService messageQueueService;

	@Autowired
	private DbLogDao dbLogDao;

	@Value("${retry.maxsendtimes}")
	private Integer maxSendTimes;

	@Override
	public String savePreparMessage(String messageId,String messageBody, String consumerQueue) {
		TransactionMessage transactionMessage = new TransactionMessage();
		transactionMessage.setCreateTime( new Date(  ) );
		transactionMessage.setMessageBody( messageBody );
		transactionMessage.setQueueName( consumerQueue );
		transactionMessage.setStatus( MessageStatus.WAITING_CONFIRM.getKey() );
		transactionMessage.setMessageId( messageId );
		transactionMessageDao.save( transactionMessage );
		return messageId;
	}

	@Override
	public void confirmAndSend(String messageId) throws Exception {
		//int i = 1/0;
		TransactionMessage transactionMessage = messageExist( messageId );
		if(!MessageStatus.WAITING_CONFIRM.getKey().equals( transactionMessage.getStatus() )){
			return;
		}
		transactionMessage.setStatus( MessageStatus.SENDING.getKey() );
		transactionMessage.setModifyTime( new Date(  ) );
		transactionMessageDao.save( transactionMessage );
		messageQueueService.sendMessageToMessageQueue( transactionMessage.getQueueName(),transactionMessage.getMessageBody() );
		log.info(">>> 确认消息并且发送到消息中间件! 消息编号:{},队列名称:{}", messageId, transactionMessage.getQueueName());

	}

	@Override
	public void directSendMessage(String messageBody, String consumerQueue) {
		messageQueueService.sendMessageToMessageQueue( consumerQueue,messageBody );
		log.info(">>> 直接发送到消息中间件! 队列名称:{}", consumerQueue);
	}

	@Override
	public void retry(String messageId) throws Exception {
		TransactionMessage message = messageExist( messageId );
		reSendMessage(message);
	}

	@Override
	public void delete(String messageId) {
		transactionMessageDao.deleteByMessageId(messageId);
	}

	@Override
	public List<TransactionMessage> queryRetryList() {
		return transactionMessageDao.findAll( );
	}

	@Override
	public List<Long> queryDeadList() {
		return transactionMessageDao.queryDeadList();
	}

	@Override
	public TransactionMessage findByMessageId(String messageId) {
		return transactionMessageDao.findByMessageId( messageId );
	}

	/**
	 * 查询已发送、超时未删除且未死亡的消息
	 * @param timeout
	 * @return
	 */
	@Override
	public List<TransactionMessage> queryRetryList(String unDead,Long timeout,String sending) {
		return transactionMessageDao.queryRetryList( unDead,timeout,sending );
	}

	@Override
	public List<TransactionMessage> queryConfirmList(Long timeout, Integer maxconfirmtimes) {
		return null;
	}

	@Override
	public Integer update(TransactionMessage message) {
		 transactionMessageDao.save( message );
		 return 1;
	}

	/**
	 * 验证messageId是否存在
	 *
	 * @param messageId
	 */
	private TransactionMessage messageExist(String messageId) throws Exception {
		TransactionMessage message = transactionMessageDao.findByMessageId(messageId);
		if (message == null) {
			throw new Exception(String.format("消息编号%s不存在", messageId));
		}
		return message;
	}

	/**
	 * 重新发送消息
	 * @param message
	 */
	private void reSendMessage(TransactionMessage message) {
		message.setSendTimes( message.getSendTimes() + 1 );
		if(message.getSendTimes() >= maxSendTimes){
			//验证次数大于等于最大尝试次数
			message.setDeadMessage( "1" );
			transactionMessageDao.save( message );
			DbLog dbLog = new DbLog();
			dbLog.setCreateTime( new Date(  ) );
			dbLog.setMessageId( message.getMessageId() );
			dbLog.setState( "0" );//等待人工处理
			dbLogDao.save( dbLog );
			log.info(">>> 消息超出最大重试次数标识死亡!消息编号:{},重试次数:{}", message.getMessageId(), message.getSendTimes());
		}else {
			transactionMessageDao.save( message );
			messageQueueService.sendMessageToMessageQueue( message.getQueueName(),message.getMessageBody() );
			log.info(">>> 重新发送消息! 消息编号:{}", message.getMessageId());
		}



	}
}
