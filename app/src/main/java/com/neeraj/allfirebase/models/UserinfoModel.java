package com.neeraj.allfirebase.models;

import java.io.Serializable;

public class UserinfoModel implements Serializable {
    String userName="",userEmail="",mobileNumber="";
    public UserinfoModel()
    {

    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public UserinfoModel(String name, String email, String mobile)
    {
        userName=name;
        userEmail=email;
        mobileNumber=mobile;

    }
}
