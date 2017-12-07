package com.example.dingo.dingoapp;

import java.util.ArrayList;

/**
 * Created by ajrsa on 2017-12-03.
 */

public class Household {
    private ArrayList<String> emails;
    private String name;

    public Household(String name){
        this.name=name;
    }

    public void addUser(UserInfo user){emails.add(user.getEmail());}
    //public void addUser(String email){emails.add(email);}

    public boolean hasUser(String email){
        return emails.contains(email);
    }
    public String getName(){
        return name;
    }

}
