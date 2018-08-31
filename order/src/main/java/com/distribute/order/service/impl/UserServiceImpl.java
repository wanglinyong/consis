package com.distribute.order.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.distribute.order.dao.UserDao;
import com.distribute.order.domain.User;
import com.distribute.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by wly on 2018/8/23.
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Override
	public void add(User user) {
		userDao.save( user );
	}

	@Override
	public void deleteById(long id) {
		userDao.deleteById( id );
	}

	@Override
	public void update(User user) {
		userDao.save( user );
	}

	@Override
	public User findById(long id) {
		return userDao.findById( id );
	}

	@Override
	public User findUsernameAndPassword(String username, String password) {
		return null;
	}

	@Override
	public User findByUsername(String username) {
		return null;
	}
}
