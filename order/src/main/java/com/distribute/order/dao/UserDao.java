package com.distribute.order.dao;

import com.distribute.order.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Administrator on 2018/8/23.
 */
public interface UserDao extends JpaRepository<User,Long> {
	User findById(long id);
}
