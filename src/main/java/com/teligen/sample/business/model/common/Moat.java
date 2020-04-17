package com.teligen.sample.business.model.common;

import java.util.Date;

public class Moat {
    private String keyid;

    private Date starttime;

    private Date endtime;

    private String lxbh;

    private String lxmc;

    private String queryPeople;

    private String queryCar;

    public String getKeyid() {
        return keyid;
    }

    public void setKeyid(String keyid) {
        this.keyid = keyid == null ? null : keyid.trim();
    }

    public Date getStarttime() {
        return starttime;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    public String getLxbh() {
        return lxbh;
    }

    public void setLxbh(String lxbh) {
        this.lxbh = lxbh == null ? null : lxbh.trim();
    }

    public String getLxmc() {
        return lxmc;
    }

    public void setLxmc(String lxmc) {
        this.lxmc = lxmc == null ? null : lxmc.trim();
    }

    public String getQueryPeople() {
        return queryPeople;
    }

    public void setQueryPeople(String queryPeople) {
        this.queryPeople = queryPeople == null ? null : queryPeople.trim();
    }

    public String getQueryCar() {
        return queryCar;
    }

    public void setQueryCar(String queryCar) {
        this.queryCar = queryCar == null ? null : queryCar.trim();
    }
}