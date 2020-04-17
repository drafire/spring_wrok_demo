package com.teligen.demo.model;

public class TCarCompany {
    private String id;

    private String refcheckcarkeyid;

    private String name;

    private String identitycard;

    private String remark;

    private Short sex;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getRefcheckcarkeyid() {
        return refcheckcarkeyid;
    }

    public void setRefcheckcarkeyid(String refcheckcarkeyid) {
        this.refcheckcarkeyid = refcheckcarkeyid == null ? null : refcheckcarkeyid.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getIdentitycard() {
        return identitycard;
    }

    public void setIdentitycard(String identitycard) {
        this.identitycard = identitycard == null ? null : identitycard.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Short getSex() {
        return sex;
    }

    public void setSex(Short sex) {
        this.sex = sex;
    }
}