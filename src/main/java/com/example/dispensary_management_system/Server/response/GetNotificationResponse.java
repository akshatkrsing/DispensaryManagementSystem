package com.example.dispensary_management_system.Server.response;

import com.example.dispensary_management_system.Server.entity.Notification;

import java.util.ArrayList;

public class GetNotificationResponse extends Response {
    private ArrayList<Notification> notificationArrayList;

    public GetNotificationResponse(ArrayList<Notification> notificationArrayList) {
        this.notificationArrayList = notificationArrayList;
    }

    public ArrayList<Notification> getNotificationArrayList() {
        return notificationArrayList;
    }
}