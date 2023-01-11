package com.example.app16.ui.main;

import java.util.ArrayList;

import java.util.List;

import android.content.Context;

public class findQuoteBean {
    ModelFacade model = null;

    private String dateFrom = "";
    private String dateTo = "";
    private List errors = new ArrayList();

    public findQuoteBean(Context _c) {
        model = ModelFacade.getInstance(_c);
    }

    public void setdate(String newDateFrom, String newDateTo) {
        dateFrom = newDateFrom;
        dateTo = newDateTo;


    }



    public void resetData() {
        dateFrom = "";
        dateTo = "";
    }

    public boolean isfindQuoteerror() {
        errors.clear();
        return errors.size() > 0;
    }

    public String errors() {
        return errors.toString();
    }

    public String findQuote(String symbol, int id) {

        return model.findQuote(dateFrom, dateTo, symbol, id);
    }

}

