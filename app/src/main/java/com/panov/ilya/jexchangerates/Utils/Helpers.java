package com.panov.ilya.jexchangerates.Utils;

import org.joda.time.DateTime;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by ilya on 24.05.17.
 */

public class Helpers {
    public enum DecimalSeparator {
        POINT, COMMA
    }

    public static Double parseDouble(String value, DecimalSeparator separator) throws ParseException {
        if (separator == DecimalSeparator.POINT) {
            return Double.parseDouble(value);
        }
        else {
            NumberFormat format = NumberFormat.getInstance(Locale.FRANCE);
            Number number = format.parse(value);
            return number.doubleValue();
        }
    }
}
