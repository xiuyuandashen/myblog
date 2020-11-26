package com.dao;

import com.entity.Comment;
import com.entity.CommentVO;
import com.entity.CreatCommentForm;
import com.entity.ReplyVO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface commentMapper {

    List<Long> getCommentIdByBlogId(long blogId);

    CommentVO selectCommentById(long commentId);

    List<ReplyVO> selectByPid(long pid);

    Integer addComment(Comment comment);

    //查询pid
    long getCommentByCommentId(long id);

}
