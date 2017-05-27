package com.panov.ilya.jexchangerates.Views.Rate;

import com.panov.ilya.jexchangerates.Models.CRResultStatus;
import com.panov.ilya.jexchangerates.Models.Currency;
import com.panov.ilya.jexchangerates.Models.CurrencyRates;

import java.util.ArrayList;

/**
 * Created by ilya on 24.05.17.
 */

public interface IRateView {
    String getMessage();
    void setMessage(String value);
    ArrayList<Currency> getRates();
    void setRates(ArrayList<Currency> value);
}
