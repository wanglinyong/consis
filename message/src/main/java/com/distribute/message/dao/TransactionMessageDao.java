package com.distribute.message.dao;

import com.distribute.message.domain.TransactionMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Administrator on 2018/8/23.
 */
public interface TransactionMessageDao extends JpaRepository<TransactionMessage,Long> {
	TransactionMessage findByMessageId(String messageId);
	@Transactional
	void deleteByMessageId(String messageId);

	@Query(value = "select * from transaction_message  where dead_message=:deadMessage and UNIX_TIMESTAMP(now())-UNIX_TIMESTAMP(modify_time)>:maxTimeOut and status=:status",nativeQuery = true)
	List<TransactionMessage> queryRetryList(@Param( "deadMessage" ) String deadMessage,@Param( "maxTimeOut" )Long maxTimeOut,@Param( "status" )String status);

	@Query(value = "select id from transaction_message  where dead_message= 1",nativeQuery = true)
	List<Long> queryDeadList();
}
