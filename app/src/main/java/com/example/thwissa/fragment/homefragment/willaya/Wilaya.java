package com.example.thwissa.fragment.homefragment.willaya;

import android.provider.ContactsContract;

/**
 * RECYCLER VIEW ITEM: image + name
 */
public class Wilaya {



    private String mImageResource;
    private String mWilayaName;

    // CONSTRUCTOR
    public Wilaya(String imageResource, String wilayaName) {
        this.mImageResource = imageResource;
        this.mWilayaName = wilayaName;
    }


    // GETTERS & SETTERS
    public String getmImageResource() {
        return mImageResource;
    }

    public void setmImageResource(String mImageResource) {
        this.mImageResource = mImageResource;
    }

    public String getmWilayaName() {
        return mWilayaName;
    }

    public void setmWilayaName(String mWilayaName) {
        this.mWilayaName = mWilayaName;
    }
}
