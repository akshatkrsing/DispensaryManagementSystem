package com.example.dispensary_management_system.Server.request;

import java.sql.Timestamp;
import java.time.LocalDate;

public class BookAppointmentRequest extends Request{
    LocalDate date;
    String fromTime;
    String toTime;
    String memo;
    public BookAppointmentRequest(){

    }
    public BookAppointmentRequest(LocalDate date,String fromTime,String toTime,String memo){
        this.date =date;
        this.toTime = toTime;
        this.fromTime = fromTime;
        this.memo = memo;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getMemo() {
        return memo;
    }

    public Timestamp getCurrentTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }

    public String getFromTime() {
        return fromTime;
    }

    public String getToTime() {
        return toTime;
    }
}
