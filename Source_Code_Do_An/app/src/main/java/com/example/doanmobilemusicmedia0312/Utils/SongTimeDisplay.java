package com.example.doanmobilemusicmedia0312.Utils;

public class SongTimeDisplay {
    public static String display(int mili){
        int seconds = (int)mili / 1000;
        int minutes = Math.floorDiv(seconds,60);
        int secondsLeft = seconds - (minutes*60);
        String result = "";
        if(minutes < 10) result = "0"+minutes+":";
        else result = minutes+":";

        if(secondsLeft<10) result += "0"+secondsLeft;
        else result += secondsLeft;

        return result;
    }
    public static int value(String value){
        String[] arrayTime = value.split(":");
        return  Integer.parseInt(arrayTime[0])*60000 + Integer.parseInt(arrayTime[1])*1000;
    }
}
