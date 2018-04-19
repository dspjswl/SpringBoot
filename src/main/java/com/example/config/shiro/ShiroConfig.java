package com.example.config.shiro;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.example.config.druid.ShiroRedisConfigInfo;
import com.example.config.oauth.OAuth2AuthenticationFilter;
import com.example.config.oauth.OAuth2Realm;
import com.example.dto.FilterRule;
import com.example.mapper.FilterRuleMapper;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Shiro 配置.
 * Apache Shiro 核心通过 Filter 来实现，就好像SpringMvc 通过DispachServlet 来主控制一样。
 既然是使用 Filter 一般也就能猜到，是通过URL规则来进行过滤和权限校验，所以我们需要定义一系列关于URL的规则和访问权限。
 * Created by yuheng.lin
 * Date : 2017/5/13
 */
@Configuration
public class ShiroConfig {
    private static final Logger logger = LoggerFactory.getLogger(ShiroConfig.class);
//    这里注入service的话会导致该service里面的事务无效，所以只要注入mapper即可
//    @Autowired
//    private IUserService userService;

//    @Value("${spring.redis.host}")
//    private String REDIS_HOST;
//
//    @Value("${spring.redis.port}")
//    private int REDIS_PORT;
//
//    @Value("${spring.redis.expire}")
//    private int REDIS_EXPIRE;
    /**
     * shiro缓存管理器;
     * 需要注入对应的其它的实体类中：
     * 1、安全管理器：securityManager
     * 可见securityManager是整个shiro的核心；
     *
     * @return
     */
//    @Bean
//    public EhCacheManager getEhCacheManager() {
//        EhCacheManager em = new EhCacheManager();
//        em.setCacheManagerConfigFile("classpath:ehcache-shiro.xml");
//        return em;
//    }
    @Bean
    public RedisManager getRedisManager(ShiroRedisConfigInfo shiroRedisConfigInfo) {
        RedisManager redisManager = new RedisManager();
        redisManager.setHost(shiroRedisConfigInfo.getHost());
        redisManager.setPort(shiroRedisConfigInfo.getPort());
        redisManager.setExpire(shiroRedisConfigInfo.getExpire());// 配置过期时间
        // redisManager.setTimeout(timeout);
        // redisManager.setPassword(password);
        return redisManager;
    }

    /**
     * cacheManager 缓存 redis实现
     *
     * @return
     */
    @Bean
    public RedisCacheManager getRedisCacheManager(RedisManager redisManager) {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager);
        return redisCacheManager;
    }

    /**
     * RedisSessionDAO shiro sessionDao层的实现 通过redis
     */
    @Bean
    public RedisSessionDAO redisSessionDAO(RedisManager redisManager) {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager);
        return redisSessionDAO;
    }
    /**
     * shiro session的管理
     */
    @Bean
    public DefaultWebSessionManager getSessionManager(RedisSessionDAO redisSessionDAO) {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionDAO(redisSessionDAO);
        return sessionManager;
    }

    @Bean(name = "customShiroRealm")
    public CustomShiroRealm customShiroRealm(RedisCacheManager redisCacheManager) {
        CustomShiroRealm realm = new CustomShiroRealm();
        realm.setCacheManager(redisCacheManager);
        realm.setCredentialsMatcher(hashedCredentialsMatcher());
        return realm;
    }

    @Bean(name = "oAuth2Realm")
    public OAuth2Realm oAuth2Realm(RedisCacheManager redisCacheManager) {
        OAuth2Realm realm = new OAuth2Realm();
        realm.setCacheManager(redisCacheManager);
        //realm.setCredentialsMatcher(hashedCredentialsMatcher());
        return realm;
    }

    @Bean(name = "authenticator")
    public CustomModularRealmAuthenticator getCustomModularRealmAuthenticator() {
        CustomModularRealmAuthenticator authenticator = new CustomModularRealmAuthenticator();
        authenticator.setAuthenticationStrategy(new AtLeastOneSuccessfulStrategy());
        return authenticator;
    }

    @Bean(name = "oAuth2AuthenticationFilter")
    public OAuth2AuthenticationFilter oAuth2AuthenticationFilter() {
        OAuth2AuthenticationFilter filter = new OAuth2AuthenticationFilter();
        filter.setAuthcCodeParam("code");
        filter.setFailureUrl("/oauth2Failure");
        return filter;
    }

    /**
     * 注册DelegatingFilterProxy（Shiro）
     * 集成Shiro有2种方法：
     * 1. 按这个方法自己组装一个FilterRegistrationBean（这种方法更为灵活，可以自己定义UrlPattern，
     * 在项目使用中你可能会因为一些很但疼的问题最后采用它， 想使用它你可能需要看官网或者已经很了解Shiro的处理原理了）
     * 2. 直接使用ShiroFilterFactoryBean（这种方法比较简单，其内部对ShiroFilter做了组装工作，无法自己定义UrlPattern，
     * 默认拦截 /*）
     *
     * @param dispatcherServlet
     * @return
     * @author SHANHY
     * @create  2016年1月13日
     */
