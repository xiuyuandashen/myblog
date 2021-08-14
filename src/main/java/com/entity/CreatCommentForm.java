package com.entity;

/**
 * 创建评论类
 */
public class CreatCommentForm {

    // 博客id
    private Long blogId;

    //评论内容
    private String commentMsg;

    //被回复人id
    private Long replyUserId;

    //父级评论id 、若为首级评论则为 0
    private Long pid;

    public CreatCommentForm() {
    }

    public CreatCommentForm(Long blogId, String commentMsg, Long replyUserId, Long pid) {
        this.blogId = blogId;
        this.commentMsg = commentMsg;
        this.replyUserId = replyUserId;
        this.pid = pid;
    }

    @Override
    public String toString() {
        return "CreatCommentForm{" +
                "blogId=" + blogId +
                ", commentMsg='" + commentMsg + '\'' +
                ", replyUserId=" + replyUserId +
                ", pid=" + pid +
                '}';
    }

    public Long getBlogId() {
        return blogId;
    }

    public void setBlogId(Long blogId) {
        this.blogId = blogId;
    }

    public String getCommentMsg() {
        return commentMsg;
    }

    public void setCommentMsg(String commentMsg) {
        this.commentMsg = commentMsg;
    }

    public Long getReplyUserId() {
        return replyUserId;
    }

    public void setReplyUserId(Long replyUserId) {
        this.replyUserId = replyUserId;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }
}
