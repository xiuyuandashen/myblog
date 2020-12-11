package com.Service.impl;

import com.Service.commentService;
import com.entity.Comment;
import com.entity.CommentVO;
import com.entity.ReplyVO;
import com.entity.myUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
public class commentServiceimpl implements commentService {

    @Autowired
    com.dao.commentMapper commentMapper;

    @Autowired
    com.dao.UserMapper userMapper;

    @Override
    public List<CommentVO> getAllCommentByBlogId(int BlogId) {
        List<CommentVO> result = new ArrayList<>();
        //查询对应博客的所有评论id
        List<Long> commentIdByBlogId = commentMapper.getCommentIdByBlogId(BlogId);

        for(long commentId : commentIdByBlogId) {
            // 查评论
            CommentVO commentVO = commentMapper.selectCommentById((int) commentId);
            // 评论所属的用户
            myUser myUser1 = userMapper.selectByUserId(commentVO.getUserId());
            commentVO.setUserName(myUser1.getName());
            commentVO.setHeadPortrait(myUser1.getHeadPortrait());

            List<ReplyVO> replyVOS = commentMapper.selectByPid(commentVO.getCommentId());
            List<ReplyVO> reply = new ArrayList<>();
//
            for (ReplyVO replyVO : replyVOS) {
                // 该回复所属的用户
                myUser myUser = userMapper.selectByUserId(replyVO.getUserId());
                //

                replyVO.setReplyUserName(userMapper.selectByUserId(replyVO.getReplyUserId()).getName());
                replyVO.setUserName(myUser.getName());
                replyVO.setHeadPortrait(myUser.getHeadPortrait());
                reply.add(replyVO);

            }

            commentVO.setReplyVO(reply);
            long pid = commentMapper.getCommentByCommentId(commentId);
            // 防止子评论多次添加
            if(pid == 0)
                result.add(commentVO);
        }
        return result;
    }

    @Override
    @Transactional
    public int addComment(Comment comment) {

        return commentMapper.addComment(comment);
    }
}
