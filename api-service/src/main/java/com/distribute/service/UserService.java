package com.distribute.service;


import com.distribute.order.domain.User;

/**
 * Created by Administrator on 2018/8/23.
 */
public interface UserService {
	/**
	 * 添加用户
	 * @param user
	 */
	void add(User user);

	/**
	 * 根据id删除用户
	 * @param id
	 */
	void deleteById(long id);

	/**
	 * 更新用户
	 * @param user
	 */
	void update(User user);

	/**
	 * 根据id查询用户
	 * @param id
	 * @return
	 */
	User findById(long id);

	/**
	 * 根据用户名密码查询用户
	 * @param username
	 * @param password
	 * @return
	 */
	User findUsernameAndPassword(String username,String password);

	/**
	 * 根据用户名查询用户
	 * @param username
	 * @return
	 */
	User findByUsername(String username);
}
