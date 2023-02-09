package com.managmentdic.models;

/**
 * Created by Payam on 1/18/2018.
 */

public class Vocab {
    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    public String getFarsi() {
        return farsi;
    }

    public void setFarsi(String farsi) {
        this.farsi = farsi;
    }

    String english;
    String farsi;

    public Vocab() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Vocab(String english, String farsi) {
        this.english = english;
        this.farsi = farsi;
    }


}
