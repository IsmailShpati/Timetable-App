package com.example.timetableapp.model;

import android.net.Uri;

import java.io.Serializable;

public class Link implements Serializable {
    private final String displayText;
    private final String link;

    public Link(String displayText, String link){
        this.displayText = displayText;
        this.link = link;
    }

    public String getDisplayText() {
        return displayText;
    }

    public String getLink() {
        return link;
    }


}
