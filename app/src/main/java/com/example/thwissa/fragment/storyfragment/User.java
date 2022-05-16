package com.example.thwissa.fragment.storyfragment;

/**
 * USER PROFILE
 */
public class User {
    /** FIELDS */
    private String userName;
    private int profilePicResource;

    /** CONSTRUCTOR */
    public User(String userName, int profilePicResource) {
        this.userName = userName;
        this.profilePicResource = profilePicResource;
    }

    /** METHODS */
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getProfilePicResource() {
        return profilePicResource;
    }

    public void setProfilePicResource(int profilePicResource) {
        this.profilePicResource = profilePicResource;
    }
}
