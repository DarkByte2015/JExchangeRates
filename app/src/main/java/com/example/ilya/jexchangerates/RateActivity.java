package com.example.ilya.jexchangerates;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RateActivity extends AppCompatActivity {
    TextView tbUSD, tbEUR;

    final String CURRENCIES_URL = "http://www.cbr.ru/scripts/XML_daily.asp?date_req=";
    OkHttpClient client = new OkHttpClient();
    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);
        tbUSD = (TextView)findViewById(R.id.tbUSD);
        tbEUR = (TextView)findViewById(R.id.tbEUR);
        Date date = new Date(getIntent().getLongExtra("date", 0));
        String url = CURRENCIES_URL + dateFormat.format(date);
        RequestTask task = new RequestTask();
        task.execute(url);
    }

    public class RequestTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... uri) {
            Request request = new Request.Builder().url(uri[0]).build();

            try (Response response = client.newCall(request).execute()) {
                return response.body().string();
            }
            catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            try {
                ValCurs curs = ValCurs.parse(result);
                tbUSD.setText(curs.get("USD").Value.toString());
                tbEUR.setText(curs.get("EUR").Value.toString());
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
