package com.example.dingo.dingoapp;

/**
 * Created by Ash Labelle on 12/1/2017.
 */

public class User {
    private String userid;
    private String username;
    private String useremail;
    private String userpassword;
    private boolean isadmin;

    public User() {
    }

    //Get from Database
    public User(String id, String name, String email, String password, boolean isa) {
        //userid = this.requestIdToken(getString(R.string.default_web_client_id));
        //useremail = this.requestEmail();
        userid = id;
        username = name;
        useremail = email;
        userpassword = password;
        isadmin = isa;
    }

    //Getters
    public String getUserid() {return userid;}
    public String getUsername() {return username;}
    public String getUseremail() {return useremail;}
    public String getUserpassword() {return userpassword;}
    public boolean getIsadmin() {return isadmin;}
}