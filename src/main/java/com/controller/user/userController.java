package com.controller.user;

import com.Config.PageConn;
import com.Service.impl.blogServiceimpl;
import com.entity.abstractBlog;
import com.entity.blog;
import com.entity.myUser;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.util.BASE64DecodedMultipartFile;
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
import java.util.*;

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


    @RequestMapping("/user/personalCenter")
    public String personalCenter(HttpServletRequest request,Model model){
        SecurityContextImpl securityContext = (SecurityContextImpl)request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
        myUser user = null;
        if(securityContext!=null){
            String username = securityContext.getAuthentication().getName();
            user = userMapper.loadUserByUsername(username);
            //System.out.println(user);
            if(user==null){
                return "admin/login";
            }
            model.addAttribute("user",user);

        }else{
            model.addAttribute("msg","请先登录！～～");
            return "error/404";
        }
        return "personalCenter";
    }

    @RequestMapping(path = "/user/updatePersonalInformation")
    public String updatePersonalInformation(@RequestParam("username") String username,
                                            @RequestParam("email") String email,
                                            @RequestParam("password") String password,Model model,HttpServletRequest request){

        SecurityContextImpl securityContext = (SecurityContextImpl)request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
        String name = securityContext.getAuthentication().getName();
        myUser user = userMapper.loadUserByUsername(name);
        user.setName(username);
        user.setPassword(password);
        user.setEmail(email);
        int i = userMapper.updateUser(user);
        if(i>0){

            return "redirect:/user/personalCenter";
        }
        model.addAttribute("msg","出现未知错误～～～！");
        return "error/404";
    }

    /**
     * 裁剪图片
     * @param request
     * @param dataURL
     * @return
     */
    @RequestMapping("/user/tailorImg")
    public String tailorImg(HttpServletRequest request,@RequestParam("dataURL") StringBuilder dataURL) throws Exception{
        Map<String,String> map = new HashMap<>();
        System.out.println(dataURL.toString());
        MultipartFile multipartFile = BASE64DecodedMultipartFile.base64ToMultipart(dataURL.toString());
        String url = githubUploader.upload(multipartFile);
        System.out.println(url);
        SecurityContextImpl securityContext = (SecurityContextImpl)request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
        String name = securityContext.getAuthentication().getName();
        myUser user = userMapper.loadUserByUsername(name);
        user.setHeadPortrait(url);
        userMapper.updateUser(user);
        return "admin/login";
    }


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
