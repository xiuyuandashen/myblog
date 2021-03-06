package com.Service;

import com.entity.abstractBlog;
import com.entity.blog;

import java.util.List;

public interface blogService {
    //查询全部
    List<abstractBlog> quireAll();

    // 添加博客
    void addBlog(blog blog);

    // 查询博客
    blog quireById(Integer id);

    //删除博客
    int removeBlogById(Integer id);

    //更新博客
    void updateBlog(blog blog);

    // 查询当前用户的所有博客
    List<abstractBlog> quireyByUserId(Integer id);

    List<abstractBlog> quireyByTitle(String title);
}
