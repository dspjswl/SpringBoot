package com.example;

import com.example.service.IRedisService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ImportResource;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

//@EnableDiscoveryClient
@ImportResource({ "classpath:applicationContext.xml"})
@SpringBootApplication
@MapperScan(basePackages = "com.example.mapper")
@ServletComponentScan
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
