package com.controller;

import com.Config.PageConn;
import com.Service.commentService;
import com.Service.impl.blogServiceimpl;
import com.dao.UserMapper;
import com.entity.CommentVO;
import com.entity.abstractBlog;
import com.entity.blog;
import com.entity.myUser;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
public class RouterController {

    @Autowired
    blogServiceimpl blogService;

    @Autowired
    PageConn pageConn;

    @Autowired
    UserMapper userMapper;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    commentService commentService;

    @GetMapping(path = {"/","index"})
    public String index(Model model,
                        @RequestParam(value = "pageNum",defaultValue = "1",required = false) Integer pageNum,
                        @RequestParam(value = "pageSize",defaultValue = "3",required = false) Integer pageSize, HttpServletRequest request){
        //PageHelper.startPage(pageNum,pageSize);
        SecurityContextImpl securityContext = (SecurityContextImpl)request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
        if(securityContext!=null){
            String username = securityContext.getAuthentication().getName();
            myUser user = userMapper.loadUserByUsername(username);
            //System.out.println(user);
            model.addAttribute("user",user);
        }
        List<abstractBlog> abstractBlogs = pageConn.pageList(pageNum, pageSize);
        final PageInfo<abstractBlog> blogPageInfo = new PageInfo<abstractBlog>(abstractBlogs);
        //System.out.println(blogPageInfo);
        model.addAttribute("blogPageInfo",blogPageInfo);
        return "index";
    }

    @GetMapping(path = "/{title}")
    public String index(Model model,@PathVariable("title") String title,
                        @RequestParam(value = "pageNum",defaultValue = "1",required = false) Integer pageNum,
                        @RequestParam(value = "pageSize",defaultValue = "3",required = false) Integer pageSize, HttpServletRequest request){
        //PageHelper.startPage(pageNum,pageSize);
        SecurityContextImpl securityContext = (SecurityContextImpl)request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
        if(securityContext!=null){
            String username = securityContext.getAuthentication().getName();
            myUser user = userMapper.loadUserByUsername(username);
            //System.out.println(user);
            model.addAttribute("user",user);
        }
        List<abstractBlog> abstractBlogs = pageConn.pageListByTitle(title,pageNum, pageSize);
        final PageInfo<abstractBlog> blogPageInfo = new PageInfo<abstractBlog>(abstractBlogs);
        //System.out.println(blogPageInfo);
        model.addAttribute("blogPageInfo",blogPageInfo);
        return "index";
    }



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
            myUser user = userMapper.loadUserByUsername(username);
            //System.out.println(user);
            model.addAttribute("user",user);
            model.addAttribute("flag","true");
        }else {
            model.addAttribute("flag","false");
        }
        final blog blog = blogService.quireById(id);
        model.addAttribute("blog",blog);
        List<CommentVO> comments = commentService.getAllCommentByBlogId(id);
        model.addAttribute("comments",comments);
        return "blog";
    }

    @RequestMapping("/user/addBlog")
    public String userBlogAdd(Model model,HttpServletRequest request){
        SecurityContextImpl securityContext = (SecurityContextImpl)request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
        if(securityContext!=null){
            String username = securityContext.getAuthentication().getName();
            myUser user = userMapper.loadUserByUsername(username);
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
            user = userMapper.loadUserByUsername(username);
            //System.out.println(user);
            model.addAttribute("user",user);
            PageHelper.startPage(pageNum,pageSize);
            List<abstractBlog> abstractBlogs = blogService.quireyByUserId(user.getId());
            final PageInfo<abstractBlog> blogPageInfo = new PageInfo<>(abstractBlogs);
            //System.out.println(blogPageInfo);
            model.addAttribute("blogPageInfo",blogPageInfo);
            int blogNumber = userMapper.blogNumber(user.getId());
            model.addAttribute("blogNumber",blogNumber);
        }
        return "userBlogList";
    }

    @RequestMapping("/registered")
    public String registered(){
        return "registered";
    }

    @RequestMapping("/user/personalCenter")
    public String personalCenter(HttpServletResponse response,Model model) throws IOException {
        model.addAttribute("msg","暂未制作，谢谢～～");
        return "error/404";
    }

    @RequestMapping("/delete/{id}")
    public String deleteBlog(@PathVariable("id") int blogId, Model model){
        int i = blogService.removeBlogById(blogId);
        if(i>0){
            Set keys = redisTemplate.keys("*");
            redisTemplate.delete(keys);
            return "redirect:/user/userBlogList";
        }
        else  {
            model.addAttribute("msg","删除博客失败！");
            return "error/404";
        }
    }

}
