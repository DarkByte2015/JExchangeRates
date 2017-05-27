package com.panov.ilya.jexchangerates.Presenters;

import com.panov.ilya.jexchangerates.Views.Main.IMainView;

import org.joda.time.DateTime;

import java.util.Date;

/**
 * Created by ilya on 23.05.17.
 */

public class MainPresenter {
    IMainView view;

    public MainPresenter(IMainView view) {
        this.view = view;
    }

    public void onRateDateSelected(DateTime date) {
        view.navigateToRateScreen(date);
    }
}
