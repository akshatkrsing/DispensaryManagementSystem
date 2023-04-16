package com.example.dispensary_management_system.Server.response;



import com.example.dispensary_management_system.Server.entity.Appointment;
import java.io.Serializable;
import java.util.ArrayList;

public class AppointmentListResponse extends Response implements Serializable {
    private ArrayList<Appointment> appointmentsList;

    public AppointmentListResponse(ArrayList<Appointment> AppointmentsList) {
        this.appointmentsList = AppointmentsList;
    }

    public ArrayList<Appointment> getAppointmentsList() {
        return appointmentsList;
    }

}