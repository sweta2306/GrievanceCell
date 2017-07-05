package com.sweta.grievancemakers.FireChatHelper;

import android.app.AlertDialog;
import android.content.Context;

import com.sweta.grievancemakers.R;

import java.util.Random;

/**
 * Created by Marcel on 12/8/2015.
 */
public class ChatHelper {

    private static Random randomAvatarGenerator = new Random();
    private static final int NUMBER_OF_AVATAR = 3;

    /*Generate an avatar randomly*/
    public static int  generateRandomAvatarForUser(){
        return randomAvatarGenerator.nextInt(NUMBER_OF_AVATAR);
    }

    public static AlertDialog buildAlertDialog(String title,String message,boolean isCancelable,Context context){

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message)
                .setTitle(title);

        if(isCancelable){
            builder.setPositiveButton(android.R.string.ok, null);
        }else {
            builder.setCancelable(false);
        }
        return builder.create();
    }

}