
package com.example.receptionsystem.Model;

import java.util.List;

import com.example.receptionsystem.Model.VisitorResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseData {

    @SerializedName("visitorResponse")
    @Expose
    private List<VisitorResponse> visitorResponse = null;

    public List<VisitorResponse> getVisitorResponse() {
        return visitorResponse;
    }

    public void setVisitorResponse(List<VisitorResponse> visitorResponse) {
        this.visitorResponse = visitorResponse;
    }

}
