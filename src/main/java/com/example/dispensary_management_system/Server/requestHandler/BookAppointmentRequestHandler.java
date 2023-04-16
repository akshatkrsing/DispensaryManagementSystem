package com.example.dispensary_management_system.Server.requestHandler;



import com.example.dispensary_management_system.Server.request.BookAppointmentRequest;
import com.example.dispensary_management_system.Server.response.BookAppointmentResponse;


import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BookAppointmentRequestHandler extends RequestHandler{

    private ObjectOutputStream oos;
    private BookAppointmentRequest bookAppointmentRequest;
    private Connection connection;

    public BookAppointmentRequestHandler(Connection connection,ObjectOutputStream oos, BookAppointmentRequest bookAppointmentRequest){
        this.oos = oos;
        this.bookAppointmentRequest = bookAppointmentRequest;
        this.connection = connection;
    }
    @Override
    public void sendResponse(String userID) {
        int result = 0;
        try {
            PreparedStatement preparedStatement=connection.prepareStatement();
            preparedStatement.setInt(1,Integer.parseInt(userID));
            result=preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if(result==0)oos.writeObject(new BookAppointmentResponse("Failure"));
            else oos.writeObject(new BookAppointmentResponse("Successful"));
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
