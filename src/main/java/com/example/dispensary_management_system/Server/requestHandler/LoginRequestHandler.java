package com.example.dispensary_management_system.Server.requestHandler;


import com.example.dispensary_management_system.Server.request.LoginRequest;

import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class LoginRequestHandler extends RequestHandler{
    private ObjectOutputStream oos;
    private LoginRequest loginRequest;
    private Connection connection;
    private boolean loginSuccessful;

    public LoginRequestHandler(ObjectOutputStream oos, LoginRequest loginRequest, Connection connection) {
        this.oos = oos;
        this.loginRequest = loginRequest;
        this.connection = connection;
        loginSuccessful = false;
    }

    @Override
    public void sendResponse(String userID) {
        PreparedStatement preaparedStatement;
        try{

        }catch(){

        }

    }



}
