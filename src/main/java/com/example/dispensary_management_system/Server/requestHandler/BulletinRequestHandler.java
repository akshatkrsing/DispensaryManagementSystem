package com.example.dispensary_management_system.Server.requestHandler;

import com.example.dispensary_management_system.Server.entity.Bulletin;
import com.example.dispensary_management_system.Server.response.BulletinResponse;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.*;
import java.util.ArrayList;

public class BulletinRequestHandler extends RequestHandler{

    Connection connection;
    ObjectOutputStream oos;

    public BulletinRequestHandler(Connection connection, ObjectOutputStream oos) {
        this.connection = connection;
        this.oos = oos;
    }

    @Override
    public void sendResponse(String userID) {
        ArrayList<Bulletin> bulletinArrayList = new ArrayList<>();
        try{
            PreparedStatement preparedStatement = connection.prepareStatement();
            preparedStatement.setString(1,userID);
            System.out.println(preparedStatement);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                bulletinArrayList.add(
                        new Bulletin()
                );
            }
            oos.writeObject(new BulletinResponse(bulletinArrayList));
            oos.flush();
        }
        catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}
