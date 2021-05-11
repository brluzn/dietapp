package com.example.dietapp.Models;

public class UserInfo {
    public String user_firstname;
    public String user_surname;
    public String user_gender;
    public String user_height;
    public String user_weight;
    public String user_BMI;

    public UserInfo() {
    }

    public UserInfo(String user_firstname, String user_surname, String user_gender, String user_height, String user_weight, String user_BMI) {
        this.user_firstname = user_firstname;
        this.user_surname = user_surname;
        this.user_gender = user_gender;
        this.user_height = user_height;
        this.user_weight = user_weight;
        this.user_BMI = user_BMI;
    }

    public String getUser_firstname() {
        return user_firstname;
    }

    public void setUser_firstname(String user_firstname) {
        this.user_firstname = user_firstname;
    }

    public String getUser_surname() {
        return user_surname;
    }

    public void setUser_surname(String user_surname) {
        this.user_surname = user_surname;
    }

    public String getUser_gender() {
        return user_gender;
    }

    public void setUser_gender(String user_gender) {
        this.user_gender = user_gender;
    }

    public String getUser_height() {
        return user_height;
    }

    public void setUser_height(String user_height) {
        this.user_height = user_height;
    }

    public String getUser_weight() {
        return user_weight;
    }

    public void setUser_weight(String user_weight) {
        this.user_weight = user_weight;
    }

    public String getUser_BMI() {
        return user_BMI;
    }

    public void setUser_BMI(String user_BMI) {
        this.user_BMI = user_BMI;
    }
}
