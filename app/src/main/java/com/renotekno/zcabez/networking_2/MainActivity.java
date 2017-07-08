package com.renotekno.zcabez.networking_2;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.renotekno.zcabez.networking_2.adapter.ListEarthQuakeAdapter;
import com.renotekno.zcabez.networking_2.loader.EarthQuakeLoader;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<EarthQuake>> {

    private ListView listView;
    private ArrayList<EarthQuake> earthQuakes = new ArrayList<>();
    private ListEarthQuakeAdapter earthQuakeAdapter;
    private TextView emptyView;
    private ProgressBar dataFetchProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        earthQuakeAdapter = new ListEarthQuakeAdapter(this, earthQuakes);

        listView.setEmptyView(emptyView);
        listView.setAdapter(earthQuakeAdapter);
        listView.setOnItemClickListener(earthQuakeAdapter);

        // Check internet connection before fetching data from internet
        if(hasInternetConnection()){
            getSupportLoaderManager().initLoader(0, null, this);
        } else {
            // No internet connection found
            // Remvoe the progress bar
            // Set text "No data found"
            // Display Toast "No internet connection"
            dataFetchProgressBar.setVisibility(View.GONE);
            emptyView.setText(this.getString(R.string.no_data_found));
            Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();
        }

    }

    private boolean hasInternetConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()){
            return true;
        }
        return false;
    }

    @Override
    public Loader<ArrayList<EarthQuake>> onCreateLoader(int id, Bundle args) {
        Log.d("AsyncTask", "onCreateLoader...");
        return new EarthQuakeLoader(MainActivity.this);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<EarthQuake>> loader, ArrayList<EarthQuake> data) {
        Log.d("AsyncTask", "onLoadFinished...");

        // This indicate that we have receive data back
        // remove the progressBar from the View
        dataFetchProgressBar.setVisibility(View.GONE);
        if (data != null) {

            // Instead of using earthQuakes.addAll(data) and earthQuakeAdapter.notifyDataSetChanged()
            // we can use the adapter instead to add all data and
            // it will be automatically notified without specifying notifyDataSetChanged()
            earthQuakeAdapter.addAll(data);
        } else {

            // No data found or error happen ( data == null )
            emptyView.setText(this.getString(R.string.no_data_found));
        }
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<EarthQuake>> loader) {
        Log.d("AsyncTask", "onLoaderReset...");

        // onLoaderReset usually called when we press the back button ro destroy the activity
        // When we click the home button {@see onLoaderReset}
        earthQuakeAdapter.clear();
    }

    private void initView() {
        listView = (ListView) findViewById(R.id.listView);
        emptyView = (TextView) findViewById(R.id.emptyView);
        dataFetchProgressBar = (ProgressBar) findViewById(R.id.dataFetchProgressBar);
    }
}
