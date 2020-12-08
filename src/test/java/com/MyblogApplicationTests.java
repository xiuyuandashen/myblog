package com;

import com.Service.blogService;
import com.dao.blogDao;
import com.dao.commentMapper;
import com.dao.loginMapper;
import com.entity.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.spec.PSource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest
class MyblogApplicationTests {

    @Autowired
    blogDao blogDao;
    @Autowired
    blogService blogService;

    @Autowired
    loginMapper loginMapper;

    @Autowired
    com.dao.commentMapper commentMapper;

    @Test
    void contextLoads() {
        //final List<blog> blogs = blogDao.quireAll();
       //blogDao.addBlog(new blog(null,"测试2","测试2","## 测试",new Date()));
//        List<role> roleByUserId = loginMapper.findRoleByUserId(1);
//        System.out.println(roleByUserId);
//        myUser root = loginMapper.loadUserByUsername("root");
//        System.out.println(root);
        //blogService.removeBlogById(1002);
        //myUser user = new myUser("陈悦林","123456");
       // user.setHeadPortrait("/img/5.png");
//        int i = loginMapper.addUserRole(1004,2);
//        System.out.println(i);
        int i = loginMapper.blogNumber(1001);
        System.out.println(i);

    }

    @Test
    void testComment(){
        List<CommentVO> result = new ArrayList<>();
        List<Long> commentIdByBlogId = commentMapper.getCommentIdByBlogId(1001);

        for(long commentId : commentIdByBlogId) {
            CommentVO commentVO = commentMapper.selectCommentById((int) commentId);
            myUser myUser1 = loginMapper.selectByUserId(commentVO.getUserId());
            commentVO.setUserName(myUser1.getName());


            List<ReplyVO> replyVOS = commentMapper.selectByPid(commentVO.getCommentId());
            List<ReplyVO> reply = new ArrayList<>();
//
            for (ReplyVO replyVO : replyVOS) {
                myUser myUser = loginMapper.selectByUserId(replyVO.getUserId());
                replyVO.setReplyUserName(myUser1.getName());
                replyVO.setUserName(myUser.getName());
                reply.add(replyVO);
            }

            commentVO.setReplyVO(reply);
            result.add(commentVO);
        }

        System.out.println(result);

    }
    @Test
    @Transactional
    void add(){

//        Comment comment =new Comment(Long.valueOf(1001),Long.valueOf(1001),0,0,"测试添加2",new Date());
//        int i = commentMapper.addComment(comment);
//        System.out.println(i);i
        long commentByCommentId = commentMapper.getCommentByCommentId(1001);
        System.out.println(commentByCommentId);
    }

}
