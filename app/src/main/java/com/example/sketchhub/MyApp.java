package com.example.sketchhub;

import android.app.Application;

public class MyApp extends Application {

    private static Database database;
    private static User userConnected;

    @Override
    public void onCreate() {
        super.onCreate();
        database = new Database(this);
        userConnected = null;
    }

    public static Database getDatabase() {
        return database;
    }

    public static User getUserConnected() {
        return userConnected;
    }

    public static void setUserConnected(User newUserConnected){
        userConnected = newUserConnected;
    }
}

