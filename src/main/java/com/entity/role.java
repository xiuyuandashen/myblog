package com.entity;

import java.io.Serializable;

public class role implements Serializable {

    private Integer id;
    private String name;

    public role() {
    }

    @Override
    public String toString() {
        return "role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public role(Integer id, String mame) {
        this.id = id;
        this.name = mame;
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

    public void setName(String mame) {
        this.name = mame;
    }
}
