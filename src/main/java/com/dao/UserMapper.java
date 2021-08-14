package com.dao;

import com.entity.myUser;
import com.entity.role;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {

    myUser loadUserByUsername(String name);

    List<role> findRoleByUserId(Integer id);


    myUser selectByUserId(long id);

    // 添加一个用户
    int insertUser(myUser user);

    // 添加一个用户角色
    int addUserRole(Integer userId,Integer roleId);

    // 根据userId查找博客数量
    int blogNumber(Integer id);

    // 修改用户信息
    int updateUser(myUser user);

    // 根据用户名,email 修改密码
    int updatePassword(@Param("email") String email, @Param("name") String name, @Param("password") String password);
}
