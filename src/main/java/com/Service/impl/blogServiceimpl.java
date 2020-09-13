package com.Service.impl;

import com.Service.blogService;
import com.dao.blogDao;
import com.entity.blog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class blogServiceimpl  implements blogService {

    @Autowired
    blogDao blogDao;

    @Override
    public List<blog> quireAll() {
        return blogDao.quireAll();
    }

    @Override
    public void addBlog(blog blog) {
        blogDao.addBlog(blog);
    }

    @Override
    public blog quireById(Integer id) {
        return blogDao.quireById(id);
    }

    @Override
    public void removeBlogById(Integer id) {
        blogDao.removeBlogById(id);
    }

    @Override
    public void updateBlog(blog blog) {
        blogDao.updateBlog(blog);
    }
}
