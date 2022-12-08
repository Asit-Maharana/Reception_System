package com.example.receptionsystem.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
public class RegistrationModel {
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("mobileNo")
    @Expose
    private String mobileNo;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("whomToVisit")
    @Expose
    private String whomToVisit;
    @SerializedName("idNumber")
    @Expose
    private String idNumber;
    @SerializedName("images")
    @Expose
    private List<ImageFile> images = null;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getWhomToVisit() {
        return whomToVisit;
    }

    public void setWhomToVisit(String whomToVisit) {
        this.whomToVisit = whomToVisit;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public List<ImageFile> getImages() {
        return images;
    }

    public void setImages(List<ImageFile> images) {
        this.images = images;
    }

    @Override
    public String toString() {
        return "RegistrationModel{" +
                "name='" + name + '\'' +
                ", mobileNo='" + mobileNo + '\'' +
                ", address='" + address + '\'' +
                ", userId='" + userId + '\'' +
                ", whomToVisit='" + whomToVisit + '\'' +
                ", idNumber='" + idNumber + '\'' +
                ", images=" + images +
                '}';
    }
}
