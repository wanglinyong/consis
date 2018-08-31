package com.distribute.message.service.activemq;

import com.alibaba.dubbo.config.annotation.Service;
import com.distribute.service.MessageQueueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;

/**
 * Created by wly on 2018/8/24.
 */
@Service
public class ActiveMqQueueServiceImpl implements MessageQueueService {
	private final Logger log = LoggerFactory.getLogger( this.getClass() );
	@Autowired
	private JmsMessagingTemplate jmsTemplate;
	@Override
	public void sendMessageToMessageQueue(String queueName,final String messageBody) {

		jmsTemplate.convertAndSend( queueName,messageBody );

		log.info(">>> 发送消息到mq 队列:{},消息内容:{}", queueName, messageBody);
	}
}
