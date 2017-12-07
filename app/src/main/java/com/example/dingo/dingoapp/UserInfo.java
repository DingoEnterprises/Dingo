package com.example.dingo.dingoapp;

import android.net.Uri;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

/**
 * Created by Sherry Wang on 2017-11-23.
 */

public class UserInfo {
    static String user = "";
    static String pass = "";
    static String chatWith = "";

    private String id;
    private String name;
    private String givenName;
    private String familyName;
    private String email;
    private Uri profilePhoto;
    private boolean isAdmin;
    private int numberOfStars;
    private String profilePicId;

    //GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);

    public UserInfo(GoogleSignInAccount acct) {
        if (acct != null) {
            name = acct.getDisplayName();
            givenName = acct.getGivenName();
            familyName = acct.getFamilyName();
            email = acct.getEmail();
            id = acct.getId();
            profilePhoto = acct.getPhotoUrl();
        }
    }

    public String getEmail(){return this.email;}
    public boolean getIsAdmin(){return this.isAdmin;}
    public int getNumberOfStars(){return this.numberOfStars;}
    public String getProfilePicId() {return this.profilePicId;}
}
