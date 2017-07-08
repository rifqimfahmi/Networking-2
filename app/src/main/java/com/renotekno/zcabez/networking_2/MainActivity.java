package com.renotekno.zcabez.networking_2;

import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import com.renotekno.zcabez.networking_2.adapter.ListEarthQuakeAdapter;
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

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayList<EarthQuake> earthQuakes = new ArrayList<>();
    private ListEarthQuakeAdapter earthQuakeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        earthQuakeAdapter = new ListEarthQuakeAdapter(this, earthQuakes);

        listView.setAdapter(earthQuakeAdapter);
        // update from web
        listView.setOnItemClickListener(earthQuakeAdapter);

        new EarthQuakeAsync().execute(QueryUtil.USGS_LINK);
    }

    private void initView() {
        listView = (ListView) findViewById(R.id.listView);
    }

    public class EarthQuakeAsync extends AsyncTask<String, Void, ArrayList<EarthQuake>> {

        private InputStreamReader isr;
        private BufferedReader reader;

        @Override
        protected ArrayList<EarthQuake> doInBackground(String... strings) {
            Log.d("AsyncTask", "Do in Background...");

            if (strings.length < 1 || strings[0] == null){
                return null;
            }

            return QueryUtil.fetchData(QueryUtil.USGS_LINK);
        }

        @Override
        protected void onPostExecute(ArrayList<EarthQuake> earthQuake) {
            Log.d("AsyncTask", "on Post Execute...");
            if (earthQuake != null) {
                earthQuakes.addAll(earthQuake);
                earthQuakeAdapter.notifyDataSetChanged();
            }
        }

        private ArrayList<EarthQuake> arrayOfEarthQuake(@Nullable String jsonRes){
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

        private String makeRequest(URL url) throws IOException {
            try {
                String jsonRes = "";
                HttpURLConnection request = (HttpURLConnection) url.openConnection();
                request.setRequestMethod("GET");
                request.setReadTimeout(10000);
                request.setConnectTimeout(15000);
                request.connect();

                isr = new InputStreamReader(request.getInputStream());
                jsonRes = readFromISR(isr);
                return jsonRes;
            } finally {
                if (isr != null) isr.close();
                if (reader != null) reader.close();
            }
        }

        private String readFromISR(InputStreamReader isr) throws IOException {
            reader = new BufferedReader(isr);
            StringBuilder json = new StringBuilder();
            String line = reader.readLine();

            while (line != null){
                json.append(line);
                line = reader.readLine();
            }

            return json.toString();
        }

        private URL createURL(String link){
            URL url = null;
            try {
                url = new URL(link);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            return url;
        }

    }


}
