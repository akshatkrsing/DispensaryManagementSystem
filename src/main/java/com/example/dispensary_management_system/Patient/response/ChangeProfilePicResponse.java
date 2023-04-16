package com.example.dispensary_management_system.Patient.response;

import com.example.dispensary_management_system.Patient.response.Response;

import java.io.Serializable;

public class ChangeProfilePicResponse extends Response implements Serializable {
    private String response;
    public ChangeProfilePicResponse(String response) {
        this.response = response;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
