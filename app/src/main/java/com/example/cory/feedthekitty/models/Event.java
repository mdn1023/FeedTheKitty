package com.example.cory.feedthekitty.models;

import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ServerValue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Akshay on 12/4/2017.
 */

@IgnoreExtraProperties
public class Event {

    public String eid;
    public String desc;
    public String name;
    public String owner;
    public boolean visibility;
    public long timestamp;
    public List<User> members;
    public Map<String, Integer> expenses;
    public String owner_name;
    public int size;

    public Event(){

    }

    public Event(String eid, String desc, String name, String owner, boolean visibility, List<User> members, Map<String, Integer> expenses, String owner_name, long timestamp){
        this.eid = eid;
        this.desc = desc;
        this.name = name;
        this.owner = owner;
        this.owner_name = owner_name;
        this.visibility = visibility;
        this.timestamp = timestamp;
        this.members = members;
        this.expenses = expenses;
    }
}
