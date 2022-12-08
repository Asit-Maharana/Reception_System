
package com.example.receptionsystem.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
public class ImageFile {
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("imgType")
    @Expose
    private String imgType;
    @SerializedName("fileName")
    @Expose
    private String fileName;
    @SerializedName("fileType")
    @Expose
    private String fileType;
    @SerializedName("size")
    @Expose
    private Integer size;
    @SerializedName("uuid")
    @Expose
    private String uuid;
    @SerializedName("systemName")
    @Expose
    private String systemName;
    @SerializedName("data")
    @Expose
    private List<String> data = null;
    @SerializedName("visitorRegDto")
    @Expose
    private String visitorRegDto;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getImgType(String personPhoto) {
        return imgType;
    }

    public void setImgType(String imgType) {
        this.imgType = imgType;
    }

    public String getFileName(String s) {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

   public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

   public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }

    public String getVisitorRegDto() {
        return visitorRegDto;
    }

    public void setVisitorRegDto(String visitorRegDto) {
        this.visitorRegDto = visitorRegDto;
    }

    @Override
    public String toString() {
        return "ImageFile{" +
                "userId='" + userId + '\'' +
                ", imgType='" + imgType + '\'' +
                ", fileName='" + fileName + '\'' +
                ", fileType='" + fileType + '\'' +
                ", size=" + size +
                ", uuid='" + uuid + '\'' +
                ", systemName='" + systemName + '\'' +
                ", data=" + data +
                ", visitorRegDto='" + visitorRegDto + '\'' +
                '}';
    }
}
