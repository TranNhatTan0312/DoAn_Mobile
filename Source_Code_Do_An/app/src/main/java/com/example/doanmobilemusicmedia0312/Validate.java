package com.example.doanmobilemusicmedia0312;


public class Validate {
    public static boolean ValidateUsername(String username){
        String usernameRegex = "^[a-zA-Z]{6,}$";
        if(username.matches(usernameRegex)){
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
