package com.finalproject;

import android.app.AlertDialog;
import android.content.Context;

public class Message {
    public static void warning(String message, Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Warning");
        builder.setMessage(message);
        builder.setPositiveButton("OK", null);
        builder.create().show();
    }
    public static void info(String message, Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Information");
        builder.setMessage(message);
        builder.setPositiveButton("OK", null);
        builder.create().show();
    }
    public static void error(String message, Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Error");
        builder.setMessage(message);
        builder.setPositiveButton("OK", null);
        builder.create().show();
    }

}
