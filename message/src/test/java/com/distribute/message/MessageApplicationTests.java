package com.distribute.message;

import com.distribute.message.domain.TransactionMessage;
import com.distribute.service.TransactionMessageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MessageApplicationTests {
	@Autowired
	private TransactionMessageService transactionMessageService;
	@Test
	public void contextLoads() {
		List<TransactionMessage> list = transactionMessageService.queryRetryList();
		System.out.println(list.size()+"===========================");
	}

}
