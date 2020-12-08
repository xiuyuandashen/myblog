package com.entity;

import java.io.Serializable;

public class myUser implements Serializable {

    private Integer id;
    private String name;
    private String password;

    private String email;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    //头像地址
    private String headPortrait;

    public String getHeadPortrait() {
        return headPortrait;
    }

    public void setHeadPortrait(String headPortrait) {
        this.headPortrait = headPortrait;
    }

    public myUser() {
    }

    public myUser(String name, String password) {
        //this.id = id;
        this.name = name;
        this.password = password;
    }

    @Override
    public String toString() {
        return "myUser{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", headPortrait='" + headPortrait + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
