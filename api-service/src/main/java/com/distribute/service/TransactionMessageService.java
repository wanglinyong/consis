package com.distribute.service;



import com.distribute.message.domain.TransactionMessage;

import java.util.List;

/**
 * Created by Administrator on 2018/8/22.
 */
public interface TransactionMessageService {
	/**
	 * 预存储消息
	 * @param messageId  消息编号
	 * @param messageBody   消息内容
	 * @param consumerQueue 队列名称
	 * @return 消息编号
	 */
	String savePreparMessage(String messageId,String messageBody, String consumerQueue);

	/**
	 * 确认并发送消息
	 *
	 * @param messageId 消息编号
	 */
	void confirmAndSend(String messageId) throws Exception;

	/**
	 * 直接发送消息
	 *
	 * @param messageBody   消息内容
	 * @param consumerQueue 队列名称
	 */
	void directSendMessage(String messageBody, String consumerQueue);

	/**
	 * 根据messageId重发某条消息
	 *
	 * @param messageId 消息编号
	 */
	void retry(String messageId) throws Exception;

	/**
	 * 根据消息ID删除消息
	 *
	 * @param messageId 消息编号
	 */
	void delete(String messageId);

	/**
	 * 查询需要重试的消息列表
	 *
	 * @return
	 */
	List<TransactionMessage> queryRetryList();

	/**
	 * 查询死亡消息（需人工处理的消息）
	 * @return
	 */
	List<Long> queryDeadList();

	/**
	 * 根据消息编号查询消息
	 * @param messageId
	 * @return
	 */
	TransactionMessage findByMessageId(String messageId);

	/**
	 * 查询需要重试的消息列表
	 *
	 * @return
	 */
	List<TransactionMessage> queryRetryList(String unDead,Long timeout,String sending);

	List<TransactionMessage> queryConfirmList(Long timeout, Integer maxconfirmtimes);

	Integer update(TransactionMessage message);
}
