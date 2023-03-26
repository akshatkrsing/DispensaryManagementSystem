package com.example.dispensary_management_system.Server.main;

import com.example.dispensary_management_system.Server.entity.RegistrationStreamWrapper;
import com.example.dispensary_management_system.Server.request.LoginRequest;
import com.example.dispensary_management_system.Server.request.RegisterRequest;
import com.example.dispensary_management_system.Server.requestHandler.LoginRequestHandler;
import com.example.dispensary_management_system.Server.requestHandler.RegisterRequestHandler;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class RequestIdentifier implements Runnable{
    Socket socket;
    ObjectOutputStream oos=null;
    ObjectInputStream ois=null;
    ServerSocket chatServerSocket;
    public String userID;

    /**
     * Constructor that takes multiple socket parameters and initialises the I/O streams accordingly
     * @param socket is used to create ObjectOutput/Input streams through which communication takes place
     * @param chatServerSocket used to create a chat connection
     */

    public RequestIdentifier(Socket socket, ServerSocket chatServerSocket){
        this.socket=socket;
        this.chatServerSocket = chatServerSocket;
        try {
            oos=new ObjectOutputStream(socket.getOutputStream());
            ois=new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    /**
     * Abstract method of the Runnable interface.
     * This method is called when the thread starts.
     * Listens for requests from the client, uses if else to categorise the request
     * Creates the respective requestHandler instance and calls the sendResponse method
     * sendResponse method processes the request and sends the appropriate response back to the client
     */

    @Override
    public void run() {
        while(socket.isConnected()) {
            Object request;
            try {
                request = Server.receiveRequest(ois);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                break;
            }

            if (request instanceof LoginRequest) {
                //When login request arrives we create an instance of the LoginRequestHandler,
                // pass the required parameters and call the sendResponse method.
                userID = ((LoginRequest) request).getUsername();
                LoginRequestHandler loginRequestHandler = new LoginRequestHandler(oos, (LoginRequest) request, Server.getConnection());
                loginRequestHandler.sendResponse(userID);

                //Once the login is successful we create the chat connection
                if (loginRequestHandler.isLoginSuccessful()) {
                    try {
                        Socket chatSocket = chatServerSocket.accept();
                        ObjectOutputStream objectOutputStream = new ObjectOutputStream(chatSocket.getOutputStream());
                        ObjectInputStream objectInputStream = new ObjectInputStream(chatSocket.getInputStream());

                        /****This Input is from the output stream from the message thread initiating method startMessageThread in Patient.LoginController****/
                        String registrationNumber = (String) objectInputStream.readObject();

                        // After chat connection is created we maintain an ArrayList of the userID and their respective
                        // Output streams
                        Server.socketArrayList.add(new RegistrationStreamWrapper(registrationNumber, objectOutputStream));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else if (request instanceof RegisterRequest) {
                RegisterRequestHandler registerRequestHandler = new RegisterRequestHandler((RegisterRequest) request, oos, Server.getConnection());
                registerRequestHandler.sendResponse(userID);
            }

        }

        System.out.println("Should have broken");
        deleteChatSocketConnection();
    }

    private void deleteChatSocketConnection() {
        //Remove the OOS after disconnection
        System.out.println(userID + " disconnected");
        Server.socketArrayList.removeIf(r -> {
            if(r.getRegistrationNumber().equals(userID)) {
                try {
                    System.out.println("Sending disconnected to their oos");
                    r.getOos().writeObject("disconnected");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return true;
            }
            return false;
        });
        Server.staffSocketArrayList.removeIf(r-> {
            if(r.getStaffId().equals(userID)) {
                try {
                    System.out.println("Sending disconnected to their oos");
                    r.getOos().writeObject("disconnected");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return true;
            }
            return false;
        });
    }
}
