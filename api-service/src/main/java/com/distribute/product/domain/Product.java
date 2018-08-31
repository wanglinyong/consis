package com.distribute.product.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品
 * Created by wly on 2018/8/22.
 */
@Entity
public class Product implements Serializable{

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	private String productName;
	private BigDecimal productPrice;
	private Long productSku;
	private String productSpec;
	private Date createTime;//上架时间
	private String remark;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public BigDecimal getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(BigDecimal productPrice) {
		this.productPrice = productPrice;
	}

	public Long getProductSku() {
		return productSku;
	}

	public void setProductSku(Long productSku) {
		this.productSku = productSku;
	}

	public String getProductSpec() {
		return productSpec;
	}

	public void setProductSpec(String productSpec) {
		this.productSpec = productSpec;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "Product{" +
				"id=" + id +
				", productName='" + productName + '\'' +
				", productPrice=" + productPrice +
				", productSku=" + productSku +
				", productSpec='" + productSpec + '\'' +
				", createTime=" + createTime +
				", remark='" + remark + '\'' +
				'}';
	}
}
