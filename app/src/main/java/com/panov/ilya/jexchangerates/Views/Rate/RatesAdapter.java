package com.panov.ilya.jexchangerates.Views.Rate;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.panov.ilya.jexchangerates.Models.Currency;
import com.panov.ilya.jexchangerates.R;
import com.panov.ilya.jexchangerates.Views.Main.DatesAdapter;

import org.joda.time.DateTime;

import java.util.ArrayList;

/**
 * Created by ilya on 25.05.17.
 */

public class RatesAdapter extends ArrayAdapter<Currency> {
    LayoutInflater inflater;
    int layout;

    public final ArrayList<Currency> ratesList;

    public RatesAdapter(@NonNull Context context, @LayoutRes int resource, ArrayList<Currency> ratesList) {
        super(context, resource, ratesList);

        this.ratesList = ratesList;
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

        Currency item = getItem(position);
        viewHolder.name.setText(item.name);
        viewHolder.charCode.setText(item.charCode);
        viewHolder.nominal.setText(item.nominal.toString());
        viewHolder.value.setText(item.value.toString());

        return convertView;
    }

    @Override
    public Currency getItem(int position) {
        return ratesList.get(position);
    }

    @Override
    public int getCount() {
        return ratesList.size();
    }

    private class ViewHolder {
        final TextView name;
        final TextView charCode;
        final TextView nominal;
        final TextView value;

        ViewHolder(View view){
            name = (TextView)view.findViewById(R.id.name);
            charCode = (TextView)view.findViewById(R.id.charCode);
            nominal = (TextView)view.findViewById(R.id.nominal);
            value = (TextView)view.findViewById(R.id.value);
        }
    }
}