//  @Bean
//  public FilterRegistrationBean filterRegistrationBean() {
//      FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
//      filterRegistration.setFilter(new DelegatingFilterProxy("shiroFilter"));
//      //  该值缺省为false,表示生命周期由SpringApplicationContext管理,设置为true则表示由ServletContainer管理
//      filterRegistration.addInitParameter("targetFilterLifecycle", "true");
//      filterRegistration.setEnabled(true);
//      filterRegistration.addUrlPatterns("/*");// 可以自己灵活的定义很多，避免一些根本不需要被Shiro处理的请求被包含进来
//      return filterRegistration;
//  }

    @Bean//(name = "lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator daap = new DefaultAdvisorAutoProxyCreator();
        daap.setProxyTargetClass(true);
        return daap;
    }

    @Bean//(name = "securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(CustomShiroRealm customShiroRealm, OAuth2Realm oAuth2Realm, RedisCacheManager redisCacheManager, SessionManager sessionManager) {
        DefaultWebSecurityManager dwsm = new DefaultWebSecurityManager();
        //设置自定义的realm认证器，要放在设置realm之前才有效果
        dwsm.setAuthenticator(getCustomModularRealmAuthenticator());
        //设置多个realm.
        List<Realm> realms = new ArrayList<>();
        realms.add(customShiroRealm);
        realms.add(oAuth2Realm);
//                dwsm.setRealm(customShiroRealm);
        dwsm.setRealms(realms);
        //注入Redis缓存管理器
        dwsm.setCacheManager(redisCacheManager);
        //注入Session管理器
        dwsm.setSessionManager(sessionManager);
        //注入记住我管理器(暂时没看到有效果)
