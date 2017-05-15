package com.example.config.security;

/**
 * 自定义user服务.
 * Created by yuheng.lin
 * Date : 2017/5/11
 */
public class CustomUserDetailsService
//        implements UserDetailsService
{
//    @Autowired  //数据库服务类
//    private IUserService userService;
//
//    @Override
//    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
//        //SUser对应数据库中的用户表，是最终存储用户和密码的表，可自定义
//        //本例使用SUser中的email作为用户名:
//        User user = userService.findUserByName(userName);
//
//        if (user == null) {
//
//            throw new UsernameNotFoundException("UserName " + userName + " not found");
//
//        }
//
//        SecurityUser securityUser = new SecurityUser(user);
//        Collection<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
//        authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
//        return securityUser;
//
//    }
//
//    private Collection<GrantedAuthority> getAuthorities(String role) {
//        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
//        SimpleGrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role);
//        grantedAuthorities.add(grantedAuthority);
//        return grantedAuthorities;
//    }
}
