package com.example.doanmobilemusicmedia0312;


public class Validate {
    public static boolean ValidateGmail(String username){
       // String usernameRegex = "username.length() >= 6";
        if(username.length() >= 6){
            return true;
        }
        else{
            return  false;
        }
    }
    public static boolean ValidatePassword(String password){
        if(password.length() >= 6){
            return true;
        }
        else{
            return  false;
        }
    }
}
