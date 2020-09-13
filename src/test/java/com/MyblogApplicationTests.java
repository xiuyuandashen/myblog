package com;

import com.dao.blogDao;
import com.entity.blog;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

@SpringBootTest
class MyblogApplicationTests {

    @Autowired
    blogDao blogDao;

    @Test
    void contextLoads() {
        //final List<blog> blogs = blogDao.quireAll();
       blogDao.addBlog(new blog(null,"测试2","测试2","## 测试",new Date()));

    }

}
