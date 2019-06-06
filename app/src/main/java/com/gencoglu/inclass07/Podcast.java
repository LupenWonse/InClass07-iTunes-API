package com.gencoglu.inclass07;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by ahmet on 03/10/2016.
 */

public class Podcast implements Serializable {



    String title;
    String thumbnail;
    String imageURL;
    String dateReloaded;
    String summary;
    boolean isHit = false;
    int position = 0;

    public Podcast(String title, String thumbnail, String imageURL, String dateReloaded, String summary, int position) {
        this.title = title;
        this.thumbnail = thumbnail;
        this.imageURL = imageURL;
        this.dateReloaded = dateReloaded;
        this.summary = summary;
        this.position = position;
    }

    public void setHit(boolean hit) {
        isHit = hit;
    }
}
