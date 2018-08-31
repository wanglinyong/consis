package com.distribute.order.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.distribute.OrderDetail;
import com.distribute.common.enums.ErrorCode;
import com.distribute.order.domain.Orders;
import com.distribute.product.domain.Product;
import com.distribute.service.OrderService;
import com.distribute.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by wly on 2018/8/24.
 */
@Controller
@RequestMapping("/")
public class OrderController {
	@Autowired
	private OrderService orderService;

	@Reference
	private ProductService productService;

	/**
	 * 创建订单
	 * @return
	 */
	@RequestMapping(value = "/createOrder",method = RequestMethod.POST)
	@ResponseBody
	public Map<String,String> createOrder(@RequestBody Map<String,Object> mp){
		Object buyerId = mp.get( "buyerId" );
		Object object = mp.get( "list" );
		String jsonStr = JSONObject.toJSONString( object );
		List<OrderDetail> list = JSONObject.parseArray( jsonStr, OrderDetail.class );
		Map<String,String> map = new HashMap<>(  );
		BigDecimal totalPrice = new BigDecimal( 0 );
		if(list!=null && list.size() > 0){
			for(OrderDetail p : list){
				Product product = productService.findById( p.getId() );
				if(product.getProductSku() < p.getNum()){
					map.put( "reuslt",ErrorCode.LOW_STOCKS.getCode() );
					return map;
				}
				BigDecimal price = product.getProductPrice().multiply(new BigDecimal( p.getNum() ));
				totalPrice = totalPrice.add( price );
				p.setProduct( null );
			}
		}
		Orders order = new Orders();
		order.setBuyerId( Long.parseLong( buyerId.toString() ) );
		order.setCreateTime( new Date(  ) );
		order.setOrderCode( UUID.randomUUID().toString() );
		order.setOrderPrice( totalPrice );
		order.setList( list );
		order.setMessageId( UUID.randomUUID().toString() );
		orderService.add( order );
		map.put( "result",ErrorCode.SUCCESS.getCode() );
		return map;
	}
}
