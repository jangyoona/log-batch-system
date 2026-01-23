package com.log.batch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@MapperScan({"com.log.batch.user.mapper", "com.log.batch.post.mapper", "com.log.batch.actionlog.mapper"})
public class LogBatchSystemApplication {

	public static void main(String[] args) {

		SpringApplication.run(LogBatchSystemApplication.class, args);

	}

}
