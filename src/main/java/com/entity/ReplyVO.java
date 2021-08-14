package com.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 回复返回类
 */
public class ReplyVO implements Serializable {

    private Long commentId;

    private Long userId;

    private String userName;

    private String headPortrait;

    private Long replyUserId;

    private String replyUserName;

    private String createTime;

    private String commentMsg;

    public ReplyVO() {
    }

    @Override
    public String toString() {
        return "ReplyVO{" +
                "commentId=" + commentId +
                ", userId=" + userId +
                ", userName='" + userName + '\'' +
                ", headPortrait='" + headPortrait + '\'' +
                ", replyUserId=" + replyUserId +
                ", replyUserName='" + replyUserName + '\'' +
                ", createTime=" + createTime +
                ", commentMsg='" + commentMsg + '\'' +
                '}';
    }

    public Long getCommentId() {
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

    public Long getReplyUserId() {
        return replyUserId;
    }

    public void setReplyUserId(Long replyUserId) {
        this.replyUserId = replyUserId;
    }

    public String getReplyUserName() {
        return replyUserName;
    }

    public void setReplyUserName(String replyUserName) {
        this.replyUserName = replyUserName;
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
}
