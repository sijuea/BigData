package com.demo.entity;

public class DAUKey {
    private String mid;

    private String logdate;

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid == null ? null : mid.trim();
    }

    public String getLogdate() {
        return logdate;
    }

    public void setLogdate(String logdate) {
        this.logdate = logdate == null ? null : logdate.trim();
    }
}