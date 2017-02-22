package com.example.ilya.jexchangerates;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.core.Persist;
import org.simpleframework.xml.core.Validate;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

/**
 * Created by ilya on 21.02.17.
 */

@Root
public class Valute {
    @Attribute
    public String ID;

    @Element
    public Integer NumCode;

    @Element
    public String CharCode;

    @Element
    public Integer Nominal;

    @Element
    public String Name;

    public Double Value;

    @Element(name = "Value")
    private String sValue;

    @Persist
    private void persist() {

        sValue = String.format(Locale.FRANCE, "%f", Value);
    }

    @Validate
    private void validate() {
        try {
            Value = NumberFormat.getInstance(Locale.FRANCE).parse(sValue).doubleValue();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
