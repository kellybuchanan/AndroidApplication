package com.example.androidapp;

import com.example.androidapp.utilities.NetworkUtils;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.os.AsyncTask;

import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;




public class ByYear extends AppCompatActivity {

    private String responseString1;
    private TextView mSearchResultsDisplay;
    private EditText mSearchTermEditText;
    private Button mSearchButton;
    private Button mResetButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.by_year);

        // access the visual elements
        mSearchResultsDisplay   = (TextView) findViewById(R.id.tv_display_text);
        mSearchTermEditText     = (EditText) findViewById(R.id.et_search_box);
        mSearchButton           = (Button) findViewById(R.id.search_button);
        mResetButton            = (Button) findViewById(R.id.reset_button);

        // make network call
        makeNetworkSearchQuery();

        // respond to button clicks
        mResetButton.setOnClickListener(new View.OnClickListener() {
            //inner method
            public void onClick(View v) {

                // make network call
                makeNetworkSearchQuery();

            }
        });

        // responding to search button
        mSearchButton.setOnClickListener(
                new View.OnClickListener(){ // a unnamed object
                    //inner method def
                    public void onClick(View v){
                        //get search string from user
                        String searchText = mSearchTermEditText.getText().toString();

                        // get text from mSearchResultsDisplayText
                        String years = mSearchResultsDisplay.getText().toString();
                        // convert to a list
                        String[] yearsList = years.split("\n");

                        // search in that list to check if search string matches
                        for(String year : yearsList){
                            if(year.toLowerCase().equals(searchText.toLowerCase())){
                                mSearchResultsDisplay.setText(year + "\n\n");
                                ArrayList<String> dateInfo =  NetworkUtils.parseByDatesJSON(responseString1, year);
                                Log.d("DATA", String.valueOf(dateInfo));
                                String data = "";
                                for(int i = 0; i < dateInfo.size() ; i++) {
                                    data = dateInfo.get(i);
                                    Log.d("DATA", data + " " + i + "\n");
                                    mSearchResultsDisplay.append(data + "\n\n");
                                }

                                break;
                            }else{
                                mSearchResultsDisplay.setText("No results match.");
                            }
                        }
                    } // end of onClick method
                } // end of View.OnClickListener
        ); // end of setOnClickListener



    } // end of on create

    public void makeNetworkSearchQuery() {
        // get the search string
        String searchTerm = mSearchTermEditText.getText().toString();

        // reset search results
        mSearchResultsDisplay.setText("Years: \n");

        // make the search
        new FetchNetworkData().execute(searchTerm);
    } // end of makeNetworkSearchQuery

    // inner class

    public class FetchNetworkData extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            // get search term
            if (params.length == 0) {
                return null;
            }
            String searchTerm = params[0];

            //get url
            URL searchUrl = NetworkUtils.buildPopulationUrl();

            //get response from url
            String responseString = null;
            try {
                responseString = NetworkUtils.getResponseFromUrl(searchUrl);
            } catch (Exception e) {
                e.printStackTrace();
            }

            // return response
            responseString1 = responseString;
            return responseString;

        } // end of doinBackground

        @Override
        protected void onPostExecute(String responseData) {
            super.onPostExecute(responseData);
            ArrayList<String> years = NetworkUtils.parseDatesJSON(responseData); //
            // display entries in GUI
            for(String y: years){
                mSearchResultsDisplay.append("\n\n" + y);
            }


        } // end of onPostExecute

    } // end of inner class



    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu, menu); // id of the menu resource that should be inflated
        return true;
    } // end of onCreateOptionsMenu

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int menuItemSelected = item.getItemId();

        if(menuItemSelected == R.id.menu_boro){ // id from main_menu.xml for the About item


            //spl - launching activity in our app - then launch the About Activity
            Class destinationActivity = MainActivity.class;

            // create intent to go to next page
            Intent startAboutActivityIntent = new Intent(ByYear.this, destinationActivity);

            startActivity(startAboutActivityIntent);
            Log.d("info", "ByYear launched");
        } else if (menuItemSelected == R.id.menu_resources) {

            //spl - launching activity in our app - then launch the Resource Activity
            Class destinationActivity = MoreInfo.class;

            // create intent to go to next page
            Intent startAboutActivityIntent = new Intent(ByYear.this, destinationActivity);

            startActivity(startAboutActivityIntent);
            Log.d("info", "MoreInfo Activity launched");

        } // end of menu options
        return true;
    } // end of onOptions

} // end of ByYear