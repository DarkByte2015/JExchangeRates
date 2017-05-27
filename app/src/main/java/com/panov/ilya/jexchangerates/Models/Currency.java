package com.panov.ilya.jexchangerates.Models;

import org.simpleframework.xml.*;
import org.simpleframework.xml.core.*;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

/**
 * Created by ilya on 23.05.17.
 */

public class Currency {
    public String id;

    public Integer numCode;

    public String charCode;

    public Integer nominal;

    public String name;

    public Double value;
}
