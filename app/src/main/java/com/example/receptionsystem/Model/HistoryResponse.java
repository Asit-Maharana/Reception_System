package com.example.receptionsystem.Model;

import com.example.receptionsystem.Model.HistoryVisitorResponse;

import java.util.List;

public class HistoryResponse {

    private List<HistoryVisitorResponse> visitorResponse = null;

    public List<HistoryVisitorResponse> getVisitorResponse() {
        return visitorResponse;
    }

    public void setVisitorResponse(List<HistoryVisitorResponse> visitorResponse) {
        this.visitorResponse = visitorResponse;
    }

}
