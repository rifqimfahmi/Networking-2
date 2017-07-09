package com.renotekno.zcabez.networking_2.loader;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;
import com.renotekno.zcabez.networking_2.EarthQuake;
import com.renotekno.zcabez.networking_2.QueryUtil;
import com.renotekno.zcabez.networking_2.R;

import java.util.ArrayList;

/**
 * Created by zcabez on 08/07/2017.
 */
public class EarthQuakeLoader extends AsyncTaskLoader<ArrayList<EarthQuake>> {

    public EarthQuakeLoader(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        Log.d("AsyncTask", "onStartLoading...");
        forceLoad();
    }


    @Override
    public ArrayList<EarthQuake> loadInBackground() {
        Log.d("AsyncTask", "loadInBackground...");
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String minMag = sharedPreferences.getString(getContext().getString(R.string.settings_min_magnitude_key), getContext().getString(R.string.settings_min_magnitude_default));

        Uri baseURI = Uri.parse(QueryUtil.USGS_LINK_URI);
        Uri.Builder uriBuilder = baseURI.buildUpon();

        uriBuilder.appendQueryParameter("format", "geojson");
        uriBuilder.appendQueryParameter("minmag", minMag);
        uriBuilder.appendQueryParameter("limit", "10");
        uriBuilder.appendQueryParameter("orderby", "time");

        return QueryUtil.fetchData(uriBuilder.toString());
    }


}
