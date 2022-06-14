package com.example.thwissa.fragment.homefragment.willaya;

import java.io.Serializable;
import java.util.ArrayList;

public class Place implements Serializable {
    public static class Stats implements Serializable{
        public int totalPhotos;
        public int totalRatings;
        public int totalTips;

        public Stats(int totalPhotos, int totalRatings, int totalTips) {
            this.totalPhotos = totalPhotos;
            this.totalRatings = totalRatings;
            this.totalTips = totalTips;
        }
    }
    /**
     private class Picture{
     public String url;
     public String classification;
     } */
    public static class Tip implements Serializable{    // REVIEW
        public String text;
        public String date;

        public Tip(String text, String date) {
            this.text = text;
            this.date = date.substring(0, 10);
        }
    }
    /** 14 FIELDS */
    public String fsqId;
    public String placeName;
    public String placeAddress;
    public String placeCategory;
    public int placeDistance; // The calculated distance (in meters) from the wilaya
    public String placeDescription;
    public String placePhoneNumber;
    public String placeWebsite;
    public double placeRate;          // from 10
    public Stats placeStats;
    public double placePopularity;     // from 0 to 1
    public String placePrice;               // 1: cheap  2: moderate   3: expensive  4: very expensive
    public ArrayList<String> placeImagesUrls; // images list
    public ArrayList<Tip> placeTips;  // reviews
    // GeoCodes (to find nearby restaurants & hotels)
    public double latitude;
    public double longitude;


    /** CONSTRUCTOR */
    public Place(){
        placeImagesUrls = new ArrayList<>();
        placeTips = new ArrayList<>();
    };

    // REMOVE THEM LATER
    public Place(String fsqId, String placeName, String placeAddress, String placeCategory, int placeDistance, String placeDescription, String placePhoneNumber, String placeWebsite, double placeRate, Stats placeStats, double placePopularity, String placePrice) {
        this.fsqId = fsqId;
        this.placeName = placeName;
        this.placeAddress = placeAddress;
        this.placeCategory = placeCategory;
        this.placeDistance = placeDistance;
        this.placeDescription = placeDescription;
        this.placePhoneNumber = placePhoneNumber;
        this.placeWebsite = placeWebsite;
        this.placeRate = placeRate;
        this.placeStats = placeStats;
        this.placePopularity = placePopularity;
        this.placePrice = placePrice;
        this.placeImagesUrls = new ArrayList<>();
        this.placeTips = new ArrayList<>();
    }
}