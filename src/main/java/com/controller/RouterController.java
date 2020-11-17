package com.controller;

import com.Config.PageConn;
import com.Service.impl.blogServiceimpl;
import com.entity.blog;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class RouterController {

    @Autowired
    blogServiceimpl blogService;

    @Autowired
    PageConn pageConn;

    @GetMapping(path = {"/","index"})
    public String index(Model model,
                        @RequestParam(value = "pageNum",defaultValue = "1",required = false) Integer pageNum,
                        @RequestParam(value = "pageSize",defaultValue = "3",required = false) Integer pageSize){
        //PageHelper.startPage(pageNum,pageSize);
        final List<blog> blogs = pageConn.pageList(pageNum,pageSize);
        final PageInfo<blog> blogPageInfo = new PageInfo<>(blogs);
        model.addAttribute("blogPageInfo",blogPageInfo);
        return "index";
    }
//    @RequestMapping("/pageInfo")
//    @ResponseBody
//    public PageInfo<blog> pageInfo(Model model,
//                         @RequestParam(value = "pageNum",defaultValue = "1",required = false) Integer pageNum,
//                         @RequestParam(value = "pageSize",defaultValue = "3",required = false) Integer pageSize){
//        PageHelper.startPage(pageNum,pageSize);
//        final List<blog> blogs = blogService.quireAll();
//        final PageInfo<blog> blogPageInfo = new PageInfo<>(blogs);
//        //model.addAttribute("blogPageInfo",blogPageInfo);
//        return blogPageInfo;
//    }



    @RequestMapping(path = {"/admin","/admin/"})
    public String adminIndex(Model model){

        return "admin/admin_index";
    }

    @RequestMapping("/admin/addBlog")
    public String addBlog(){

        return "admin/blog_add";

    }

    @RequestMapping("/login")
    public String login(){

        return "admin/login";
    }

    @RequestMapping("/blog/queryId")
    public String queryById(Model model,@RequestParam("id") Integer id){
        final blog blog = blogService.quireById(id);
        model.addAttribute("blog",blog);
        return "blog";
    }


}
