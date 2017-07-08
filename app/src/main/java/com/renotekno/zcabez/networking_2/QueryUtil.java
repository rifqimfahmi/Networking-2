package com.renotekno.zcabez.networking_2;

import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by zcabez on 06/07/2017.
 */
public class QueryUtil {
    public static final String USGS_LINK = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&minmag=6&limit=10";

    private QueryUtil(){}

    public static ArrayList<EarthQuake> fetchEarthQuakeData(){
        ArrayList<EarthQuake> datas = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(USGS_LINK);
            JSONArray features = jsonObject.getJSONArray("features");
            for (int i = 0; i < features.length(); i++){
                JSONObject data = features.getJSONObject(i);
                JSONObject properties = data.getJSONObject("properties");

                double mag = properties.getDouble("mag");
                long time = properties.getLong("time");
                String place = properties.getString("place");
                String url = properties.getString("url");

                EarthQuake earthQuake = new EarthQuake(mag, place, time, url);
                datas.add(earthQuake);
            }

        } catch (JSONException e) {
            Log.v("EARTHQUAKE DATA",  "error");
            e.printStackTrace();
        }



        return datas;
    }
}
