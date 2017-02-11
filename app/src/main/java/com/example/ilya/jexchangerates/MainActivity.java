package com.example.ilya.jexchangerates;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private DatePicker datePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        datePicker = (DatePicker)findViewById(R.id.datePicker);
        Calendar calendar = Calendar.getInstance();
        calendar.set(1970, 1, 1);
        datePicker.setMinDate(calendar.getTime().getTime());
        datePicker.setMaxDate(System.currentTimeMillis() - 1000);
    }

    static Date getDateFromDatePicker(DatePicker datePicker){
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year =  datePicker.getYear();
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        return calendar.getTime();
    }

    public void btnGetCurrency_Click(View v)
    {
        Intent intent = new Intent(this, RateActivity.class);
        startActivity(intent);
        Date date = getDateFromDatePicker(datePicker);
        intent.putExtra("date", date.getTime());
    }
}
