package com;

import com.Service.blogService;
import com.dao.blogDao;
import com.dao.loginMapper;
import com.entity.blog;
import com.entity.myUser;
import com.entity.role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.crypto.spec.PSource;
import java.util.Date;
import java.util.List;

@SpringBootTest
class MyblogApplicationTests {

    @Autowired
    blogDao blogDao;
    @Autowired
    blogService blogService;

    @Autowired
    loginMapper loginMapper;

    @Test
    void contextLoads() {
        //final List<blog> blogs = blogDao.quireAll();
       //blogDao.addBlog(new blog(null,"测试2","测试2","## 测试",new Date()));
//        List<role> roleByUserId = loginMapper.findRoleByUserId(1);
//        System.out.println(roleByUserId);
//        myUser root = loginMapper.loadUserByUsername("root");
//        System.out.println(root);
        //blogService.removeBlogById(1002);

        blogService.quireAll();



    }



}
