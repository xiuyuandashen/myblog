package com.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

public class abstractBlog implements Serializable {

    private static final long serialVersionUID = 165852037836933055L;

    @ApiModelProperty("id")
    private Integer bid;
    @ApiModelProperty("博客名称")
    private String  bName;
    @ApiModelProperty("博客标题")
    private String title;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    @ApiModelProperty("修改时间")
    private Date time;
    @ApiModelProperty("博主id")
    private Integer userId;




    public abstractBlog() {

    }


    public abstractBlog(Integer bid, String bName, String title, Date time, Integer userId) {
        this.bid = bid;
        this.bName = bName;
        this.title = title;
        this.time = time;
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "abstractBlog{" +
                "bid=" + bid +
                ", bName='" + bName + '\'' +
                ", title='" + title + '\'' +
                ", time=" + time +
                ", userId=" + userId +
                '}';
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

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
