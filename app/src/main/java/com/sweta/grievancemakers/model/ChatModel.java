package com.sweta.grievancemakers.model;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Date;



public class ChatModel {


    String text;
    String uid;

    public ChatModel() {
    }

    public ChatModel( String text, String uid) {

        this.text = text;
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }



    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }




}
