package com.board.batch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@MapperScan({"com.board.batch.common.mapper", "com.board.batch.user.mapper"})
public class LogBatchSystemApplication {

	public static void main(String[] args) {

		SpringApplication.run(LogBatchSystemApplication.class, args);

	}

}
