package com.distribute.service;



import com.distribute.message.domain.DbLog;

import java.util.List;

/**
 * Created by Administrator on 2018/8/23.
 */
public interface DbLogService {
	/**
	 * 添加日志
	 * @param dbLog
	 */
	void add(DbLog dbLog);

	/**
	 * 删除日志
	 * @param dbLog
	 */
	void delete(DbLog dbLog);


	/**
	 * 根据消息ID删除日志
	 * @param messageId
	 */
	void deleteByMessageId(String messageId);

	/**
	 * 更新日志
	 * @param dbLog
	 */
	void update(DbLog dbLog);

	/**
	 * 根据id查询日志
	 * @param id
	 * @return
	 */
	DbLog findById(long id);

	/**
	 * 根据消息id查询日志
	 * @param messageId
	 * @return
	 */
	DbLog findByMesageId(String messageId);

	/**
	 * 查询所有log日志
	 * @return
	 */
	List<DbLog> findAll();
}
