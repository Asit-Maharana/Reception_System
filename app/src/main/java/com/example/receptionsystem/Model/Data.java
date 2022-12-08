package com.example.receptionsystem.Model;

public class Data {
    public String name;
    public String mobileNo;
    public Integer id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Data(String name, String mobileNo, Integer id) {
        this.name = name;
        this.mobileNo = mobileNo;
        this.id = id;
    }
}
