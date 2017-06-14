package com.example.r3l0ad3d.bazarbuy.ModelClass;

/**
 * Created by r3l0ad3d on 5/29/17.
 */

public class UserInformation {
    private String userId;
    private String userFullName;
    private String userEmail;
    private String userMobileNo;
    private String userPassword;
    private String userLocation;
    private String userGender;
    private boolean isLogIn;

    public UserInformation(String userId, String userFullName, String userEmail,
                           String userMobileNo, String userPassword, String userLocation) {
        this.userId = userId;
        this.userFullName = userFullName;
        this.userEmail = userEmail;
        this.userMobileNo = userMobileNo;
        this.userPassword = userPassword;
        this.userLocation = userLocation;
    }

    public UserInformation() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserMobileNo() {
        return userMobileNo;
    }

    public void setUserMobileNo(String userMobileNo) {
        this.userMobileNo = userMobileNo;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserLocation() {
        return userLocation;
    }

    public void setUserLocation(String userLocation) {
        this.userLocation = userLocation;
    }

    public String getUserGender() {
        return userGender;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }

    public boolean isLogIn() {
        return isLogIn;
    }

    public void setLogIn(boolean logIn) {
        isLogIn = logIn;
    }
}
