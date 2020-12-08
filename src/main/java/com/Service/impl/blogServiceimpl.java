package com.Service.impl;

import com.Service.blogService;
import com.dao.blogDao;
import com.entity.abstractBlog;
import com.entity.blog;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@CacheConfig(cacheNames = "c2")
public class blogServiceimpl  implements blogService {

    @Autowired
    blogDao blogDao;

    @Override
    //@Cacheable
    public List<abstractBlog> quireAll() {
        System.out.println("读取全部博客");
        return blogDao.quireAll();
    }


    @Override
    public void addBlog(blog blog) {
        blogDao.addBlog(blog);
    }

    @Override
    @Cacheable
    public blog quireById(Integer id) {
        System.out.println("获取文章");
        return blogDao.quireById(id);
    }

    @Override
    @CacheEvict(key = "#id",beforeInvocation = true) // beforeInvocation 在执行方法前先删除缓存 默认为false
    public void removeBlogById(Integer id) {
        blogDao.removeBlogById(id);
    }

    @Override
    @CachePut(key ="#blog" )
    public void updateBlog(blog blog) {
        blogDao.updateBlog(blog);
    }

    @Override
    public List<abstractBlog> quireyByUserId(Integer id) {
        return  blogDao.quireyByUserId(id);
    }
}
