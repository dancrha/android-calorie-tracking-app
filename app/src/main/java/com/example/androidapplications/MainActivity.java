package com.example.androidapplications;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ImageButton;
import com.google.android.material.snackbar.Snackbar;


import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    final String logTAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(logTAG, "In onCreate()");

        Button b1 = (Button)findViewById(R.id.activity_main_button);

        b1.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                       Intent mainActivityIntent = new Intent(MainActivity.this, ListItemsActivity.class);

                       startActivityForResult(mainActivityIntent, 10);


                    }
                }
        );

        Button startchat_button = (Button) findViewById(R.id.startchatbutton);

        startchat_button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.i(logTAG, "User clicked Start Chat");
                        Intent ChatWindowIntent = new Intent(MainActivity.this, ChatWindow.class);
                        startActivity(ChatWindowIntent);
                    }
                }
        );
        }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        String ACTIVITY_NAME = "MainActivity";
        if (requestCode == 10){
            Log.i(logTAG, "Returned to MainActivity.onActivityResult");
        }
        if (resultCode == ListItemsActivity.RESULT_OK){
            String messagePassed = data.getStringExtra("Response");
            Toast.makeText(MainActivity.this, messagePassed, Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.i(logTAG, "In onResume()");
    }

    @Override
    protected void onStart(){
        super.onStart();
        Log.i(logTAG, "In onStart()");
    }

    @Override
    protected void onPause(){
        super.onPause();
        Log.i(logTAG, "In onPause()");
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.i(logTAG, "In onStop()");
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.i(logTAG, "In onDestroy()");
    }

    public void print(String s){

        Snackbar.make(findViewById(R.id.coordinator_layout), s, Snackbar.LENGTH_LONG).show();
        Toast.makeText(MainActivity.this, s, Toast.LENGTH_LONG).show();
    }
}