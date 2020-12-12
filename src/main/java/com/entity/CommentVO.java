package com.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 评论返回类
 */
public class CommentVO implements Serializable {
    private Long commentId;

    private Long userId;

    private String userName;

    //头像地址
    private String headPortrait;

    private String createTime;

    private String commentMsg;

    private List<ReplyVO> replyVO;

    public CommentVO() {
    }

    @Override
    public String toString() {
        return "CommentVO{" +
                "commentId=" + commentId +
                ", userId=" + userId +
                ", userName='" + userName + '\'' +
                ", headPortrait='" + headPortrait + '\'' +
                ", createTime='" + createTime + '\'' +
                ", commentMsg='" + commentMsg + '\'' +
                ", replyVO=" + replyVO +
                '}';
    }

    public long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getHeadPortrait() {
        return headPortrait;
    }

    public void setHeadPortrait(String headPortrait) {
        this.headPortrait = headPortrait;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCommentMsg() {
        return commentMsg;
    }

    public void setCommentMsg(String commentMsg) {
        this.commentMsg = commentMsg;
    }

    public List<ReplyVO> getReplyVO() {
        return replyVO;
    }

    public void setReplyVO(List<ReplyVO> replyVO) {
        this.replyVO = replyVO;
    }
}
