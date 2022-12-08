package com.example.receptionsystem.Model;

public class AutoCompleteTxtModel {
    private String mobileNo;
    private String userName;

    public AutoCompleteTxtModel(String userName2, String mobileNo2) {
        this.userName = userName2;
        this.mobileNo = mobileNo2;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName2) {
        this.userName = userName2;
    }

    public String getMobileNo() {
        return this.mobileNo;
    }

    public void setMobileNo(String mobileNo2) {
        this.mobileNo = mobileNo2;
    }
}

