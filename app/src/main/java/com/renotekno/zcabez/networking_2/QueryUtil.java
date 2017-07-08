package com.renotekno.zcabez.networking_2;

import android.support.annotation.Nullable;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by zcabez on 06/07/2017.
 */
public class QueryUtil {

    public static final String USGS_LINK = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&minmag=6&limit=10";

    private QueryUtil(){}

    public static ArrayList<EarthQuake> fetchData(String urlEarthQuake){

        String jsonRes = null;

        URL url = createURL(urlEarthQuake);

        try {
            jsonRes = makeRequest(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return arrayOfEarthQuake(jsonRes);
    }


    private static ArrayList<EarthQuake> arrayOfEarthQuake(@Nullable String jsonRes){
        if (jsonRes == null) return null;
        ArrayList<EarthQuake> datas = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(jsonRes);
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
            e.printStackTrace();
        }

        return datas;
    }

    private static String makeRequest(URL url) throws IOException {
        InputStreamReader isr = null;
        BufferedReader reader = null;
        String jsonRes = null;
        try {
            HttpURLConnection request = (HttpURLConnection) url.openConnection();
            request.setRequestMethod("GET");
            request.setReadTimeout(10000);
            request.setConnectTimeout(15000);
            request.connect();

            isr = new InputStreamReader(request.getInputStream());
            reader = new BufferedReader(isr);
            jsonRes = readResponse(reader);
        } finally {
            if (isr != null) isr.close();
            if (reader != null) reader.close();
        }

        return jsonRes;
    }

    private static String readResponse(BufferedReader reader) throws IOException {
        StringBuilder json = new StringBuilder();
        String line = reader.readLine();

        while (line != null){
            json.append(line);
            line = reader.readLine();
        }

        return json.toString();
    }

    private static URL createURL(String link){
        URL url = null;
        try {
            url = new URL(link);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }
}
