package com.example.dispensary_management_system.Patient.controller;

import com.example.dispensary_management_system.Patient.entity.*;
import com.example.dispensary_management_system.Patient.request.*;
import com.example.dispensary_management_system.Patient.response.*;
import com.example.dispensary_management_system.Patient.util.HashUtil;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.w3c.dom.Node;
import sun.awt.image.ToolkitImage;


import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ProfileScreenController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void refreshButtonResponse() {
        setDutyChart();
        setAppointmentsList();
        setProfilePic();
        setBulletin();
    }

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

    @FXML
    private ImageView profilePicImageView;
    @FXML
    private ImageView changeProfilePicImageView;

    private void setProfilePic() {
        GetProfilePicRequest getProfilePicRequest = new GetProfilePicRequest();
        Main.sendRequest(getProfilePicRequest);
        GetProfilePicResponse getProfilePicResponse = (GetProfilePicResponse) Main.getResponse();
        System.out.println("Image input stream received "+getProfilePicResponse);
        BufferedImage bufferedImage;
        Image image;
        if(getProfilePicResponse != null) {
            bufferedImage=  ((ToolkitImage)getProfilePicResponse. getImageIcon().getImage()).getBufferedImage();
            image = SwingFXUtils.toFXImage(bufferedImage, null);
            profilePicImageView.setImage(image);
            changeProfilePicImageView.setImage(image);
        }
    }

    @FXML
    private PasswordField oldPasswordTextField;
    @FXML
    private PasswordField newPasswordTextField;
    @FXML
    private PasswordField confirmNewPasswordTextField;

    public void changePasswordButtonResponse() {
        String oldPassword = oldPasswordTextField.getText();
        String newPassword = newPasswordTextField.getText();
        String confirmedNewPassword = confirmNewPasswordTextField.getText();

        if(newPassword.equals(confirmedNewPassword)) {
            ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest(HashUtil.getMd5(oldPassword),HashUtil.getMd5(newPassword));
            System.out.println("change password request sent");
            Main.sendRequest(changePasswordRequest);
            ChangePasswordResponse changePasswordResponse = (ChangePasswordResponse) Main.getResponse();
            assert changePasswordResponse != null;
            if(changePasswordResponse.getResponse().equals("Successful")) {
                JOptionPane.showMessageDialog(null,"Password changed successfully!");
            }
            else {
                JOptionPane.showMessageDialog(null,"Some error occurred.");
            }
        }
        else {
            JOptionPane.showMessageDialog(null,"New password fields don't match.");
        }
    }

    //Changing profile picture
    @FXML
    private Button selectImageButton;
    File selectedFile;
    //Select the image to set
    public void selectImageButtonResponse() {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("image files","*.png","*.jpg","*.jpeg"));
        selectedFile = fc.showOpenDialog(null);
        selectImageButton.setText(selectedFile.getName());
    }

    //Send request to server to set it
    public void confirmPicChangeButtonResponse() {
        try {
            FileInputStream fis = new FileInputStream(selectedFile);
            byte[] imageArray  = new byte[(int) selectedFile.length()];
            fis.read(imageArray);
            fis.close();
            ChangeProfilePicRequest changeProfilePicRequest = new ChangeProfilePicRequest(imageArray);
            Main.sendRequest(changeProfilePicRequest);
            System.out.println("profile pic Request sent ");
            ChangeProfilePicResponse changeProfilePicResponse = (ChangeProfilePicResponse) Main.getResponse();
            System.out.println("response profile pic "+ changeProfilePicResponse);
            assert changeProfilePicResponse != null;
            if(changeProfilePicResponse.getResponse().equals("Successful")) {
                JOptionPane.showMessageDialog(null,"Profile picture changed successfully!");
                setProfilePic();
            }
            else {
                JOptionPane.showMessageDialog(null,"Some error occurred.");
            }
        }
        catch(FileNotFoundException fileNotFoundException) {
            JOptionPane.showMessageDialog(null,"Please select a valid image first!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private Button logOutButton;
    public void logOutButtonResponse() {
        LogOutRequest logOutRequest = new LogOutRequest();
        Main.sendRequest(logOutRequest);
        LogOutResponse logOutResponse = (LogOutResponse)Main.getResponse();
        assert logOutResponse != null;
        System.out.println("Response received ");
        if(logOutResponse.getResponse().equals("Successful")) {
            System.out.println("Logout "+logOutResponse.getResponse());
            FXMLLoader loader=new FXMLLoader(getClass().getResource("../fxml/Login.fxml"));
            Stage stage=(Stage)logOutButton.getScene().getWindow();
            try {
                Scene scene=new Scene(loader.load(), logOutButton.getScene().getWidth(), logOutButton.getScene().getHeight());
                Main.profileScreenController = null;
                stage.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
            stage.setTitle("Login");
        }
        else {
            JOptionPane.showMessageDialog(null,"LogOut failed. Please try again!");
        }
    }

    @FXML
    private VBox appointmentContainer;
    public void setAppointmentsList(){
        appointmentContainer.getChildren().clear();
        Main.sendRequest(new AppointmentListRequest());
        System.out.println("requesting appointment list");
        AppointmentListResponse appointmentListResponse = (AppointmentListResponse) Main.getResponse();
        System.out.println("got appointment list response");
        assert appointmentListResponse != null;
        System.out.println("response is "+appointmentListResponse);
        ArrayList<Appointment> appointmentArrayList = appointmentListResponse.getAppointmentsList();
        int i =1;
        for(Appointment appointment : appointmentArrayList) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../fxml/AppointmentCardFXML.fxml"));
            try {
                Node node = fxmlLoader.load();
                AppointmentCardLayoutController appointmentCardLayoutController = fxmlLoader.getController();
                appointmentCardLayoutController.appointmentLabel.setText("Appointment : "+String.valueOf(i));
                appointmentCardLayoutController.timeStampLabel.setText(appointment.getTimestamp().toString());
                appointmentCardLayoutController.doctorNameLabel.setText(appointment.getDoctor().getName());
                appointmentCardLayoutController.doctorTypeLabel.setText(appointment.getDoctor().getType());
                appointmentCardLayoutController.statusLabel.setText(appointment.getStatus());
                appointmentCardLayoutController.fromTimeLabel.setText(appointment.getFromTime().toString());
                appointmentCardLayoutController.toTimeLabel.setText(appointment.getToTime().toString());
                appointmentCardLayoutController.memoLabel.setText(appointment.getMemo());
                appointmentCardLayoutController.doctorImage.setImage(appointment.getDoctor().getImage());
                appointmentContainer.getChildren().add((javafx.scene.Node) node);
                ++i;

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @FXML
    private DatePicker appointmentDatePicker ;
    @FXML
    private TextField appointmentFromTime;
    @FXML
    private TextField appointmentToTime;
    @FXML
    private TextField appointmentMemo;
    public void beforeAppointmentPage(){
        appointmentMemo.setTextFormatter(new TextFormatter<>(c -> c.getControlNewText().matches(".{0,120}") ? c : null));
    }
    public void bookAppointment(){

        if(appointmentDatePicker.getValue()==null) {
            JOptionPane.showMessageDialog(null,"Unchecked fields!");
        }
        else if(appointmentFromTime.getText().isEmpty()){
            JOptionPane.showMessageDialog(null,"Please enter the preferred time!");

        }
        else if(appointmentToTime.getText().isEmpty()){
            JOptionPane.showMessageDialog(null,"Please enter the preferred time!");

        }
        else if(appointmentMemo.getText().isEmpty()){
            JOptionPane.showMessageDialog(null,"Enter a short memo specifying your issue!");

        }
        else {
            BookAppointmentRequest bookAppointmentRequest =  new BookAppointmentRequest(appointmentDatePicker.getValue(),appointmentFromTime.getText(),appointmentToTime.getText(),appointmentMemo.getText());
            Main.sendRequest(bookAppointmentRequest);
            BookAppointmentResponse bookAppointmentResponse = (BookAppointmentResponse) Main.getResponse();
            assert bookAppointmentResponse != null;
            System.out.println("Booking response received");
            if(bookAppointmentResponse.getResponse().equals("Successful")) {
                System.out.println("Booking "+bookAppointmentResponse.getResponse());
            }
            else {
                JOptionPane.showMessageDialog(null,"Booking failed. Try again!");
            }

        }
    }

    /****
     ******
     **********
     */
    @FXML
    private VBox dutyChartVBox;
    @FXML
    private Label dutyChartLastUpdate;
    public void setDutyChart(){
        dutyChartVBox.getChildren().clear();
        Main.sendRequest(new DutyChartRequest());
        System.out.println("requesting duty chart list");
        DutyChartResponse dutyChartResponse = (DutyChartResponse) Main.getResponse();
        System.out.println("got duty chart list response");
        assert dutyChartResponse != null;
        System.out.println("response is "+dutyChartResponse);
        dutyChartLastUpdate.setText(dutyChartLastUpdate.getText()+" "+dutyChartResponse.getDutyChartTimestamp().toString());
        ArrayList<DutyChartRow> dutyChartRowArrayList = dutyChartResponse.getDutyChartRowArrayList();
        int i =1;
        for(DutyChartRow dutyChartRow : dutyChartRowArrayList) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../fxml/DutyChartCardLayoutFXML.fxml"));
            try{
                Node node = fxmlLoader.load();
                DutyChartCardLayoutFXMLController dutyChartCardLayoutFXMLController = fxmlLoader.getController();
                dutyChartCardLayoutFXMLController.slNo.setText(String.valueOf(i));
                dutyChartCardLayoutFXMLController.nameOfDoctor.setText(dutyChartRow.getDoctorName());
                dutyChartCardLayoutFXMLController.doctorDutyTime.setText(dutyChartRow.getDoctorDutyTime());
                dutyChartCardLayoutFXMLController.doctorMobileNo.setText(dutyChartRow.getDoctorMobileNo());
                dutyChartVBox.getChildren().add((javafx.scene.Node) node);
                ++i;

            } catch (IOException e) {
                JOptionPane.showMessageDialog(null,"Some error occurred!");
                throw new RuntimeException(e);
            }
        }
    }
    @FXML
    private FlowPane bulletinFlowPane;
    public void setBulletin(){
        bulletinFlowPane.getChildren().clear();
        Main.sendRequest(new BulletinRequest());
        System.out.println("requesting bulletin list");
        BulletinResponse bulletinResponse = (BulletinResponse) Main.getResponse();
        System.out.println("got bulletin list response");
        assert bulletinResponse != null;
        System.out.println("response is "+bulletinResponse);
        ArrayList<Bulletin> bulletinArrayList = bulletinResponse.getBulletinArrayList();
        for(Bulletin bulletin : bulletinArrayList) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../fxml/Bulletin.fxml"));
            try{
                Node node = fxmlLoader.load();
                BulletinController bulletinController = fxmlLoader.getController();
                bulletinController.bulletinTopic.setText(bulletin.getTopic());
                bulletinController.bulletinCaption.setText(bulletin.getCaption());
                bulletinController.bulletinImage.setImage(bulletin.getImage());
                bulletinFlowPane.getChildren().add((javafx.scene.Node) node);

            } catch (IOException e) {
                JOptionPane.showMessageDialog(null,"Some error occurred!");
                throw new RuntimeException(e);
            }
        }

    }

}
