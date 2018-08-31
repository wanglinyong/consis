package com.distribute.order.dao;


import com.distribute.order.domain.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Administrator on 2018/8/23.
 */
public interface OrderDao extends JpaRepository<Orders,Long> {
	Orders findById(long id);
}
