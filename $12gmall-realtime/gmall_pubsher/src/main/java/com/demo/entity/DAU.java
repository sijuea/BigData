package com.demo.entity;

public class DAU extends DAUKey {
    private String uid;

    private String appid;

    private String area;

    private String os;

    private String ch;

    private String type;

    private String vs;

    private String loghour;

    private Long ts;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid == null ? null : appid.trim();
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area == null ? null : area.trim();
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os == null ? null : os.trim();
    }

    public String getCh() {
        return ch;
    }

    public void setCh(String ch) {
        this.ch = ch == null ? null : ch.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getVs() {
        return vs;
    }

    public void setVs(String vs) {
        this.vs = vs == null ? null : vs.trim();
    }

    public String getLoghour() {
        return loghour;
    }

    public void setLoghour(String loghour) {
        this.loghour = loghour == null ? null : loghour.trim();
    }

    public Long getTs() {
        return ts;
    }

    public void setTs(Long ts) {
        this.ts = ts;
    }
}