package com.controller;

import com.Config.PageConn;
import com.Service.impl.blogServiceimpl;
import com.dao.loginMapper;
import com.entity.abstractBlog;
import com.entity.blog;
import com.entity.myUser;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class RouterController {

    @Autowired
    blogServiceimpl blogService;

    @Autowired
    PageConn pageConn;

    @Autowired
    com.dao.loginMapper loginMapper;

    @GetMapping(path = {"/","index"})
    public String index(Model model,
                        @RequestParam(value = "pageNum",defaultValue = "1",required = false) Integer pageNum,
                        @RequestParam(value = "pageSize",defaultValue = "3",required = false) Integer pageSize, HttpServletRequest request){
        //PageHelper.startPage(pageNum,pageSize);
        SecurityContextImpl securityContext = (SecurityContextImpl)request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
        if(securityContext!=null){
            String username = securityContext.getAuthentication().getName();
            myUser user = loginMapper.loadUserByUsername(username);
            //System.out.println(user);
            model.addAttribute("user",user);
        }
        List<abstractBlog> abstractBlogs = pageConn.pageList(pageNum, pageSize);
        final PageInfo<abstractBlog> blogPageInfo = new PageInfo<abstractBlog>(abstractBlogs);
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
    public String queryById(Model model,@RequestParam("id") Integer id,HttpServletRequest request){
        SecurityContextImpl securityContext = (SecurityContextImpl)request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
        if(securityContext!=null){
            String username = securityContext.getAuthentication().getName();
            myUser user = loginMapper.loadUserByUsername(username);
            //System.out.println(user);
            model.addAttribute("user",user);
            model.addAttribute("flag","true");
        }else {
            model.addAttribute("flag","false");
        }
        final blog blog = blogService.quireById(id);
        model.addAttribute("blog",blog);
        return "blog";
    }

    @RequestMapping("/user/addBlog")
    public String userBlogAdd(Model model,HttpServletRequest request){
        SecurityContextImpl securityContext = (SecurityContextImpl)request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
        if(securityContext!=null){
            String username = securityContext.getAuthentication().getName();
            myUser user = loginMapper.loadUserByUsername(username);
            //System.out.println(user);
            model.addAttribute("user",user);
        }
        return "userBlogAdd";
    }

    @RequestMapping("/user/userBlogList")
    public String userBlogList(Model model,HttpServletRequest request,@RequestParam(value = "pageNum",defaultValue = "1",required = false) Integer pageNum,
                               @RequestParam(value = "pageSize",defaultValue = "3",required = false) Integer pageSize){
        SecurityContextImpl securityContext = (SecurityContextImpl)request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
        myUser user = null;
        if(securityContext!=null){
            String username = securityContext.getAuthentication().getName();
            user = loginMapper.loadUserByUsername(username);
            //System.out.println(user);
            model.addAttribute("user",user);
            PageHelper.startPage(pageNum,pageSize);
            List<abstractBlog> abstractBlogs = blogService.quireyByUserId(user.getId());
            final PageInfo<abstractBlog> blogPageInfo = new PageInfo<>(abstractBlogs);
            model.addAttribute("blogPageInfo",blogPageInfo);
            int blogNumber = loginMapper.blogNumber(user.getId());
            model.addAttribute("blogNumber",blogNumber);
        }
        return "userBlogList";
    }

    @RequestMapping("/registered")
    public String registered(){
        return "registered";
    }

}
