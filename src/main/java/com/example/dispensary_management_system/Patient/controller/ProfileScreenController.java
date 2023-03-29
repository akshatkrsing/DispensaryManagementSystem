package com.example.dispensary_management_system.Patient.controller;

import com.example.dispensary_management_system.Patient.entity.Main;
import com.example.dispensary_management_system.Patient.entity.Notification;
import com.example.dispensary_management_system.Patient.request.GetNotificationRequest;
import com.example.dispensary_management_system.Patient.response.GetNotificationResponse;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.w3c.dom.Node;

import java.io.IOException;
import java.util.ArrayList;

public class ProfileScreenController {

        private String name;
        @FXML
        public Label heyNameLabel;

        public void first(String name) {
            Main.profileScreenController = this;
            this.name=name;
            heyNameLabel.setText("Hey, "+name);
            System.out.println("inside the first method after login trying to create chat socket");

    }
        @FXML
        public VBox notificationContainer;
    public void onNotificationsClicked(Event event) {
        notificationContainer.getChildren().clear();
        Main.sendRequest(new GetNotificationRequest());
        System.out.println("notif request sent");
        GetNotificationResponse getNotificationResponse=(GetNotificationResponse)Main.getResponse();
        System.out.println("notif response received");
        assert getNotificationResponse != null;
        System.out.println("response is "+getNotificationResponse);
        ArrayList<Notification> notificationArrayList=getNotificationResponse.getNotificationArrayList();

        for (Notification notification:notificationArrayList) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../fxml/SingleNotificationCardFXML.fxml"));
            try {
                Node node = fxmlLoader.load();
                SingleNotificationCardFXMLController singleNotificationCardFXMLController = fxmlLoader.getController();
                singleNotificationCardFXMLController.courseLabel.setText(notification.getCourseName());
                singleNotificationCardFXMLController.messageLabel.setText(notification.getText());
                singleNotificationCardFXMLController.timestampLabel.setText(notification.getSentAt().toString());
                notificationContainer.getChildren().add((javafx.scene.Node) node);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }




}
