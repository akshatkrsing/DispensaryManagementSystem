package com.example.dispensary_management_system.Server.requestHandler;

import com.example.dispensary_management_system.Server.request.RegisterRequest;
import com.example.dispensary_management_system.Server.response.RegisterResponse;
import com.example.dispensary_management_system.Server.table.PatientTable;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegisterRequestHandler extends RequestHandler{
    private RegisterRequest registerRequest;
    private ObjectOutputStream oos;
    private Connection connection;

    public RegisterRequestHandler(RegisterRequest registerRequest, ObjectOutputStream oos, Connection connection) {
        this.registerRequest = registerRequest;
        this.oos = oos;
        this.connection = connection;
    }
    @Override
    public void sendResponse(String userID) {
        PreparedStatement preparedStatement;
        File file=new File("");
        FileInputStream fis;
        int result = 0;
        try {
            preparedStatement=connection.prepareStatement(PatientTable.QUERY_REGISTER);
            preparedStatement.setString(1,registerRequest.getUsername());
            preparedStatement.setString(2,registerRequest.getFirstName());
            preparedStatement.setString(3,registerRequest.getLastName());
            preparedStatement.setString(4,registerRequest.getEmailID());
            preparedStatement.setString(5,registerRequest.getPassword());
            fis=new FileInputStream(file);
            preparedStatement.setBinaryStream(6,fis);
            System.out.println(preparedStatement);
            result=preparedStatement.executeUpdate();
        } catch (SQLException | FileNotFoundException e) {
            e.printStackTrace();
        }
        if(result==0) {
            try {
                oos.writeObject(new RegisterResponse(""));
                oos.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            try {
                oos.writeObject(new RegisterResponse("Registered successfully"));
                oos.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    }


