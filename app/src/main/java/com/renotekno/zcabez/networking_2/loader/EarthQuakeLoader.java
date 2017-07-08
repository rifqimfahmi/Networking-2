package com.renotekno.zcabez.networking_2.loader;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;
import com.renotekno.zcabez.networking_2.EarthQuake;
import com.renotekno.zcabez.networking_2.QueryUtil;

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
        return QueryUtil.fetchData();
    }
}
