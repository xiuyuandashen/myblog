package com.dao;

import com.entity.myUser;
import com.entity.role;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface loginMapper {

    myUser loadUserByUsername(String name);

    List<role> findRoleByUserId(Integer id);
}
