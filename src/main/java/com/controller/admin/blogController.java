package com.controller.admin;


import com.Config.PageConn;
import com.Service.impl.blogServiceimpl;
import com.entity.abstractBlog;
import com.entity.blog;
import com.entity.myUser;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.util.GithubUploader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("/admin/blog")
public class blogController {

    @Autowired
    blogServiceimpl blogService;

    @Autowired
    PageConn pageConn;

    @Autowired
    com.dao.UserMapper userMapper;

    @Value("${file.uploadFolder}")
    public  String uploadFolder;

    @Autowired
    GithubUploader githubUploader;

    @Autowired
    RedisTemplate redisTemplate;


    @RequestMapping("/queryAll")
    public List<abstractBlog> queryAllBlog(){

        return  blogService.quireAll();
    }



    @RequestMapping("/BlogList")
    public String BlogList(Model model, @RequestParam(value = "pageNum",defaultValue = "1") int pageNum, @RequestParam(value = "pageSize",defaultValue = "3") int pageSize){

        //PageHelper.startPage(pageNum,pageSize);
        List<abstractBlog> abstractBlogs = pageConn.pageList(pageNum, pageSize);
        PageInfo<abstractBlog> blogPageInfo = new PageInfo<>(abstractBlogs);
        model.addAttribute("blogPageInfo",blogPageInfo);
        return "admin/blog_list";
    }



    @RequestMapping("/{id}/update")
    public String queryByIdIsUpdate(@PathVariable("id") Integer id,Model model){
        final blog blog = blogService.quireById(id);
        myUser user = userMapper.selectByUserId(blog.getUserId());
        blog.setUserName(user.getName());
        model.addAttribute("blog",blog);
        return "admin/blog_update";
    }
    @RequestMapping("/UpdateBlog")
    @ResponseBody
    public blog UpdateBlog(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //final blog blog = new blog(bid, b_name, title, content, time);
        ServletInputStream inputStream = request.getInputStream();
        StringBuffer str =new StringBuffer();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream,"utf-8"));
        String len = null;
        while((len = reader.readLine())!=null){
            str.append(len);
        }
        System.out.println(str.toString());
        len = str.toString();
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        blog blog = gson.fromJson(len, blog.class);

        blogService.updateBlog(blog);
        return blog;
    }


    @RequestMapping(value = "/addBlog",produces = "application/json")
    @ResponseBody
    public blog addBlog(HttpServletRequest request, HttpServletResponse response) throws ParseException, IOException {

        ServletInputStream inputStream = request.getInputStream();
        StringBuffer str =new StringBuffer();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream,"utf-8"));
        String len = null;
        while((len = reader.readLine())!=null){
            str.append(len);
        }
        //System.out.println(str.toString());
        len = str.toString();
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        blog blog = gson.fromJson(len, blog.class);
        SecurityContextImpl securityContext = (SecurityContextImpl)request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
        String name = securityContext.getAuthentication().getName();
        myUser myUser = userMapper.loadUserByUsername(name);
        blog.setUserId(myUser.getId());
        blog.setUserName(myUser.getName());
        //System.out.println(blog);
        blogService.addBlog(blog);
        return blog;

    }

    @RequestMapping("/delete/{id}")
    public String deleteBlog(@PathVariable("id") int blogId, Model model){
        int i = blogService.removeBlogById(blogId);
        if(i>0){
            Set keys = redisTemplate.keys("*");
            redisTemplate.delete(keys);
            return "redirect:/admin/blog/BlogList";
        }
        else  {
            model.addAttribute("msg","删除博客失败！");
            return "error/404";
        }
    }

    /**
     * 博客上传图片
     * @param upload    图片 图片不能超过1mb （已经配置成5mb）
     * @param request
     * @return
     */
    @RequestMapping("/upload")
    @ResponseBody                      //value必须是 editormd-image-file
    public Map<String,Object> upload(@RequestParam(value = "editormd-image-file", required = false) MultipartFile upload
            , HttpServletRequest request)  {
        final HashMap<String, Object> resultMap = new HashMap<>();
        try{

            request.setCharacterEncoding("utf-8");
            //request.getClass().getClassLoader().getResource("static").getPath()+"/img/blog";
            //request.getSession().getServletContext().getRealPath("/static/img/blog");
            //final String realPath = request.getClass().getClassLoader().getResource("static").getPath()+"/img/blog";
//            String realPath = uploadFolder +"img/blog";
//            //System.out.println(realPath);
//            File filePath = new File(realPath);
//            if (!filePath.exists()) {
//                filePath.mkdirs();
//            }
//            File realFile = new File(realPath + File.separator + upload.getOriginalFilename());

            //这个是editor 要求的上传文件的格式 或则直接用一个map 就行了 key 分别是success message url
            resultMap.put("success",1);
            resultMap.put("message","上传成功");
            String url = githubUploader.upload(upload);
            resultMap.put("url",url);
            //upload.transferTo(realFile);
            return resultMap;
        }catch (Exception e){
            resultMap.put("success",0);
            resultMap.put("message","上传失败");
            return resultMap;
        }

    }

}
