package com.example.thwissa.fragment.newsfragment.classes;

public class Meteo {
    public int backgroundId;
    public int temp;
    public String wilayaName;
    private int weatherState = 0;

    public static final int Sunny = 0;
    public static final int Cloudy = 1;
    public static final int Rainy = 2;

    public Meteo(){}
    public Meteo(int backgroundId, int temp, String wilayaName, int weatherState){
        this.backgroundId = backgroundId;
        this.temp = temp;
        this.wilayaName = wilayaName;
        this.setWeatherState(weatherState);
    }

    public void setWeatherState(int weatherState) {
        if(weatherState == 0 || weatherState == 1 || weatherState == 2) {
            this.weatherState = weatherState;
        }
    }

    public int getWeatherState() {
        return weatherState;
    }
}
