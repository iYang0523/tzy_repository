package com.jiyun.pojo;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
public class User {
    @Id //这是一个主键
    @GeneratedValue(strategy = GenerationType.IDENTITY)//自增主键
    private Long id;
    private String name;
    private Integer sex;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;
    private String dizhi;
private Integer shengid;
private Integer shiid;
private Integer xianid;
private String sheng;
private String shi;
private String xian;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getDizhi() {
        return dizhi;
    }

    public void setDizhi(String dizhi) {
        this.dizhi = dizhi;
    }

    public Integer getShengid() {
        return shengid;
    }

    public void setShengid(Integer shengid) {
        this.shengid = shengid;
    }

    public Integer getShiid() {
        return shiid;
    }

    public void setShiid(Integer shiid) {
        this.shiid = shiid;
    }

    public Integer getXianid() {
        return xianid;
    }

    public void setXianid(Integer xianid) {
        this.xianid = xianid;
    }

    public String getSheng() {
        return sheng;
    }

    public void setSheng(String sheng) {
        this.sheng = sheng;
    }

    public String getShi() {
        return shi;
    }

    public void setShi(String shi) {
        this.shi = shi;
    }

    public String getXian() {
        return xian;
    }

    public void setXian(String xian) {
        this.xian = xian;
    }

    public User() {
    }

    public User(Long id, String name, Integer sex, Date birthday, String dizhi, Integer shengid, Integer shiid, Integer xianid, String sheng, String shi, String xian) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.birthday = birthday;
        this.dizhi = dizhi;
        this.shengid = shengid;
        this.shiid = shiid;
        this.xianid = xianid;
        this.sheng = sheng;
        this.shi = shi;
        this.xian = xian;
    }
}
