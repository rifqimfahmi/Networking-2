package com.renotekno.zcabez.networking_2;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zcabez on 06/07/2017.
 */
public class EarthQuake {

    private static final String SEPARATOR = " of ";

    private double mag;
    private String range;
    private String country;
    private Date date;

    public EarthQuake(double mag, String country, long date) {
        this.mag = mag;
        this.date = new Date(date);
        assignRangeAndCountry(country);
    }


    public String getMag() {
        DecimalFormat formatter = new DecimalFormat("#.#");
        return formatter.format(mag);
    }

    public String getCountry() {
        return country;
    }

    public String getRange(){
        return range;
    }

    public String getDate() {
        SimpleDateFormat format = new SimpleDateFormat("MM d Y\nH : m a");
        return format.format(date);
    }


    private void assignRangeAndCountry(String country) {
        if (country.contains(SEPARATOR)){
            String[] places = country.split(SEPARATOR);
            this.range = places[0] + SEPARATOR;
            this.country = places[1];
        } else {
            this.range = "Near the";
            this.country = country;
        }
    }
}
