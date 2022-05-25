package com.example.thwissa.fragment.homefragment.willaya;

public class Place {
    private int mPlaceImageResource;
    private String mPlaceName;
    private float mPlaceRate;
    private String mPlaceSummary;


    /** CONSTRUCTOR */
    public Place(int placeImageResource, String placeName, float placeRate, String placeSummary) {
        this.mPlaceImageResource = placeImageResource;
        this.mPlaceName = placeName;
        this.mPlaceRate = placeRate;
        this.mPlaceSummary = placeSummary;

    }

    /** GETTERS & SETTERS */
    public int getmPlaceImageResource() {
        return mPlaceImageResource;
    }

    public void setmPlaceImageResource(int mPlaceImageResource) {
        this.mPlaceImageResource = mPlaceImageResource;
    }

    public String getmPlaceName() {
        return mPlaceName;
    }

    public void setmPlaceName(String mPlaceName) {
        this.mPlaceName = mPlaceName;
    }

    public float getmPlaceRate() {
        return mPlaceRate;
    }

    public void setmPlaceRate(int mPlaceRate) {
        this.mPlaceRate = mPlaceRate;
    }

    public String getmPlaceSummary() {
        return mPlaceSummary;
    }

    public void setmPlaceSummary(String mPlaceSummary) {
        this.mPlaceSummary = mPlaceSummary;
    }
}
