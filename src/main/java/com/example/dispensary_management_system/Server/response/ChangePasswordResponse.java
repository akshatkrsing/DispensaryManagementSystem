package com.example.dispensary_management_system.Server.response;

import java.io.Serializable;

public class ChangePasswordResponse extends Response implements Serializable {
    private String response;
    public ChangePasswordResponse(String response) {
        this.response = response;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}