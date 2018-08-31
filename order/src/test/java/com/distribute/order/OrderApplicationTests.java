package com.distribute.order;

import com.distribute.OrderDetail;
import com.distribute.order.domain.Orders;
import com.distribute.service.OrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderApplicationTests {
	@Autowired
	private OrderService orderService;
	@Test
	public void contextLoads() {
		Orders order = orderService.findById( 16l );
		System.out.println(order.getList()+"============");
	}

}
