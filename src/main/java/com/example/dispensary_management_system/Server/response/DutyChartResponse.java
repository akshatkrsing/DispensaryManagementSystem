package com.example.dispensary_management_system.Server.response;

import com.example.dispensary_management_system.Server.entity.DutyChartRow;

import java.sql.Timestamp;
import java.util.ArrayList;

public class DutyChartResponse extends Response {

    private ArrayList<DutyChartRow> dutyChartRowArrayList;
    private Timestamp dutyChartTimestamp;

    public DutyChartResponse(ArrayList<DutyChartRow> dutyChartRowArrayList, Timestamp dutyChartTimestamp) {
        this.dutyChartRowArrayList= dutyChartRowArrayList;
        this.dutyChartTimestamp = dutyChartTimestamp;
    }

    public ArrayList<DutyChartRow> getDutyChartRowArrayList() {
        return dutyChartRowArrayList;
    }

    public Timestamp getDutyChartTimestamp() {
        return dutyChartTimestamp;
    }
}
