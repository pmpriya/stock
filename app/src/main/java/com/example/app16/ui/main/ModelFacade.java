package com.example.app16.ui.main;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModelFacade
        implements InternetCallback {
    FileAccessor fileSystem;
    Context myContext;
    static ModelFacade instance = null;
    private String symbol;
    private String symbol2;
    private int index = 0;

    public static ModelFacade getInstance(Context context) {
        if (instance == null) {
            instance = new ModelFacade(context);
        }
        return instance;
    }


    private ModelFacade(Context context) {
        myContext = context;
        fileSystem = new FileAccessor(context);
    }

    public void internetAccessCompleted(String response) {
        ArrayList<DailyQuote> dailyQuotes = DailyQuote_DAO.makeFromCSV(response);
        String fileName = "" ;
        if(index==1) {
             fileName = symbol + ".json";
        }
        else {
            fileName = symbol2 + ".json";
        }
        fileSystem.createFile(fileName);
        ArrayList<String> xnames = null;
        xnames = Ocl.copySequence(Ocl.collectSequence(dailyQuotes, (q) -> {
            return q.date;
        }));
        ArrayList<Double> yvalues = null;
        yvalues = Ocl.copySequence(Ocl.collectSequence(dailyQuotes, (q) -> {
            return q.close;
        }));
        fileSystem.writeFile(fileName,xnames,yvalues);
    }

    public String findQuote(String dateFrom, String dateTo, String newSymbol, int id) {
        String result = "";
        if (DailyQuote_DAO.isCached(dateFrom)) {
            result = "Data already exists";
            return result;
        } else {
            {
            }
        }
        long t1 = 0;
        t1 = DateComponent.getEpochSeconds(dateFrom);
        long t2 = 0;
        t2 = DateComponent.getEpochSeconds(dateTo);
        String url = "";
        ArrayList<String> sq1 = null;
        sq1 = Ocl.copySequence(Ocl.initialiseSequence("period1", "period2", "interval", "events"));
        ArrayList<String> sq2 = null;
        sq2 = Ocl.copySequence(Ocl.initialiseSequence((t1 + ""), (t2 + ""), "1d", "history"));
        url = DailyQuote_DAO.getURL(newSymbol, sq1, sq2);
        InternetAccessor x = null;
        x = new InternetAccessor();
        x.setDelegate(this);
        x.execute(url);
        result = ("Called url: " + url);
        if(id==1){
            symbol=newSymbol;
            index = 1;
        }else{
            symbol2=newSymbol;
            index = 2;
        }

        return result;
    }

    public GraphDisplay analyse() {
        GraphDisplay result = null;
        result = new GraphDisplay();
        ArrayList<String>xnames = fileSystem.readJsonDate(symbol+".json");
        result.setXNominal(xnames);
        ArrayList<Double>yvalues = fileSystem.readJsonPrice(symbol+".json");
        ArrayList<Double>zvalues = fileSystem.readJsonPrice(symbol2+".json");
        result.setXNominal(xnames);
        result.setYPoints(yvalues);
        result.setZPoints(zvalues);

        return result;
    }

}