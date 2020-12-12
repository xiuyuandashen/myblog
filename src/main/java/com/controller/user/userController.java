package com.controller.user;

import com.Config.PageConn;
import com.Service.impl.blogServiceimpl;
import com.entity.blog;
import com.entity.myUser;
import com.util.GithubUploader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Set;

@Controller
public class userController {


    @Autowired
    blogServiceimpl blogService;

    @Autowired
    PageConn pageConn;

    @Autowired
    com.dao.UserMapper userMapper;

   //图片上传本地路径目录
   // @Value("${file.uploadFolder}")
    //public  String uploadFolder;

    @Autowired
    GithubUploader githubUploader;

    @Autowired
    RedisTemplate redisTemplate;

    @RequestMapping("/{userId}/addBlog")
    @ResponseBody
    public blog addBlog(@RequestBody blog blog,@PathVariable("userId") Integer userId,HttpServletRequest request){
        SecurityContextImpl securityContext = (SecurityContextImpl)request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
        String name = securityContext.getAuthentication().getName();
        //System.out.println(blog);
        blog.setUserId(userId);
        blog.setTime(new Date());
        blog.setUserName(name);
        blogService.addBlog(blog);
        //清除所有缓存
        Set keys = redisTemplate.keys("*");
        redisTemplate.delete(keys);
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
        /**
         *

        System.out.println("uploadFolder: "+ uploadFolder);

        final String realPath = uploadFolder + "img/User";
        //System.out.println(username + "  " + password);

        System.out.println(realPath);
        File filePath = new File(realPath);
        if (!filePath.exists()) {
            filePath.mkdirs();
        }
        String url = filePath + File.separator+headPortrait.getOriginalFilename();
        File file = new File(url);

         */
        try {

            myUser user = new myUser(username,password);
            user.setEmail(email+"@qq.com");
            //user.setHeadPortrait(url);
            // 图床图片路径
            String url = githubUploader.upload(headPortrait);
            System.out.println("url: "+url);
            user.setHeadPortrait(url);
            int i = userMapper.insertUser(user);
            // 普通用户 2 root用户 1
            if(i>0){
                //headPortrait.transferTo(file);



                myUser user1 = userMapper.loadUserByUsername(user.getName());
                userMapper.addUserRole(user1.getId(),2);

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
