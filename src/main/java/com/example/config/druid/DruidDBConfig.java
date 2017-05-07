package com.example.config.druid;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * 配置数据源.
 * Created by yuheng.lin.
 * Date : 2017/5/4
 */
@Configuration
public class DruidDBConfig {

    private Logger logger = LoggerFactory.getLogger(DruidDBConfig.class);

    @Autowired
    DruidConfigInfo druidConfigInfo;

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource druidDataSource() {
        DruidDataSource druidDataSource = new DruidDataSource();
        return druidDataSource;
    }

    /**
     * 注册一个StatViewServlet.
     *
     * @return
     */

    @Bean
    public ServletRegistrationBean DruidStatViewServlet() {
        //org.springframework.boot.context.embedded.ServletRegistrationBean提供类的进行注册.
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");

        //添加初始化参数：initParams
        //白名单：
        servletRegistrationBean.addInitParameter("allow", druidConfigInfo.getAllow());
        //IP黑名单 (存在共同时，deny优先于allow) : 如果满足deny的话提示:Sorry, you are not permitted to view this page.
        servletRegistrationBean.addInitParameter("deny", druidConfigInfo.getDeny());
        //登录查看信息的账号密码.
        servletRegistrationBean.addInitParameter("loginUsername", druidConfigInfo.getLoginUsername());
        servletRegistrationBean.addInitParameter("loginPassword", druidConfigInfo.getLoginPassword());
        //是否能够重置数据.
        servletRegistrationBean.addInitParameter("resetEnable", druidConfigInfo.getResetEnable());
        return servletRegistrationBean;

    }


//    @Bean
//    @ConfigurationProperties(prefix = "druid")
//    public DruidConfigInfo druidConfigInfo(){
//        return new DruidConfigInfo();
//
//    }

}
