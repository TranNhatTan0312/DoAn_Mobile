package com.example.doanmobilemusicmedia0312.Model;


public class Users {
    private String FullName, email, Phone, Password, Avatar, Gender, Birth;
    public Users(String fullName, String email,String password) {
        FullName = fullName;
        this.email = email;
        Phone = "";
        Password = password;
        Avatar = "";
        Gender = "";
        Birth = "";
    }

    public Users(String fullName, String email, String phone, String password, String avatar, String gender, String birth) {
        FullName = fullName;
        this.email = email;
        Phone = phone;
        Password = password;
        Avatar = avatar;
        Gender = gender;
        Birth = birth;
    }
    public Users(String fullName, String email, String phone, String password, String gender, String birth) {
        FullName = fullName;
        this.email = email;
        Phone = phone;
        Password = password;
        Avatar = "";
        Gender = gender;
        Birth = birth;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getAvatar() {
        return Avatar;
    }

    public void setAvatar(String avatar) {
        Avatar = avatar;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getBirth() {
        return Birth;
    }

    public void setBirth(String birth) {
        Birth = birth;
    }
}

