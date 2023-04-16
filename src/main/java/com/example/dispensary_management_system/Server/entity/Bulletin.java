package com.example.dispensary_management_system.Server.entity;

import javafx.scene.image.Image;

import java.io.Serializable;
import java.sql.Timestamp;

public class Bulletin implements Serializable {

    private Image image;
    private String caption;
    private Timestamp timestamp;
    private String topic;
    public Bulletin(Image image,String caption,Timestamp timestamp,String topic){
        this.image = image;
        this.caption = caption;
        this.timestamp = timestamp;
        this.topic = topic;
    }

    public Image getImage() {
        return image;
    }

    public String getCaption() {
        return caption;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public String getTopic() {
        return topic;
    }
}
