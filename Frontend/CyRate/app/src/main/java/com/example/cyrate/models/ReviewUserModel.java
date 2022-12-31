package com.example.cyrate.models;

public class ReviewUserModel {
    private int userId;
    private String fullName;
    private String username;
    private String photoUrl;

    /**
     * Model class to hold only the required User info needed for Reviews.
     * Prevents unnecessary data from being passed.
     *
     * @param userId
     * @param fullName
     * @param username
     * @param photoUrl
     */
    public ReviewUserModel(int userId, String fullName, String username, String photoUrl) {
        this.userId = userId;
        this.fullName = fullName;
        this.username = username;
        this.photoUrl = photoUrl;
    }

    /**
     *
     * @return User ID of reviewer
     */
    public int getUserId() {
        return userId;
    }

    /**
     *
     * @param userId
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     *
     * @return Reviewer Full Name
     */
    public String getFullName() {
        return fullName;
    }

    /**
     *
     * @param fullName
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     *
     * @return Reviewer Username
     */
    public String getUsername() {
        return username;
    }

    /**
     *
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     *
     * @return Reviewer profile picture URL
     */
    public String getPhotoUrl() {
        return photoUrl;
    }

    /**
     *
     * @param photoUrl
     */
    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
