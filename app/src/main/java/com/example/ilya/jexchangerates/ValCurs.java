package com.example.ilya.jexchangerates;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.core.Persist;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.core.Validate;

import java.io.Reader;
import java.io.StringReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;;
import java.util.Date;
import java.util.List;;

/**
 * Created by ilya on 21.02.17.
 */

@Root
public class ValCurs {
    public Date Date;

    @Attribute(name = "Date")
    private String sDate;

    @Attribute(name = "name", required = false)
    public String Name;

    @ElementList(inline = true, required = false, entry = "Valute")
    public List<Valute> Valutes;

    private static DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

    private static Persister serializer = new Persister();

    @Persist
    private void persist() {
        sDate = dateFormat.format(Date);
    }

    @Validate
    private void validate() {
        try {
            Date = dateFormat.parse(sDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public Valute get(final String charCode) {
        for (Valute valute : Valutes) {
            if (valute.CharCode.equals(charCode))
                return valute;
        }

        return null;
    }

    public static ValCurs parse(String xml) throws Exception {
        Reader reader = new StringReader(xml);
        return serializer.read(ValCurs.class, reader, false);
    }
}
