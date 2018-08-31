package com.distribute.product.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.distribute.product.dao.ProductDao;
import com.distribute.product.domain.Product;
import com.distribute.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by wly on 2018/8/23.
 */
@Service
public class ProductServiceImpl implements ProductService {
	@Autowired
	private ProductDao productDao;
	@Override
	public Product findById(long id) {
		return productDao.findById( id );
	}

	@Override
	public void add(Product product) {
		productDao.save( product );
	}

	@Override
	public void delete(long id) {
		productDao.deleteById( id );
	}

	@Override
	public void update(Product product) {
		productDao.save( product );
	}

	@Override
	public List<Product> findByIds(Long[] ids) {
		return productDao.findByIds( ids );
	}

	@Override
	public List<Product> findAll() {
		return productDao.findAll();
	}
}
