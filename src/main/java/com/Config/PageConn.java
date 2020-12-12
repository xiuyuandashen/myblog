package com.Config;

import com.Service.blogService;
import com.entity.abstractBlog;
import com.entity.blog;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 分页
 */
@Component
public class PageConn {

    @Autowired
    blogService blogService;

    @Cacheable(cacheNames = "c1",key = "#pageNum")
    public List<abstractBlog> pageList(int pageNum, int pageSize){
        System.out.println("读取分页博客列表");
        PageHelper.startPage(pageNum,pageSize);
        List<abstractBlog> abstractBlogs = blogService.quireAll();
        return abstractBlogs;
    }
}
