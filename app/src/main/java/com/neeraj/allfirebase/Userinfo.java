package com.neeraj.allfirebase;

import java.io.Serializable;

public class Userinfo implements Serializable {
    String userName="",userEmail="",mobileNumber="";
    public Userinfo()
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

    public Userinfo(String name, String email, String mobile)
    {
        userName=name;
        userEmail=email;
        mobileNumber=mobile;

    }
}
