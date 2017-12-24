package com.example.cory.feedthekitty.models;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Akshay on 12/4/2017.
 */

@IgnoreExtraProperties
public class User {

    public String name;
    public String email;
    public Map<String, Object> cardinfo;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

}
