package com.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

@ApiModel
public class blog implements Serializable {

    @ApiModelProperty("id")
    private Integer bid;
    @ApiModelProperty("博客名称")
    private String  bName;
    @ApiModelProperty("博客标题")
    private String title;
    @ApiModelProperty("博客内容")
    private String content;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    @ApiModelProperty("修改时间")
    private Date time;
    @ApiModelProperty("博主id")
    private Integer userId;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @ApiModelProperty("博主名")

    private String userName;
    @Override
    public String toString() {
        return "blog{" +
                "bid=" + bid +
                ", bName='" + bName + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", time=" + time +
                '}';
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public blog(Integer bid, String bName, String title, String content, Date time) {
        this.bid = bid;
        this.bName = bName;
        this.title = title;
        this.content = content;
        this.time = time;
    }

    public blog() {

    }

    public Integer getBid() {
        return bid;
    }

    public void setBid(Integer bid) {
        this.bid = bid;
    }

    public String getbName() {
        return bName;
    }

    public void setbName(String bName) {
        this.bName = bName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
