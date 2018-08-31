package com.distribute.product.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.distribute.OrderDetail;
import com.distribute.common.utils.Constant;
import com.distribute.message.domain.DbLog;
import com.distribute.message.domain.TransactionMessage;
import com.distribute.order.domain.Orders;
import com.distribute.product.domain.Product;
import com.distribute.service.DbLogService;
import com.distribute.service.ProductService;
import com.distribute.service.TransactionMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by wly on 2018/8/25.
 */
@Component
public class ProductConsumerListener{
	@Reference
	private TransactionMessageService transactionMessageService;
	@Reference
	private DbLogService dbLogService;
	@Autowired
	private ProductService productService;

	private final Logger log = LoggerFactory.getLogger( this.getClass() );
	@Transactional
	@JmsListener( destination = Constant.ORDER_QUEUE_NAME)
	public void	receiveQueue(String msg){
		boolean flag = false;
		Orders orders = JSONObject.parseObject( msg, Orders.class );
		log.info(">>> 接收到mq消息队列，消息编号:{} ,消息内容:{}", orders.getMessageId(), msg);

		TransactionMessage transactionMessage = transactionMessageService.findByMessageId( orders.getMessageId() );
		try {
			//保证幂等性
			if(transactionMessage!=null){
				List<OrderDetail> list = orders.getList();
				for(OrderDetail detail : list){
					Product product = productService.findById( detail.getId() );
					Long skuNum = product.getProductSku() - detail.getNum();
					if(skuNum >= 0){
						product.setProductSku( skuNum );
						productService.update( product );
					}else {
						throw new Exception( ">>> 库存不足,修改库存失败！" );
					}

				}
				//int i = 1 /0 ;
				flag = true;
			}

		}catch (Exception e){
			e.printStackTrace();
			throw new RuntimeException( e );
		}finally {
			if(flag){
				transactionMessageService.delete( orders.getMessageId() );
				DbLog dbLog = dbLogService.findByMesageId( orders.getMessageId() );
				if(dbLog!=null){
					dbLog.setState( "1" );//已处理成功
					dbLogService.update( dbLog );
				}
				log.info(">>> 业务执行成功删除消息! messageId:{}", orders.getMessageId());
			}
		}

	}
}
