package com.example.thwissa.fragment.homefragment.willaya;

/**
 * RECYCLER VIEW ITEM: image + name
 */
public class Wilaya {
    private int mImageResource;
    private String mWilayaName;

    // CONSTRUCTOR
    public Wilaya(int imageResource, String wilayaName) {
        this.mImageResource = imageResource;
        this.mWilayaName = wilayaName;
    }


    // GETTERS & SETTERS
    public int getmImageResource() {
        return mImageResource;
    }

    public void setmImageResource(int mImageResource) {
        this.mImageResource = mImageResource;
    }

    public String getmWilayaName() {
        return mWilayaName;
    }

    public void setmWilayaName(String mWilayaName) {
        this.mWilayaName = mWilayaName;
    }
}
