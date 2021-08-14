package com.Config;

import com.dao.UserMapper;
import com.entity.myUser;
import com.entity.role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 主要是封装从数据库获取的用户信息
 */
@Component
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        // 获取数据库对应名称的用户
        myUser myUser = userMapper.loadUserByUsername(name);
        // 用户不存在
        if(myUser == null)  throw new UsernameNotFoundException("用户不存在");
        // 获取用户对应的角色
        List<role> roleByUserId = userMapper.findRoleByUserId(myUser.getId());

        // 添加角色
        List<GrantedAuthority> authorities = new ArrayList<>();
        for(role r : roleByUserId){
            authorities.add(new SimpleGrantedAuthority(r.getName()));
        }
        // 构建 Security 的 User 对象 得使用 BCryptPasswordEncoder 进行编码
        // 否则会出现 o.s.s.c.b.BCryptPasswordEncoder - Encoded password does not look like BCrypt 报错
        User user = new User(myUser.getName(),new BCryptPasswordEncoder().encode(myUser.getPassword()),authorities);
        return user;
    }
}
