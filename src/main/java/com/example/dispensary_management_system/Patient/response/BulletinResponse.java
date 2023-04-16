package com.example.dispensary_management_system.Patient.response;



import com.example.dispensary_management_system.Patient.entity.Bulletin;

import java.util.ArrayList;

public class BulletinResponse extends Response{
    private ArrayList<Bulletin> bulletinArrayList;

    public BulletinResponse(ArrayList<Bulletin> bulletinArrayList) {
        this.bulletinArrayList = bulletinArrayList;
    }

    public ArrayList<Bulletin> getBulletinArrayList() {
        return bulletinArrayList;
    }
}
