package com.distribute.service;


import com.distribute.order.domain.Orders;

/**
 * Created by Administrator on 2018/8/22.
 */
public interface OrderService {
	/**
	 * 添加订单
	 * @param order
	 */
	void add(Orders order);

	/**
	 * 删除订单
	 * @param id
	 */
	void delete(long id);

	/**
	 * 更新订单
	 * @param order
	 */
	void update(Orders order);

	/**
	 * 根据id查询订单
	 * @param id
	 * @return
	 */
	Orders findById(long id);
}
