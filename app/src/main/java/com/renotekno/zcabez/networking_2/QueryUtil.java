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

    public static ArrayList<EarthQuake> fetchData(){
        // Because the data fetching happen too fast
        // we want to simulate if fetching data take long time using Thread.sleep
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String jsonRes = null;

        // 1. Make an URL instance first before making connection request
        //    Use the String url as the parameter for constructor
        URL url = createURL(USGS_LINK);

        try {
            Log.d("AsyncTask", "fetch earthquake data from USGS...");
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

        //2. Initiate null Stream
        InputStreamReader isr = null;
        BufferedReader reader = null;
        String jsonRes = null;
        try {
            //3. Start constructing the request by opening the connection
            //   Opening connection doesn't mean we are making request yet
            //   Prepare the the request detail such as Timeout and Method
            HttpURLConnection request = (HttpURLConnection) url.openConnection();
            request.setRequestMethod("GET");
            request.setReadTimeout(10000);
            request.setConnectTimeout(15000);

            //4. Start making request to the url
            //   In this line we will wait for the response back from the server
            //   It is okay since we are doing it at background thread
            //   It doesn't disturb the process in MainThread
            request.connect();

            //5. We have get the response back
            //   We only want to continue processing when we success retirieve the data
            //   with 200 response code it mean success
            //   1xx informational response
            //   2xx success response
            //   3xx redirection
            //   4xx client error
            //   5xx server error
            if (request.getResponseCode() == 200){

                //6. get the response body as InputStream and wrap in InputStreamReader
                isr = new InputStreamReader(request.getInputStream());


                //7.   Since the read and request was handled directly by the underlying OS
                //   We have to put it in a buffer. Buffer is a memory area that is used to
                //   put data temporarily while its being moved from one place to another
                //   This can save disk usage since each request, network activity often use expensive disk usage
                reader = new BufferedReader(isr);

                //8. Read from the InputStreamReader wrapped in BufferedReader
                jsonRes = readResponse(reader);
            }
        } finally {

            //9. Close the stream we just used to be used for other application that need it
            //   Check first if the stream is not null then close()
            if (isr != null) isr.close();
            if (reader != null) reader.close();
        }

        return jsonRes;
    }

    private static String readResponse(BufferedReader reader) throws IOException {
        StringBuilder json = new StringBuilder();

        // Read the first line of the response
        String line = reader.readLine();

        while (line != null){

            // if the recently read line is not null, append it to string buildeer
            json.append(line);

            // Read the next line and continue the while loop if its not empty
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
