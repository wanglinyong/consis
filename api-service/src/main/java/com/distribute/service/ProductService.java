package com.distribute.service;


import com.distribute.product.domain.Product;

import java.util.List;

/**
 * Created by Administrator on 2018/8/22.
 */
public interface ProductService {
	/**
	 * 根据id查询商品
	 * @param id
	 * @return
	 */
	Product findById(long id);

	/**
	 * 添加商品
	 * @param product
	 */
	void add(Product product);

	/**
	 * 根据id删除商品
	 * @param id
	 */
	void delete(long id);

	/**
	 * 更新商品
	 * @param product
	 */
	void update(Product product);

	/**
	 * 根据ids查询商品集合
	 * @param ids
	 * @return
	 */
	List<Product> findByIds(Long[] ids);

	/**
	 * 查询所有商品
	 * @return
	 */
	List<Product> findAll();

}
