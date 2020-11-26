package com.entity;

import java.io.Serializable;
import java.util.Date;

public class Comment  implements Serializable {

    private Long commentId = null;
    private Long userId = null;
    private Long blogId = null;
    private Long replyUserId = null;
    private Long pid = null;
    private String commentMsg;
    private Date createTime;

    public Comment( long userId, long blogId, long replyUserId, long pid, String commentMsg, Date createTime) {

        this.userId = userId;
        this.blogId = blogId;
        this.replyUserId = replyUserId;
        this.pid = pid;
        this.commentMsg = commentMsg;
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "commentId=" + commentId +
                ", userId=" + userId +
                ", blogId=" + blogId +
                ", replyUserId=" + replyUserId +
                ", pid=" + pid +
                ", commentMsg='" + commentMsg + '\'' +
                ", createTime=" + createTime +
                '}';
    }

    public long getCommentId() {
        return commentId;
    }

    public void setCommentId(long commentId) {
        this.commentId = commentId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getBlogId() {
        return blogId;
    }

    public void setBlogId(long blogId) {
        this.blogId = blogId;
    }

    public long getReplyUserId() {
        return replyUserId;
    }

    public void setReplyUserId(long replyUserId) {
        this.replyUserId = replyUserId;
    }

    public long getPid() {
        return pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }

    public String getCommentMsg() {
        return commentMsg;
    }

    public void setCommentMsg(String commentMsg) {
        this.commentMsg = commentMsg;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
