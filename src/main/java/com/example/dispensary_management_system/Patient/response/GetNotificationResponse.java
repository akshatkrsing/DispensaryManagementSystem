package com.example.dispensary_management_system.Patient.response;

import com.example.dispensary_management_system.Patient.entity.Notification;

import java.util.ArrayList;

public class GetNotificationResponse extends Response{
    private ArrayList<Notification> notificationArrayList;

    public GetNotificationResponse(ArrayList<Notification> notificationArrayList) {
        this.notificationArrayList = notificationArrayList;
    }

    public ArrayList<Notification> getNotificationArrayList() {
        return notificationArrayList;
    }
}
