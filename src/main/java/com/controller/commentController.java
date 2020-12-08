package com.controller;

import com.Service.commentService;
import com.dao.loginMapper;
import com.entity.Comment;
import com.entity.CommentVO;
import com.entity.CreatCommentForm;
import com.entity.myUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/comment")
public class commentController {

    @Autowired
    commentService commentService;

    @Autowired
    com.dao.loginMapper loginMapper;

    @RequestMapping("/{blogId}")
    public List<CommentVO> selectCommentByBlogId(@PathVariable("blogId") int blogId){

        return commentService.getAllCommentByBlogId(blogId);
    }


    @RequestMapping(path = "/addcomment")
    public boolean addComment(@RequestBody CreatCommentForm creatCommentForm, HttpServletRequest request){
        SecurityContextImpl securityContext = (SecurityContextImpl)request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");

        if(securityContext==null)  return false;
        if(creatCommentForm!=null) {
            //System.out.println(creatCommentForm);

            String username = securityContext.getAuthentication().getName();
            myUser user = loginMapper.loadUserByUsername(username);
            Comment comment = new Comment(user.getId(),creatCommentForm.getBlogId(),creatCommentForm.getReplyUserId()
                    ,creatCommentForm.getPid(),creatCommentForm.getCommentMsg(),new Date());
            //System.out.println(comment);
            commentService.addComment(comment);
            return true;
        }
        else  return false;
    }

}
