package com.zltel;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * APP入口 BroadcastApplication class
 *
 * @author Touss
 * @date 2018/5/2
 */
@SpringBootApplication
@MapperScan("com.zltel.broadcast.*.dao")
@ServletComponentScan
@EnableScheduling // 启用定时器
@EnableSwagger2 // API文档
public class BroadcastApplication {

	public static void main(String[] args) {
		SpringApplication.run(BroadcastApplication.class, args);
	}
}
