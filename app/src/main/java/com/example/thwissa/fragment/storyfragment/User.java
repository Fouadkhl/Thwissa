package com.example.thwissa.fragment.storyfragment;

/**
 * USER PROFILE
 */
public class User {
    /** FIELDS */
    private String userName;
    private String profilePicResource;

    /** CONSTRUCTOR */
    public User(String userName, String profilePicResource) {
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

    public String getProfilePicResource() {
        return profilePicResource;
    }

    public void setProfilePicResource(String profilePicResource) {
        this.profilePicResource = profilePicResource;
    }
}
