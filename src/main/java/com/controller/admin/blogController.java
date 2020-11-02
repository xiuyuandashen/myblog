package com.controller.admin;


import com.Service.impl.blogServiceimpl;
import com.entity.blog;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/blog")
public class blogController {

    @Autowired
    blogServiceimpl blogService;

    @RequestMapping("/queryAll")
    public List<blog> queryAllBlog(){

        return  blogService.quireAll();
    }



    @RequestMapping("/BlogList")
    public String BlogList(Model model, @RequestParam(value = "pageNum",defaultValue = "1") int pageNum, @RequestParam(value = "pageSize",defaultValue = "3") int pageSize){
        //final List<blog> blogs = blogService.quireAll();
        //model.addAttribute("blogs",blogs);
        PageHelper.startPage(pageNum,pageSize);
        List<blog> blogs = blogService.quireAll();
        PageInfo<blog> blogPageInfo = new PageInfo<>(blogs);
        model.addAttribute("blogPageInfo",blogPageInfo);
        return "admin/blog_list";
    }



    @RequestMapping("/{id}/update")
    public String queryByIdIsUpdate(@PathVariable("id") Integer id,Model model){
        final blog blog = blogService.quireById(id);
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
        System.out.println(str.toString());
        len = str.toString();
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        blog blog = gson.fromJson(len, blog.class);

        //System.out.println(blog);
        blogService.addBlog(blog);
        return blog;

    }

    /**
     * 博客上传图片
     * @param upload    图片 图片不能超过1mb
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
            final String realPath = request.getClass().getClassLoader().getResource("static").getPath()+"/img/blog";
            System.out.println(realPath);
            File filePath = new File(realPath);
            if (!filePath.exists()) {
                filePath.mkdirs();
            }
            File realFile = new File(realPath + File.separator + upload.getOriginalFilename());

            //这个是editor 要求的上传文件的格式 或则直接用一个map 就行了 key 分别是success message url
            resultMap.put("success",1);
            resultMap.put("message","上传成功");
            resultMap.put("url","/img/blog/" + upload.getOriginalFilename());
            upload.transferTo(realFile);
            return resultMap;
        }catch (Exception e){
            resultMap.put("success",0);
            resultMap.put("message","上传失败");
            return resultMap;
        }

    }

}
