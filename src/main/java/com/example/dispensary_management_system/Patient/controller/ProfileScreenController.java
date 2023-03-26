package com.example.dispensary_management_system.Patient.controller;

import com.example.dispensary_management_system.Patient.entity.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

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
}
