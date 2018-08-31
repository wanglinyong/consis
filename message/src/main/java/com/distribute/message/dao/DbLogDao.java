package com.distribute.message.dao;

import com.distribute.message.domain.DbLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Administrator on 2018/8/23.
 */
public interface DbLogDao extends JpaRepository<DbLog,Long > {
	DbLog findById(long id);

	DbLog findByMessageId(String messageId);

	void deleteByMessageId(String messageId);
}
