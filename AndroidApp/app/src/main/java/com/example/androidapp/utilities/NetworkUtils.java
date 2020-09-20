package com.example.androidapp.utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class NetworkUtils {

    // this method is specific to the countries URL
    public static URL buildPopulationUrl(){
        // get string url
        String populationUrl = "https://data.cityofnewyork.us/resource/xywu-7bv9.json";
        URL popURL = null;
        try{
            popURL = new URL(populationUrl);
        }catch(MalformedURLException e){
            e.printStackTrace();
        }
        return popURL;
    } // end of buildPopulationUrl

    // this method can be used with any URL object
    public static String getResponseFromUrl(URL url) throws IOException{
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection(); // getting the connection open
        try{
            InputStream in = urlConnection.getInputStream();
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A"); // delimiter for end of message
            boolean hasInput = scanner.hasNext();
            if(hasInput) return scanner.next(); // success
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            urlConnection.disconnect();
        }
        return null;
    } // end of get Resp


    // Parse JSON to get a list of Boroughs
    public static ArrayList<String> parseBoroughsJSON(String popResponseString){
        ArrayList<String> popList = new ArrayList<String>();
        try{
            JSONObject allPopulationObject = new JSONObject(popResponseString);
            JSONArray allPopulationArray = allPopulationObject.getJSONArray("results");

            for(int i = 0; i < allPopulationArray.length(); i++){
                JSONObject childJson = allPopulationArray.getJSONObject(i);
                // check if it has name
                if(childJson.has("borough")){
                    String borough = childJson.getString("borough");
                    if(borough != null) popList.add(borough);
                }
            } // end for
        } catch(JSONException e){
            e.printStackTrace();
        }
        return popList;
    } // end of parse


    // Parse JSON to get list of populations by a year
    public static ArrayList<String> parseByDatesJSON(String popResponseString, String year){
        ArrayList<String> popByDate = new ArrayList<String>();
        try{
            JSONObject allPopulationObject = new JSONObject(popResponseString);
            JSONArray allPopulationArray = allPopulationObject.getJSONArray("results");
            for(int i = 0; i < allPopulationArray.length(); i++){
                JSONObject childJson = allPopulationArray.getJSONObject(i);
                // check if it has name
                if(childJson.has("_" + year)){
                    String pop = childJson.getString("_" + year);
                    String borough = childJson.getString("borough");
                    String percent = childJson.getString("_" + year + "_boro_share_of_nyc_total");
                    if (pop != null) popByDate.add(borough + ": " + pop + " people (" + percent + "%)");
                }
            } // end for
        } catch(JSONException e){
            e.printStackTrace();
        }
        return popByDate;
    } // end of parse


}

