package com.example.androidapp;


import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.util.Log;


public class MoreInfo extends AppCompatActivity {

    private TextView mDisplayAboutTextView;
    private Button mOpenWebpageButton;
    private Button mOpenMapButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.more_info);

        // connect with UI elements
        mDisplayAboutTextView = (TextView) findViewById(R.id.tv_about_text);
        mOpenWebpageButton = (Button) findViewById(R.id.button_open_webpage);
        mOpenMapButton = (Button) findViewById(R.id.button_open_map);

        // get string message and display here
        mDisplayAboutTextView.setText("More Info!");
        String message = "hello";
        Intent intentThatStartedActivity = getIntent();

        // check if extra message is there
        if(intentThatStartedActivity.hasExtra(Intent.EXTRA_TEXT)) {
            message = intentThatStartedActivity.getStringExtra(Intent.EXTRA_TEXT);
            mDisplayAboutTextView.append("\n\n" + message);
        }

        final String urlString = "https://www.nd.edu/"; // url string

        // open webpage button
        mOpenWebpageButton.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        openWebPage(urlString);
                    } // end of onClick method

                } // end of View
        ); // end of setOnClickListener

        // open map button
        mOpenMapButton.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        openMap();
                    } // end of onClick
                } // end of View
        ); // end of setOnCli


    } // end of onCreate method


    public void openMap(){
        Log.d("info", "special string");

        String addressString = "New York City, NY";
        Uri addressUri = Uri.parse("geo:0,0").buildUpon().appendQueryParameter("q", addressString).build();
        Intent openMapIntent = new Intent(Intent.ACTION_VIEW);
        openMapIntent.setData(addressUri);

        startActivity(openMapIntent);
    } // end of open map

    public void openWebPage(String urlString){
        Log.d("info", urlString);

        Uri webpage = Uri.parse(urlString);

        Intent openWebPageIntent = new Intent(Intent.ACTION_VIEW, webpage);

        startActivity(openWebPageIntent);
    } // end of open web page

} // end of class MoreInfoActivity
