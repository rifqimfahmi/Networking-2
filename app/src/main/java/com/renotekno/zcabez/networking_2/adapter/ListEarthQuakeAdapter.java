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
        EarthQuakeVH earthQuakeVH;

        if (convertView == null) {
            earthQuakeVH = new EarthQuakeVH();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.country_list, parent, false);
            earthQuakeVH.mag = (TextView) convertView.findViewById(R.id.mag);
            earthQuakeVH.range = (TextView) convertView.findViewById(R.id.range);
            earthQuakeVH.country = (TextView) convertView.findViewById(R.id.country);
            earthQuakeVH.date = (TextView) convertView.findViewById(R.id.date);

            convertView.setTag(earthQuakeVH);
        } else {
            earthQuakeVH = (EarthQuakeVH) convertView.getTag();
        }

        EarthQuake earthQuake = getItem(position);

        earthQuakeVH.mag.setText(earthQuake.getMag());

        GradientDrawable magColor = (GradientDrawable) earthQuakeVH.mag.getBackground();
        magColor.setColor(getMagnitudeColor(earthQuake.getMag()));

        earthQuakeVH.range.setText(earthQuake.getRange());
        earthQuakeVH.country.setText(earthQuake.getCountry());
        earthQuakeVH.date.setText(earthQuake.getDate());

        return convertView;
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

    static class EarthQuakeVH {
        TextView mag;
        TextView range;
        TextView country;
        TextView date;
    }
}
