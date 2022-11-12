package com.example.androidapplications;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class WeatherForecastActivity extends Activity {
    private final String ACTIVITY_NAME = "WeatherForecastActivity";

    ProgressBar progressBar;

    ImageView imageView;
    TextView current_temp;
    TextView min_temp;
    TextView max_temp;
    TextView wind_speed;
    List <String> cityList;
    TextView cityName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);
        setTitle("Weather Network Information");

        current_temp = findViewById(R.id.wf_currtemp_tv);
        min_temp = findViewById(R.id.wf_mintemp_tv);
        max_temp = findViewById(R.id.wf_maxtemp_tv);
        imageView = findViewById(R.id.wf_imgview_id);
        cityName = findViewById(R.id.cityName);

        progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);
        get_a_city();
    }

    public void get_a_city() {

        // Get the list of cities.
        cityList = Arrays.asList(getResources().getStringArray(R.array.cities));

        // Make a handler for the city list.
        final Spinner citySpinner = findViewById(R.id.citySpinner);
        ArrayAdapter <CharSequence> adapter =
                ArrayAdapter.createFromResource(
                        this, R.array.cities, android.R.layout.simple_spinner_dropdown_item);
        //  adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //  android.R.layout.simple_spinner_item
        citySpinner.setAdapter(adapter);
        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView <?> adapterView, View view, int i, long l) {
                new ForecastQuery(cityList.get(i)).execute();
                cityName.setText(cityList.get(i) + " Weather");
            }

            @Override
            public void onNothingSelected(AdapterView <?> adapterView) {

            }
        });


    }

    private class ForecastQuery extends AsyncTask <String, Integer, String> {

        private String windSpeed;
        private String currentTemp;
        private String minTemp;
        private String maxTemp;
        private Bitmap picture;
        protected String city;

        ForecastQuery(String city) {
            this.city = city;
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url = new URL(
                        "https://api.openweathermap.org/" +
                                "data/2.5/weather?q=" + this.city + "," +
                                "ca&APPID=79cecf493cb6e52d25bb7b7050ff723c&" +
                                "mode=xml&units=metric");

                HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.connect();

                InputStream in = conn.getInputStream();

                try {
                    XmlPullParser parser = Xml.newPullParser();
                    parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                    parser.setInput(in, null);
                    int type;
                    //While you're not at the end of the document:
                    while ((type = parser.getEventType()) != XmlPullParser.END_DOCUMENT) {
                        //Are you currently at a Start Tag?
                        if (parser.getEventType() == XmlPullParser.START_TAG) {
                            if (parser.getName().equals("temperature")) {
                                currentTemp = parser.getAttributeValue(null, "value");
                                publishProgress(25);
                                minTemp = parser.getAttributeValue(null, "min");
                                publishProgress(50);
                                maxTemp = parser.getAttributeValue(null, "max");
                                publishProgress(75);
                            } else if (parser.getName().equals("weather")) {
                                String iconName = parser.getAttributeValue(null, "icon");
                                String fileName = iconName + ".png";

                                Log.i(ACTIVITY_NAME, "Looking for file: " + fileName);
                                if (fileExistance(fileName)) {
                                    FileInputStream fis = null;
                                    try {
                                        fis = openFileInput(fileName);

                                    } catch (FileNotFoundException e) {
                                        e.printStackTrace();
                                    }
                                    Log.i(ACTIVITY_NAME, "Found the file locally");
                                    picture = BitmapFactory.decodeStream(fis);
                                } else {
                                    String iconUrl = "https://openweathermap.org/img/w/" + fileName;
                                    picture = getImage(new URL(iconUrl));
                                    FileOutputStream outputStream = openFileOutput(fileName, Context.MODE_PRIVATE);
                                    picture.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                                    Log.i(ACTIVITY_NAME, "Downloaded the file from the Internet");
                                    outputStream.flush();
                                    outputStream.close();
                                }
                                publishProgress(100);
                            } else if (parser.getName().equals("wind")) {
                                parser.nextTag();
                                if (parser.getName().equals("speed")) {
                                    windSpeed = parser.getAttributeValue(null, "value");
                                }
                            }
                        }
                        // Go to the next XML event
                        parser.next();
                    }
                } finally {
                    in.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            return "";

        }

        public boolean fileExistance(String fname) {
            File file = getFileStreamPath(fname);
            return file.exists();
        }

        public Bitmap getImage(URL url) {
            HttpsURLConnection connection = null;
            try {
                connection = (HttpsURLConnection) url.openConnection();
                connection.connect();
                int responseCode = connection.getResponseCode();
                if (responseCode == 200) {
                    return BitmapFactory.decodeStream(connection.getInputStream());
                } else
                    return null;
            } catch (Exception e) {
                return null;
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
        }

        @Override
        protected void onPostExecute(String a) {
            progressBar.setVisibility(View.INVISIBLE);
            imageView.setImageBitmap(picture);
            current_temp.setText(currentTemp + "C\u00b0");
            min_temp.setText(minTemp + "C\u00b0");
            max_temp.setText(maxTemp + "C\u00b0");
            wind_speed.setText(windSpeed + "km/h");
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBar.setProgress(values[0]);
        }
    }
}



