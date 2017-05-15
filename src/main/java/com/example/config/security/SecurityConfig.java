package com.example.config.security;

/**
 * Spring SecurityController Config.
 * Created by yuheng.lin
 * Date : 2017/5/11
 */
//@Configuration
//@EnableWebMvcSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)//开启security注解
public class SecurityConfig
//        extends WebSecurityConfigurerAdapter
{

//    @Autowired
//    CustomUserDetailsService customUserDetailsService;
//
////    @Autowired
////    PersistentTokenRepository tokenRepository;
//
//    @Bean
//    @Override
//    protected AuthenticationManager authenticationManager() throws Exception {
//        return super.authenticationManager();
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//
//        //允许所有用户访问"/"
//        http.authorizeRequests()
//                .antMatchers("/login","/","/lib/**","/images/**").permitAll()
//                //其他地址的访问均需验证权限
//                .anyRequest().authenticated()
//                .and()
//                .formLogin()
//                //指定登录页是"/login"
//                .loginPage("/login")
//                .defaultSuccessUrl("/index")//登录成功后默认跳转到"/index"
//                .permitAll()
//                .and()
//                .logout()
//                .logoutSuccessUrl("/index")//退出登录后的默认url是"/home"
//                .permitAll();
//
//    }
//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//
//        auth
//                .userDetailsService(customUserDetailsService());
////        auth
////                .inMemoryAuthentication()
////                .withUser("user").password("password").roles("USER");
//
//    }
//
//    /**
//     * 设置用户密码的加密方式为MD5加密
//     * @return
//     */
//    @Bean
//    public Md5PasswordEncoder passwordEncoder() {
//        return new Md5PasswordEncoder();
//
//    }
//
//    /**
//     * 自定义UserDetailsService，从数据库中读取用户信息
//     * @return
//     */
//    @Bean
//    public CustomUserDetailsService customUserDetailsService(){
//        return new CustomUserDetailsService();
//    }

//    @Bean
//    public DaoAuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
//        authenticationProvider.setUserDetailsService(customUserDetailsService);
//        authenticationProvider.setPasswordEncoder(passwordEncoder());
//        return authenticationProvider;
//    }
//
//    @Bean
//    public PersistentTokenBasedRememberMeServices getPersistentTokenBasedRememberMeServices() {
//        PersistentTokenBasedRememberMeServices tokenBasedservice = new PersistentTokenBasedRememberMeServices(
//                "remember-me", customUserDetailsService, tokenRepository);
//        return tokenBasedservice;
//    }
//
//    @Bean
//    public AuthenticationTrustResolver getAuthenticationTrustResolver() {
//        return new AuthenticationTrustResolverImpl();
//    }
//
//    @Autowired
//    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(customUserDetailsService);
//        auth.authenticationProvider(authenticationProvider());
//    }
}
