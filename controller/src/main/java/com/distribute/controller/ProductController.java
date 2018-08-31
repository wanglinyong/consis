package com.distribute.controller;

import com.distribute.service.OrderService;
import com.distribute.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by wly on 2018/8/23.
 */
@RestController
@RequestMapping("/")
public class ProductController {
	@Autowired
	private ProductService productService;

	@Autowired
	private OrderService orderService;
	@RequestMapping(value = "/createOrder",method = RequestMethod.POST)
	public String createOrder(@RequestParam(value = "num") Integer num,
							  @RequestParam(value = "ids") String[] ids){
		if(ids!=null && ids.length > 0){

		}

		return "";
	}
}
