package com.example.dto;

/**
 * SecurityUser.
 * Created by yuheng.lin
 * Date : 2017/5/11
 */
public class SecurityUser extends User
       // implements UserDetails
{
//
//    //必须
//    private Collection<? extends GrantedAuthority> authorities;
//    private boolean enabled;
//
//    public SecurityUser(User user) {
//        if (user != null){
//            this.setUsername(user.getUsername());
//            this.setPassword(user.getPassword());
//            this.setRole(user.getRole());
//            this.setId(user.getId());
//            this.setAccess(user.getAccess());
//            this.setEnabled(true);
//        }
//    }
//
//    public SecurityUser(User user,Collection<? extends GrantedAuthority> authorities) {
//        if (user != null){
//            this.setUsername(user.getUsername());
//            this.setPassword(user.getPassword());
//            this.setRole(user.getRole());
//            this.setId(user.getId());
//            this.setAccess(user.getAccess());
//            this.authorities = authorities;
//        }
//    }
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return authorities;
//    }
//
//    @Override
//    public String getPassword() {
//        return this.getPassword();
//    }
//
//    @Override
//    public String getUsername() {
//        return this.getUsername();
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return enabled;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return enabled;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return enabled;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return enabled;
//    }
//
//    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
//        this.authorities = authorities;
//    }
//
//    public void setEnabled(boolean enabled) {
//        this.enabled = enabled;
//    }
}
