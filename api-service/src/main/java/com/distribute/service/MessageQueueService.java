package com.distribute.service;

/**
 * 消息队列接口
 * Created by Administrator on 2018/8/22.
 */
public interface MessageQueueService {
	/**
	 * 发送消息到消息队列
	 *
	 * @param queueName   队列名称
	 * @param messageBody 消息内容
	 */
	void sendMessageToMessageQueue(String queueName, String messageBody);
}
