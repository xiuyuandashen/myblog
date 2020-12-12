package com.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 权限认证配置
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig  extends WebSecurityConfigurerAdapter {
    @Autowired
    MyAuthenticationProvider myAuthenticationProvider;

    @Autowired
    UserDetailServiceImpl userDetailService;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/blog").permitAll()
                .antMatchers("/img/**").permitAll()
                .antMatchers("/css/**").permitAll()
                .antMatchers("/editor.md-master/**").permitAll()
                .antMatchers("/admin/blog/queryAll").hasRole("ADMIN")
                .antMatchers("/admin").hasRole("ADMIN")
                .antMatchers("/admin/blog/BlogList").hasRole("ADMIN")
                .antMatchers("/admin/blog/addBlog").hasRole("USER")
                .antMatchers("/admin/blog/upload").hasRole("USER")
                .antMatchers("/admin/blog/**/update").hasAnyRole("USER","ADMIN")
                .antMatchers("/admin/blog/UpdateBlog").hasRole("USER");
        //关闭  CSRF disable() 禁用
        http.csrf().disable();
        http.formLogin().loginPage("/login")
                .loginProcessingUrl("/toLogin")
                .usernameParameter("username")
                .passwordParameter("password");
        //注销 默认链接是/logout
        http.logout().logoutSuccessUrl("/");
        //防止iframe 劫持 iframe
        http.headers().frameOptions().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
//                .withUser("admin")
//                .password(new BCryptPasswordEncoder().encode("123456"))
//                .roles("admin");
        // 使用自定义登陆校验器
        auth.authenticationProvider(myAuthenticationProvider);
        // 使用这行代码（使用默认登陆校验器）
        //auth.userDetailsService(userDetailService).passwordEncoder(new BCryptPasswordEncoder());
    }

//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        web.ignoring().antMatchers("/img/**");
//    }
}
