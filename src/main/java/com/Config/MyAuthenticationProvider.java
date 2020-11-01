package com.Config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class MyAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private UserDetailServiceImpl userDetailService;

    /**
     * 进行身份认证
     * @param authentication
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 获取用户输入的用户名和密码
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        // 获取封装用户信息的对象
        UserDetails userDetails = userDetailService.loadUserByUsername(username);
        // 进行密码的比对
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        boolean flag = bCryptPasswordEncoder.matches(password, userDetails.getPassword());
        // 校验通过
        if(flag){
           return new UsernamePasswordAuthenticationToken(userDetails,new BCryptPasswordEncoder().encode(password),userDetails.getAuthorities());
        }
        // 校验失败
        return null;
    }

    /**
     * 这个方法 确保返回 true 即可，
     * @param authentication
     * @return
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
