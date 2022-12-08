
package com.example.receptionsystem.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VisitorResponse {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("mobileNo")
    @Expose
    private String mobileNo;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("whomToVisit")
    @Expose
    private String whomToVisit;
    @SerializedName("idNumber")
    @Expose
    private String idNumber;
    @SerializedName("visitorId")
    @Expose
    private String visitorId;
    @SerializedName("imgUrl")
    @Expose
    private Object imgUrl;

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

    public String getVisitorId() {
        return visitorId;
    }

    public void setVisitorId(String visitorId) {
        this.visitorId = visitorId;
    }

    public Object getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(Object imgUrl) {
        this.imgUrl = imgUrl;
    }

}
