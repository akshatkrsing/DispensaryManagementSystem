package com.example.dispensary_management_system.Server.response;

public class BookAppointmentResponse extends Response{
    private String response;
    public BookAppointmentResponse(String response){
        this.response =response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getResponse() {
        return response;
    }
}
