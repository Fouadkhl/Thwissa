package com.example.thwissa.fragment.homefragment.willaya;

import java.util.ArrayList;

public class Place {
    public static class Stats{
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
    public static class Tip{    // REVIEW
        public String text;
        public String date;

        public Tip(String text, String date) {
            this.text = text;
            this.date = date;
        }
    }
    /** 14 FIELDS */
    String fsqId;
    String placeName;
    String placeAddress;
    String placeCategory;
    int placeDistance; // The calculated distance (in meters) from the wilaya
    String placeDescription;
    String placePhoneNumber;
    String placeWebsite;
    double placeRate;          // from 10
    Stats placeStats;
    double placePopularity;     // from 0 to 1
    String placePrice;               // 1: cheap  2: moderate   3: expensive  4: very expensive
    ArrayList<String> placeImagesUrls; // images list
    ArrayList<Tip> placeTips;  // reviews


    /** CONSTRUCTOR */
    public Place(){
        placeImagesUrls = new ArrayList<>();
        placeTips = new ArrayList<>();
    };
}