package com.panov.ilya.jexchangerates.Models;

import org.joda.time.DateTime;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Date;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ilya on 24.05.17.
 */

public class CRService {
    public static final String CR_URL = "http://www.cbr.ru/scripts/XML_daily.asp?date_req=";

    private DateTime date;
    private String requestUrl;
    private CRResultStatus status = CRResultStatus.NOT_COMPLETED;
    private String result;

    public CRService(DateTime date) {
        this.date = date;
        this.requestUrl = CR_URL + date.toString("dd/MM/yyyy");
    }

    public DateTime getDate() {
        return date;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public CRResultStatus getStatus() {
        return status;
    }

    public String getResult() {
        return result;
    }

    public void request() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(requestUrl).build();

        try (Response response = client.newCall(request).execute()) {
            result = new String(response.body().bytes(), "windows-1251");
            status = CRResultStatus.SUCCESS;
        }
        catch (Exception e) {
            e.printStackTrace();
            status = CRResultStatus.SERVER_ERROR;
        }
    }
}
