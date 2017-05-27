package com.panov.ilya.jexchangerates.Views.Main;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.panov.ilya.jexchangerates.Models.Currency;
import com.panov.ilya.jexchangerates.R;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;

/**
 * Created by ilya on 24.05.17.
 */

public class DatesAdapter extends ArrayAdapter<DateTime> {
    LayoutInflater inflater;
    int layout;
    final int basePosition = Integer.MAX_VALUE;
    final DateTime baseDate = DateTime.now().plusDays(1);

    public DatesAdapter(@NonNull Context context, @LayoutRes int resource) {
        super(context, resource);

        inflater = LayoutInflater.from(context);
        layout = resource;
    }

    @Override
    public @NonNull View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final ViewHolder viewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(this.layout, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        viewHolder.date.setText(getItem(position).toString("dd.MM.yyyy"));

        return convertView;
    }

    @Override
    public DateTime getItem(int position) {
        return baseDate.minusDays(basePosition - position);
    }

    @Override
    public int getCount() {
        return basePosition;
    }

    private class ViewHolder {
        final TextView date;

        ViewHolder(View view){
            date = (TextView)view.findViewById(R.id.date);
        }
    }
}
