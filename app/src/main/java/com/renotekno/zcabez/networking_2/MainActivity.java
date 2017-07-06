package com.renotekno.zcabez.networking_2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import com.renotekno.zcabez.networking_2.adapter.ListEarthQuakeAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        ArrayList<EarthQuake> earthQuakes = QueryUtil.geEartQuakes();

        ListEarthQuakeAdapter adapter = new ListEarthQuakeAdapter(this, earthQuakes);

        listView.setAdapter(adapter);
        // update from web
        listView.setOnItemClickListener(adapter);
    }

    private void initView() {
        listView = (ListView) findViewById(R.id.listView);
    }

}
