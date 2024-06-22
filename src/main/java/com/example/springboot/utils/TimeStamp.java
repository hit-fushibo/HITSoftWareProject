package com.example.springboot.utils;

public class TimeStamp {
    public static String getTimeStamp() {
        long currentTime = System.currentTimeMillis();
        String timestamp = String.valueOf(currentTime);
        while (timestamp.length() < 20) {
            timestamp = "0" + timestamp;
        }
        return timestamp;
    }
}
