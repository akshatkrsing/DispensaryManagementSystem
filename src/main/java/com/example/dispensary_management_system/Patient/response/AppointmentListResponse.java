package com.example.dispensary_management_system.Patient.response;



import com.example.dispensary_management_system.Patient.entity.Appointment;

import java.io.Serializable;
import java.util.ArrayList;

public class AppointmentListResponse extends Response implements Serializable {
    private ArrayList<Appointment> appointmentsList;
    public AppointmentListResponse(ArrayList<Appointment> appointmentsList) {
        this.appointmentsList = appointmentsList;
    }

    public ArrayList<Appointment> getAppointmentsList() {
        return appointmentsList;
    }

    public void setAppointmentsList(ArrayList<Appointment> appointmentsList) {
        this.appointmentsList= appointmentsList;
    }
}