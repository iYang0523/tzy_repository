package com.jiyun.pojo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//自增主键
    private Long tid;
    private String tname;
    private Integer oid;

    @Override
    public String toString() {
        return "Address{" +
                "tid=" + tid +
                ", tname='" + tname + '\'' +
                ", oid=" + oid +
                '}';
    }

    public Long getTid() {
        return tid;
    }

    public void setTid(Long tid) {
        this.tid = tid;
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public Integer getOid() {
        return oid;
    }

    public void setOid(Integer oid) {
        this.oid = oid;
    }

    public Address() {
    }

    public Address(Long tid, String tname, Integer oid) {
        this.tid = tid;
        this.tname = tname;
        this.oid = oid;
    }
}
