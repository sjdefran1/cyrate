package com.example.cyrate.models;

import com.example.cyrate.UserType;

public class UserModel {
    private int userId;
    private UserType userType;
    private String email;
    private String password;
    private String fullName;
    private String username;
    private String phoneNum;
    private String dob;
    private String photoUrl;

    /**
     *
     * @param email
     * @param password
     */
    public UserModel(String email, String password){
        //default to basic user
        this.userType = UserType.BASIC_USER;
        this.email = email;
        this.password = password;
        this.photoUrl = "";
    }

    /**
     *
     * @return User ID
     */
    public int getUserId() {return userId;}

    /**
     *
     * @param userId
     */
    public void setUserId(int userId) {this.userId = userId;}

    /**
     *
     * @return
     */
    public UserType getUserType() {return userType;}

    /**
     *
     * @param userType
     */
    public void setUserType(UserType userType) {this.userType = userType;}

    /**
     *
     * @return
     */
    public String getEmail() {return email;}

    /**
     *
     * @param email
     */
    public void setEmail(String email) {this.email = email;}

    /**
     *
     * @return
     */
    public String getPassword() {return password;}

    /**
     *
     * @param password
     */
    public void setPassword(String password) {this.password = password;}

    /**
     *
     * @return
     */
    public String getFullName(){return fullName;}

    /**
     *
     * @param fullName
     */
    public void setFullName(String fullName) {this.fullName = fullName;}

    /**
     *
     * @return
     */
    public String getUsername(){return username;}

    /**
     *
     * @param username
     */
    public void setUsername(String username) {this.username = username;}

    /**
     *
     * @return
     */
    public String getPhoneNum() {return phoneNum;}

    /**
     *
     * @param phoneNum
     */
    public void setPhoneNum(String phoneNum) {this.phoneNum = phoneNum;}

    /**
     *
     * @return
     */
    public String getDob() {return dob;}

    /**
     *
     * @param dob
     */
    public void setDob(String dob) {this.dob = dob;}

    /**
     *
     * @return
     */
    public String getPhotoUrl(){return photoUrl;}

    /**
     *
     * @param url
     */
    public void setPhotoUrl(String url) {this.photoUrl = url;}

}

