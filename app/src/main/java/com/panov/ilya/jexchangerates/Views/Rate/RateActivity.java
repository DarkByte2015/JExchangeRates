package com.panov.ilya.jexchangerates.Views.Rate;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import com.panov.ilya.jexchangerates.Models.Currency;
import com.panov.ilya.jexchangerates.Presenters.RatePresenter;
import com.panov.ilya.jexchangerates.R;

import org.joda.time.DateTime;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ilya on 24.05.17.
 */

public class RateActivity extends AppCompatActivity implements IRateView {
    RatePresenter presenter;

    @BindView(R.id.message)
    TextView message;

    @BindView(R.id.ratesList)
    ListView ratesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);

        ButterKnife.bind(this);

        presenter = new RatePresenter(this);

        DateTime date = (DateTime)getIntent().getExtras().get("date");
        presenter.onCreate(date);
    }

    public String getMessage() {
        return (String)message.getText();
    }

    public void setMessage(String value) {
        message.setText(value);
    }

    public ArrayList<Currency> getRates() {
        return ((RatesAdapter)ratesList.getAdapter()).ratesList;
    }

    public void setRates(ArrayList<Currency> values) {
        RatesAdapter rates = new RatesAdapter(this, R.layout.rate_item, values);
        ratesList.setAdapter(rates);
    }
}
