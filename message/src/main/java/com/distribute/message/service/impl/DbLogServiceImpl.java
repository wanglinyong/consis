package com.distribute.message.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.distribute.message.dao.DbLogDao;
import com.distribute.message.domain.DbLog;
import com.distribute.service.DbLogService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by wly on 2018/8/23.
 */
@Service
public class DbLogServiceImpl implements DbLogService {
	@Autowired
	private DbLogDao dbLogDao;
	@Override
	public void add(DbLog dbLog) {
		dbLogDao.save( dbLog );
	}

	@Override
	public void delete(DbLog dbLog) {
		dbLogDao.delete( dbLog );
	}

	@Override
	public void deleteByMessageId(String messageId) {
		dbLogDao.deleteByMessageId(messageId);
	}

	@Override
	public void update(DbLog dbLog) {
		dbLogDao.save( dbLog );
	}

	@Override
	public DbLog findById(long id) {
		return dbLogDao.findById( id );
	}

	@Override
	public DbLog findByMesageId(String messageId) {
		return dbLogDao.findByMessageId( messageId );
	}

	@Override
	public List<DbLog> findAll() {
		return dbLogDao.findAll();
	}
}
