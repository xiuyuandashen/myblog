package com.Service;

import com.entity.Comment;
import com.entity.CommentVO;
import com.entity.CreatCommentForm;

import java.util.List;

public interface commentService {

    List<CommentVO>  getAllCommentByBlogId(int BlogId);



    int addComment(Comment comment);

}
