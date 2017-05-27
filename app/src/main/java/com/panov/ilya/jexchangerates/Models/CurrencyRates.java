package com.panov.ilya.jexchangerates.Models;

import org.joda.time.DateTime;
import org.simpleframework.xml.*;
import org.simpleframework.xml.core.*;

import java.io.Reader;
import java.io.StringReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ilya on 23.05.17.
 */

public class CurrencyRates {
    public DateTime date;

    public String name;

    public ArrayList<Currency> rates = new ArrayList<Currency>();

    public Currency getCurrencyByCharCode(final String charCode) {
        for (Currency valute : rates) {
            if (valute.charCode.equals(charCode))
                return valute;
        }

        return null;
    }
}
