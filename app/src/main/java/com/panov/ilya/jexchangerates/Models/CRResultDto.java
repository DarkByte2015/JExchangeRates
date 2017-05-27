package com.panov.ilya.jexchangerates.Models;

import java.util.ArrayList;

/**
 * Created by ilya on 26.05.17.
 */

public class CRResultDto {
    public final String message;
    public final ArrayList<Currency> rates;

    public CRResultDto(String message, ArrayList<Currency> rates) {
        this.message = message;
        this.rates = rates;
    }
}
