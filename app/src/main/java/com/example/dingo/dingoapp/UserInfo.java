package com.example.dingo.dingoapp;

/**
 * Created by Sherry Wang on 2017-11-23.
 */

public class UserInfo {
    static String user = "";
    static String pass = "";
    static String chatWith = "";

    private String firstName;
    private String lastName;
    private String email;
    private boolean isAdmin;
    private int numberOfStars;
    private String profilePicId;

    public UserInfo() {
    }

    public String getEmail(){return this.email;}
    public boolean getIsAdmin(){return this.isAdmin;}
    public int getNumberOfStars(){return this.numberOfStars;}
    public String getProfilePicId() {return this.profilePicId;}
}
