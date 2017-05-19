package com.example;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//@EnableDiscoveryClient
@ImportResource({ "classpath:applicationContext.xml"})
@SpringBootApplication
@MapperScan(basePackages = "com.example.mapper")
@ServletComponentScan
@EnableTransactionManagement
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

//	public @PostConstruct
//	void init() {
//       System.out.println("=====执行初始化动作========");
//	}
//
//
//    public @PreDestroy
//    void destory() {
//       System.out.println("=====执行销毁动作========");
//    }
}
