package com.example.dispensary_management_system.Patient.request;

import com.example.dispensary_management_system.Patient.request.Request;

public class ChangeProfilePicRequest extends Request {
    private byte[] image;

    /**
     * When a student wants to change her profile pic, this request is sent
     * @param image in the form of byte array is sent
     */
    public ChangeProfilePicRequest(byte[] image) {
        this.image = image;
    }

    //Getter method
    public byte[] getImage() {
        return image;
    }
}
