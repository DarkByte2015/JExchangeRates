package com.panov.ilya.jexchangerates;

import android.content.Context;
import android.content.res.Resources;

import com.panov.ilya.jexchangerates.Models.CRResultStatus;
import com.panov.ilya.jexchangerates.Models.CRService;
import com.panov.ilya.jexchangerates.Models.CRXmlParser;
import com.panov.ilya.jexchangerates.Models.CurrencyRates;

import net.danlew.android.joda.JodaTimeAndroid;

import org.apache.commons.io.FileUtils;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by ilya on 24.05.17.
 */

public class CRTest {
    private static final String ASSETS_PATH = "app/src/test/java/com/panov/ilya/jexchangerates/assets/";
    private static final double DELTA = 1e-15;

    private String xml;

    private String readAssetsFile(String path, String encoding) throws IOException {
        String finalPath = ASSETS_PATH + path;
        File file = new File(finalPath);
        return FileUtils.readFileToString(file, encoding);
    }

    private String getXml(DateTime date) {
        CRService service = new CRService(date);
        service.request();
        return service.getResult();
    }

    private DateTime date(int day, int month, int year) {
        return new DateTime(year, month, day, 0, 0);
    }

    @Before
    public void setUp() throws IOException {
        Context context = mock(Context.class);
        Context appContext = mock(Context.class);
        Resources resources = mock(Resources.class);
        when(resources.openRawResource(anyInt())).thenReturn(mock(InputStream.class));
        when(appContext.getResources()).thenReturn(resources);
        when(context.getApplicationContext()).thenReturn(appContext);
        JodaTimeAndroid.init(context);

        xml = readAssetsFile("rates.xml", "windows-1251");
    }

    @Test
    public void xml_test() {
        assertNotNull(xml);
        assertTrue(xml.length() > 0);
    }

    @Test
    public void service_test() {
        DateTime date = date(1, 3, 2017);
        CRService service = new CRService(date);
        service.request();

        assertEquals(date, service.getDate());
        assertEquals("http://www.cbr.ru/scripts/XML_daily.asp?date_req=01/03/2017", service.getRequestUrl());
        assertEquals(CRResultStatus.SUCCESS, service.getStatus());
        assertEquals(xml, service.getResult());
    }

    @Test
    public void parser_success_test() {
        DateTime date = date(1, 3, 2017);
        String xml = getXml(date);
        CRXmlParser parser = new CRXmlParser(xml);
        parser.parse();

        assertEquals(xml, parser.getXml());
        assertEquals(CRResultStatus.SUCCESS, parser.getStatus());

        CurrencyRates result = parser.getResult();

        assertNotNull(result);
        assertEquals(date, result.date);
        assertFalse(result.rates.isEmpty());
        assertEquals(57.9627, result.getCurrencyByCharCode("USD").value, DELTA);
        assertEquals(61.3883, result.getCurrencyByCharCode("EUR").value, DELTA);
    }

    @Test
    public void parser_params_error_test() {
        DateTime date = date(1, 3, 999);
        String xml = getXml(date);
        CRXmlParser parser = new CRXmlParser(xml);
        parser.parse();

        assertEquals(xml, parser.getXml());
        assertEquals(CRResultStatus.PARAMS_ERROR, parser.getStatus());

        CurrencyRates result = parser.getResult();

        assertNull(result);
    }
}
