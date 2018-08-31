package com.distribute;

import com.distribute.product.domain.Product;

import java.io.Serializable;

/**
 * Created by wly on 2018/8/24.
 */
public class OrderDetail implements Serializable{
	private Long id;
	private Integer num;
	private Product product;

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}
}