//        dwsm.setRememberMeManager(rememberMeManager());
        return dwsm;
    }

    /**
     * 开启shiro aop注解支持,启用权限控制.
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor getAuthorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor aasa = new AuthorizationAttributeSourceAdvisor();
        aasa.setSecurityManager(securityManager);
        return aasa;
    }

    /**
     * 加载shiroFilter权限控制规则（从数据库读取然后配置）
     *
     * @author SHANHY
     * @create  2016年1月14日
     */
    public static void loadShiroFilterChain(ShiroFilterFactoryBean shiroFilterFactoryBean, FilterRuleMapper filterRuleMapper){
        /////////////////////// 下面这些规则配置最好配置到配置文件中 ///////////////////////
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        //配置记住我或认证通过可以访问的地址(暂时没看到有效果)
        //        filterChainDefinitionMap.put("/index", "user");
        //        filterChainDefinitionMap.put("/", "user");
        // logout: 配置退出过滤器,其中的具体的退出代码Shiro已经替我们实现了
        // authc：该过滤器下的页面必须验证后才能访问，它是Shiro内置的一个拦截器org.apache.shiro.web.filter.authc.FormAuthenticationFilter
        // anon：它对应的过滤器里面是空的,什么都没做
        logger.info("##################从数据库读取权限规则，加载到shiroFilter中##################");

        List<FilterRule> ruleList = filterRuleMapper.getAllRuleList();
        for (FilterRule filterRule : ruleList) {
            filterChainDefinitionMap.put(filterRule.getUrl(), filterRule.getPermission());
        }

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
    }

    /**
     * 该方法用于解决问题"No SecurityManager accessible to the calling code, either bound to the org.apache.shiro.util.ThreadContext or as a vm static singleton."
     * 解决方案采用<a>http://www.cnblogs.com/ginponson/p/6217057.html</a>
     * @return
     */
    @Bean
    public FilterRegistrationBean delegatingFilterProxy(){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        DelegatingFilterProxy proxy = new DelegatingFilterProxy();
        proxy.setTargetFilterLifecycle(true);
        proxy.setTargetBeanName("shiroFilter");
        filterRegistrationBean.setFilter(proxy);
        return filterRegistrationBean;
    }

    /**
     * ShiroFilter<br/>
     * 注意这里参数中的 StudentService 和 IScoreDao 只是一个例子，因为我们在这里可以用这样的方式获取到相关访问数据库的对象，
     * 然后读取数据库相关配置，配置到 shiroFilterFactoryBean 的访问规则中。实际项目中，请使用自己的Service来处理业务逻辑。
     *
     * @param myShiroRealm
     * @param stuService
     * @param scoreDao
     * @return
     * @author SHANHY
     * @create  2016年1月14日
     */
    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(DefaultWebSecurityManager securityManager, FilterRuleMapper filterRuleMapper) {

        ShiroFilterFactoryBean shiroFilterFactoryBean = new CustomShiroFilterFactoryBean();
        Map<String, Filter> filters = shiroFilterFactoryBean.getFilters();//获取filters
        filters.put("authc", new CustomFormAuthenticationFilter()); //将自定义的CustomFormAuthenticationFilter放入
        filters.put("oauth2Authc", oAuth2AuthenticationFilter());
//        filters.put("logout", getCustomLogoutFilter());
        // 必须设置 SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
        shiroFilterFactoryBean.setLoginUrl("/login");
        // 登录成功后要跳转的连接
        shiroFilterFactoryBean.setSuccessUrl("/user");
        shiroFilterFactoryBean.setUnauthorizedUrl("/error/unauthorized");


        shiroFilterFactoryBean.setFilters(filters);
        loadShiroFilterChain(shiroFilterFactoryBean, filterRuleMapper);

        return shiroFilterFactoryBean;
    }

    /**
     * ShiroDialect，为了在thymeleaf里使用shiro的标签的bean
     * @return
     */
    @Bean
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }

    /**
     * 凭证匹配器
     * （由于我们的密码校验交给Shiro的SimpleAuthenticationInfo进行处理了
     *  所以我们需要修改下doGetAuthenticationInfo中的代码;
     * ）
     * @return
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher(){
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();

        hashedCredentialsMatcher.setHashAlgorithmName("md5");//散列算法:这里使用MD5算法;
        hashedCredentialsMatcher.setHashIterations(1);//散列的次数，比如散列两次，相当于 md5(md5(""));

        return hashedCredentialsMatcher;
    }

//  配置完logout时会死循环
//    @Bean
//    public CustomLogoutFilter getCustomLogoutFilter() {
//        return new CustomLogoutFilter();
//    }
    //暂时没看到有效果
//    /**
//     * cookie对象;
//     * @return
//     */
//    @Bean
//    public SimpleCookie rememberMeCookie(){
//        //这个参数是cookie的名称，对应前端的checkbox的name = rememberMe
//        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
//        //<!-- 记住我cookie生效时间30天 ,单位秒;-->
//        simpleCookie.setMaxAge(259200);
//        return simpleCookie;
//    }
//
//    /**
//     * cookie管理对象;
//     * @return
//     */
//    @Bean
//    public CookieRememberMeManager rememberMeManager(){
//        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
//        cookieRememberMeManager.setCookie(rememberMeCookie());
//        return cookieRememberMeManager;
//    }
}
