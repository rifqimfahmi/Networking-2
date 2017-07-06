package com.renotekno.zcabez.networking_2.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.renotekno.zcabez.networking_2.EarthQuake;
import com.renotekno.zcabez.networking_2.R;

import java.util.ArrayList;

import static java.security.AccessController.getContext;

/**
 * Created by zcabez on 06/07/2017.
 */
public class ListEarthQuakeAdapter extends ArrayAdapter<EarthQuake> implements AdapterView.OnItemClickListener {
    public ListEarthQuakeAdapter(Context context, ArrayList<EarthQuake> datas) {
        super(context, 0, datas);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.country_list, parent, false);
        }

        EarthQuake earthQuake = getItem(position);

        TextView mag = (TextView) view.findViewById(R.id.mag);
        TextView range = (TextView) view.findViewById(R.id.range);
        TextView country = (TextView) view.findViewById(R.id.country);
        TextView date = (TextView) view.findViewById(R.id.date);

        mag.setText(earthQuake.getMag());

        GradientDrawable magColor = (GradientDrawable) mag.getBackground();
        magColor.setColor(getMagnitudeColor(earthQuake.getMag()));

        range.setText(earthQuake.getRange());
        country.setText(earthQuake.getCountry());
        date.setText(earthQuake.getDate());

        return view;
    }

    private int getMagnitudeColor(String mag){
        Double magDouble = Double.parseDouble(mag);
        int magIndex = (int) Math.floor(magDouble);
        int color;
        switch (magIndex) {
            case 0:
            case 1:
                color = R.color.magnitude1;
                break;
            case 2:
                color = R.color.magnitude2;
                break;
            case 3:
                color = R.color.magnitude3;
                break;
            case 4:
                color = R.color.magnitude4;
                break;
            case 5:
                color = R.color.magnitude5;
                break;
            case 6:
                color = R.color.magnitude6;
                break;
            case 7:
                color = R.color.magnitude7;
                break;
            case 8:
                color = R.color.magnitude8;
                break;
            case 9:
                color = R.color.magnitude9;
                break;
            default:
                color = R.color.magnitude1;
                break;
        }
        return ContextCompat.getColor(getContext(), color);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        EarthQuake tappedEQ = getItem(position);

        Uri uri = Uri.parse(tappedEQ.getURI());

        Intent intent = new Intent(Intent.ACTION_VIEW, uri);

        getContext().startActivity(intent);
    }
}
