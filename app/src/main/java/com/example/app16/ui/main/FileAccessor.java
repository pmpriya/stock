package com.example.app16.ui.main;

import android.content.Context;
import android.app.Activity;
import android.os.Bundle;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;

import android.widget.Toast;



import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.File;

public class FileAccessor {
    Context myContext;

    public FileAccessor(Context context) {
        myContext = context;
    }

    public void createFile(String filename) {
        try {
            File newFile = new File(myContext.getFilesDir(), filename);
            FileOutputStream outputStream;
            outputStream = myContext.openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.close();
        } catch (Exception _e) {
            _e.printStackTrace();
            System.out.println("File failed");

        }
    }

    public ArrayList<Double> readJsonPrice(String fileName) {
        JSONParser parser = new JSONParser();
        ArrayList<Double> prices = new ArrayList<>();
        try {
            File file = new File(myContext.getFilesDir(), fileName);
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            StringBuilder stringBuilder = new StringBuilder();
            String line = bufferedReader.readLine();
            while (line != null) {
                stringBuilder.append(line).append("\n");
                line = bufferedReader.readLine();
            }
            bufferedReader.close();
            String responce = stringBuilder.toString();
            Object obj = parser.parse((responce));
            System.out.println("Printing before reading : " + responce);
            JSONObject jsonObject = (JSONObject) obj;
            JSONArray jsonArray = (JSONArray) jsonObject.get("prices");
            Iterator<Double> iterator = jsonArray.iterator();
            while (iterator.hasNext()) {
                prices.add(iterator.next());
            }
        } catch (ParseException | FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return prices;

    }

    public ArrayList<String> readJsonDate(String fileName) {
        JSONParser parser = new JSONParser();
        ArrayList<String> date = new ArrayList<>();
        try {
            File file = new File(myContext.getFilesDir(), fileName);
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            StringBuilder stringBuilder = new StringBuilder();
            String line = bufferedReader.readLine();
            while (line != null) {
                stringBuilder.append(line).append("\n");
                line = bufferedReader.readLine();
            }
            bufferedReader.close();
            String response = stringBuilder.toString();
            Object obj = parser.parse((response));
            System.out.println("Printing before reading : " + response);
            JSONObject jsonObject = (JSONObject) obj;
            JSONArray jsonArray = (JSONArray) jsonObject.get("date");
            Iterator<String> iterator = jsonArray.iterator();
            while (iterator.hasNext()) {
                date.add(iterator.next());
            }
        } catch (ParseException | FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return date;

    }

    public void writeFile(String filename, ArrayList<String> datesIn, ArrayList<Double> values) {
        JSONObject obj = new JSONObject();
        JSONArray dates = new JSONArray();
        for (int i = 0; i < datesIn.size(); i++) {
            dates.add(datesIn.get(i));
        }
        JSONArray prices = new JSONArray();
        for (int i = 0; i < values.size(); i++) {
            prices.add(values.get(i));
        }

        try {
            obj.put("name", filename);
            obj.put("date", dates);
            obj.put("prices", prices);
            FileOutputStream outputStream;
            outputStream = myContext.openFileOutput(filename, Context.MODE_PRIVATE);
            System.out.println("printing fileData before saving  : " + obj.toString());
            outputStream.write(obj.toString().getBytes());
            outputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
