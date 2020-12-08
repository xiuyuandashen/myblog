package com.controller.user;

import com.Config.PageConn;
import com.Service.impl.blogServiceimpl;
import com.entity.blog;
import com.entity.myUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Date;

@Controller
public class userController {


    @Autowired
    blogServiceimpl blogService;

    @Autowired
    PageConn pageConn;

    @Autowired
    com.dao.loginMapper loginMapper;

    @RequestMapping("/{userId}/addBlog")
    @ResponseBody
    public blog addBlog(@RequestBody blog blog,@PathVariable("userId") Integer userId){

        //System.out.println(blog);
        blog.setUserId(userId);
        blog.setTime(new Date());
        blogService.addBlog(blog);
        return blog;
    }

    /**
     * 用户注册接口
     * @param username
     * @param password
     * @param headPortrait 头像
     * @param request
     * @return
     */
    @RequestMapping("/user/registeredUser")
    @Transactional
    public String registeredUser(@RequestParam("username") String username, @RequestParam("password") String password ,
                                @RequestParam("email") String email,
                                 @RequestParam("imgFile") MultipartFile headPortrait, HttpServletRequest request, Model model){
        final String realPath = request.getClass().getClassLoader().getResource("static").getPath()+"/img/User";
        //System.out.println(username + "  " + password);

        System.out.println(realPath);
        File filePath = new File(realPath);
        if (!filePath.exists()) {
            filePath.mkdirs();
        }
        String url = filePath + File.separator+headPortrait.getOriginalFilename();
        File file = new File(url);

        try {

            myUser user = new myUser(username,password);
            user.setEmail(email+"@qq.com");
            user.setHeadPortrait(url.substring(url.indexOf("/img")));
            int i =loginMapper.insertUser(user);
            // 普通用户 2 root用户 1
            if(i>0){
                myUser user1 = loginMapper.loadUserByUsername(user.getName());
                loginMapper.addUserRole(user1.getId(),2);
                headPortrait.transferTo(file);
                return "redirect:/";
            }else {
                new RuntimeException("注册失败");
                model.addAttribute("msg","注册失败");
                return "error/404";
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        model.addAttribute("msg","注册失败");
        return "error/404";

    }

}
