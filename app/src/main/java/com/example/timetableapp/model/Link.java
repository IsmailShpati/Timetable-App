package com.example.timetableapp.model;

import android.net.Uri;

public class Link {
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
