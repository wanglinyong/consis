package com.distribute.product.dao;

import com.distribute.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Administrator on 2018/8/23.
 */
public interface ProductDao extends JpaRepository<Product,Long> {
	Product findById(long id);

	@Query(value = "select * from product  where id in (:ids)",nativeQuery = true)
	List<Product> findByIds(@Param( value = "ids") Long[] ids);
}
