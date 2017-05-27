package com.panov.ilya.jexchangerates.Presenters;

import android.os.AsyncTask;

import com.panov.ilya.jexchangerates.Models.CRResultDto;
import com.panov.ilya.jexchangerates.Models.CRResultStatus;
import com.panov.ilya.jexchangerates.Models.CRService;
import com.panov.ilya.jexchangerates.Models.CRXmlParser;
import com.panov.ilya.jexchangerates.Models.Currency;
import com.panov.ilya.jexchangerates.Models.CurrencyRates;
import com.panov.ilya.jexchangerates.Views.Rate.IRateView;

import org.joda.time.DateTime;

import java.util.ArrayList;

/**
 * Created by ilya on 24.05.17.
 */

public class RatePresenter {
    private IRateView view;

    private final String MSG_DATE_FOUND = "Курсы валют за %s.";
    private final String MSG_DATE_INVALID = "Данные за %s не найдены.\nВыведены курсы валют за %s.";
    private final String MSG_DATE_NOT_FOUND = "Данные за %s не найдены.";

    public RatePresenter(IRateView view) {
        this.view = view;
    }

    public void onCreate(DateTime date) {
        new CRTask().execute(date);
    }

    private class CRTask extends AsyncTask<DateTime, CurrencyRates, CRResultDto> {
        @Override
        protected CRResultDto doInBackground(DateTime... args) {
            DateTime date = args[0].withTime(0, 0, 0, 0);

            CRService service = new CRService(date);
            service.request();

            if (service.getStatus() == CRResultStatus.SERVER_ERROR) {
                String msg = String.format(MSG_DATE_NOT_FOUND, date.toString("dd.MM.yyyy"));
                return new CRResultDto(msg, new ArrayList<Currency>());
            }

            CRXmlParser parser = new CRXmlParser(service.getResult());
            parser.parse();

            if (parser.getStatus() != CRResultStatus.SUCCESS) {
                String msg = String.format(MSG_DATE_NOT_FOUND, date.toString("dd.MM.yyyy"));
                return new CRResultDto(msg, new ArrayList<Currency>());
            }

            CurrencyRates result = parser.getResult();

            if (!result.date.isEqual(date)) {
                String msg = String.format(MSG_DATE_INVALID, date.toString("dd.MM.yyyy"), result.date.toString("dd.MM.yyyy"));
                return new CRResultDto(msg, result.rates);
            }

            String msg = String.format(MSG_DATE_FOUND, date.toString("dd.MM.yyyy"));
            return new CRResultDto(msg, result.rates);
        }

        @Override
        protected void onPostExecute(CRResultDto result) {
            super.onPostExecute(result);

            view.setMessage(result.message);
            view.setRates(result.rates);
        }
    }
}
