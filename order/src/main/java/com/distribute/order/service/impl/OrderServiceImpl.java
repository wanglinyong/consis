package com.distribute.order.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONObject;
import com.distribute.common.utils.Constant;
import com.distribute.order.dao.OrderDao;
import com.distribute.order.domain.Orders;
import com.distribute.product.domain.Product;
import com.distribute.service.OrderService;
import com.distribute.service.ProductService;
import com.distribute.service.TransactionMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by wly on 2018/8/23.
 */
@Service
public class OrderServiceImpl implements OrderService{
	private final Logger log = LoggerFactory.getLogger( this.getClass() );
	@Autowired
	private OrderDao orderDao;

	@Reference
	private TransactionMessageService transactionMessageService;

	@Reference
	private ProductService productService;

	@Override
	@Transactional
	public void add(Orders order) {
		String messageBody = JSONObject.toJSONString( order );
		//添加消息到数据库
		String messageId = transactionMessageService.savePreparMessage(order.getMessageId(), messageBody, Constant.ORDER_QUEUE_NAME );
		log.info(">>> 预发送消息,消息编号:{}", messageId);
		boolean flag = false;
		boolean success = false;
		try{

			Orders orders = orderDao.saveAndFlush( order );
			//int i = 1/0 ;
			log.info(">>> 插入订单,订单编号:{}", orders.getId());
			flag = true;
		}catch (Exception e){
			transactionMessageService.delete( messageId );
			log.info(">>> 业务执行异常删除消息,消息编号:{}", messageId, e);
			throw new RuntimeException( ">>> 创建订单失败" );
		}finally {
			if(flag){
				try {
					transactionMessageService.confirmAndSend( messageId );
					success = true;
					log.info(">>> 确认并且发送消息到实时消息中间件,消息编号:{}", messageId);

				}catch (Exception e){
					log.error(">>> 消息确认异常,消息编号:{}", messageId, e);
					if(!success){
						transactionMessageService.delete( messageId );
						throw new RuntimeException( ">>> 确认消息异常，创建订单失败" );
					}
				}
			}
		}
	}

	@Override
	public void delete(long id) {
		orderDao.deleteById( id );
	}

	@Override
	public void update(Orders order) {
		orderDao.save( order );
	}

	@Override
	public Orders findById(long id) {
		return orderDao.findById( id );
	}

}
