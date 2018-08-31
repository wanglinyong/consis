package com.distribute.product;

import com.distribute.product.domain.Product;
import com.distribute.service.ProductService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductApplicationTests {

	@Autowired
	private ProductService productService;

	@Test
	public void contextLoads() {
		Product product = new Product();
		product.setCreateTime( new Date(  ) );
		product.setProductName( "锤子手机" );
		product.setProductSku( 100l );
		product.setProductPrice( new BigDecimal( 998.00 ) );
		product.setProductSpec( "960X760" );
		Product product2 = new Product();
		product2.setCreateTime( new Date(  ) );
		product2.setProductName( "小米手机" );
		product2.setProductSku( 100l );
		product2.setProductPrice( new BigDecimal( 1666.99 ) );
		product2.setProductSpec( "1024X760" );
		productService.add( product );
		productService.add( product2 );
		Long[] arr = {1l,2l};
		List<Product> list = productService.findByIds( arr );
		System.out.println(list.get( 0 ).getProductName()+"==============");
/*
		List<Product> list = productService.findAll();
		System.out.println(list.get( 0 ).getProductName()+"==============");
*/

	}

}
