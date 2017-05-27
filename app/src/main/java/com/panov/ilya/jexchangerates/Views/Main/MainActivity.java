package com.panov.ilya.jexchangerates.Views.Main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.ListView;

import com.panov.ilya.jexchangerates.Presenters.MainPresenter;
import com.panov.ilya.jexchangerates.R;
import com.panov.ilya.jexchangerates.Views.Main.IMainView;
import com.panov.ilya.jexchangerates.Views.Rate.RateActivity;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

/**
 * Created by ilya on 23.05.17.
 */

public class MainActivity extends AppCompatActivity implements IMainView {
    MainPresenter presenter;

    @BindView(R.id.datesList)
    ListView datesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        presenter = new MainPresenter(this);

        DatesAdapter adapter = new DatesAdapter(this, R.layout.date_item);
        datesList.setAdapter(adapter);
    }

    @OnItemClick(R.id.datesList)
    public void onDateItemClick(AdapterView<?> parent, View view, int position, long id) {
        DatesAdapter adapter = (DatesAdapter)datesList.getAdapter();
        presenter.onRateDateSelected(adapter.getItem(position));
    }

    @Override
    public void navigateToRateScreen(DateTime date) {
        Intent intent = new Intent(this, RateActivity.class);
        intent.putExtra("date", date);
        startActivity(intent);
    }
}