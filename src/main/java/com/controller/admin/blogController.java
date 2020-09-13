package com.controller.admin;


import com.Service.impl.blogServiceimpl;
import com.entity.blog;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
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
    public String BlogList(Model model){
        final List<blog> blogs = blogService.quireAll();
        model.addAttribute("blogs",blogs);
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
    public void UpdateBlog(@RequestParam("title") String title
            ,@RequestParam("b_name") String b_name
            ,@RequestParam("content") String content
            ,@RequestParam("time") Date time,
              @RequestParam("bid")  Integer bid){
        final blog blog = new blog(bid, b_name, title, content, time);
        //System.out.println(blog);
        blogService.updateBlog(blog);
    }


    @RequestMapping("/addBlog")
    @ResponseBody
    public void addBlog(@RequestParam("title") String title
            ,@RequestParam("b_name") String b_name
            ,@RequestParam("content") String content
            ,@RequestParam("time") Date time){
        final blog blog = new blog(null, b_name, title, content, time);
//        System.out.println(blog);
        blogService.addBlog(blog);
    }

    /**
     * 博客上传图片
     * @param upload    图片
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
