package com.bitspilani.apogeear.Models;

import com.google.firebase.Timestamp;

public class Event_Details {
    private String Name,Type;
    private Timestamp Time;

    public Event_Details(String name, String type, Timestamp time) {
        Name = name;
        Type = type;
        Time = time;
    }

    public String getName() {
        return Name;
    }

    public String getType() {
        return Type;
    }

    public Timestamp getTime() {
        return Time;
    }
}
