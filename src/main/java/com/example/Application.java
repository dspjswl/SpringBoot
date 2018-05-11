package com.example;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.embedded.undertow.UndertowServletWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//@EnableDiscoveryClient
@ImportResource({ "classpath:applicationContext.xml"})
@SpringBootApplication
@MapperScan(basePackages = "com.example.mapper")
@ServletComponentScan
@EnableTransactionManagement
//@EnableDiscoveryClient
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		//下面这条语句也可以启动
		//new SpringApplicationBuilder(Application.class).web(true).run(args);
	}

//    /**
//     * 这是使用Java 8的lambda表达式来简化实现的方式，在代码中创建了三个ErrorPage
//     * 实例来处理三个通用的HTTP错误状态码，并将他们添加到Container当中。ErrorPage类
//     * 是一个封装了错误信息的类，它可以在Jetty和Tomcat环境下使用。下方有java7实现的
//     * 等价方式.
//     * @return
//     */
//	@Bean
//	public EmbeddedServletContainerCustomizer containerCustomizer() {
//
//		return (container -> {
////			ErrorPage error401Page = new ErrorPage(HttpStatus.UNAUTHORIZED, "/401.html");
//            ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/error/404");
//            ErrorPage error415Page = new ErrorPage(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "/error/500");
//            ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error/500");
//
//			container.addErrorPages(/*error401Page,*/ error404Page, error415Page, error500Page);
//		});
//	}

	@Bean
	public ConfigurableServletWebServerFactory webServerFactory() {
		TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
		factory.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/error/404"));
		factory.addErrorPages(new ErrorPage(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "/error/500"));
		factory.addErrorPages(new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error/500"));
		return factory;
	}
	//java 7 实现
//    @Bean
//    public EmbeddedServletContainerCustomizer containerCustomizer() {
//
//        return new EmbeddedServletContainerCustomizer() {
//            @Override
//            public void customize(ConfigurableEmbeddedServletContainer container) {
//
//                ErrorPage error401Page = new ErrorPage(HttpStatus.UNAUTHORIZED, "/401.html");
//                ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/404.html");
//                ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/500.html");
//
//                container.addErrorPages(error401Page, error404Page, error500Page);
//            }
//        };
//    }

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
