package com.board.batch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("com.board.batch.mapper")
public class LogBatchSystemApplication {

	public static void main(String[] args) {

		SpringApplication.run(LogBatchSystemApplication.class, args);

	}

}
