package com.example.dispensary_management_system.Server.requestHandler;

import com.example.dispensary_management_system.Server.request.ChangePasswordRequest;
import com.example.dispensary_management_system.Server.response.ChangePasswordResponse;
import com.example.dispensary_management_system.Server.table.PatientTable;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ChangePasswordRequestHandler extends RequestHandler {
    Connection connection;
    ObjectOutputStream oos;
    ChangePasswordRequest changePasswordRequest;

    public ChangePasswordRequestHandler(Connection connection, ObjectOutputStream oos, ChangePasswordRequest changePasswordRequest) {
        this.connection = connection;
        this.oos = oos;
        this.changePasswordRequest = changePasswordRequest;
    }

    @Override
    public void sendResponse(String userID) {
        int result=0;
        try {
            PreparedStatement preparedStatement=connection.prepareStatement(PatientTable.QUERY_CHANGE_PASSWORD);
            preparedStatement.setString(1,changePasswordRequest.getNewPassword());
            preparedStatement.setInt(2,Integer.parseInt(userID));
            preparedStatement.setString(3,changePasswordRequest.getOldPassword());
            result=preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if(result==0)oos.writeObject(new ChangePasswordResponse(""));
            else oos.writeObject(new ChangePasswordResponse("Successful"));
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
