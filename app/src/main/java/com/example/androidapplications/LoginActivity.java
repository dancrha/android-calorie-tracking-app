package com.example.androidapplications;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;
import android.util.Patterns;
import android.text.TextUtils;

import com.google.android.material.snackbar.Snackbar;

public class LoginActivity extends AppCompatActivity {
    final String logTAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.i(logTAG, "In onCreate()");

        //Shared Preferences
        EditText emailEditText = (EditText) findViewById(R.id.email_edittext);
        SharedPreferences sharedpreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        emailEditText.setText(sharedpreferences.getString("DefaultEmail", "email@domain.com"));
        Log.i(logTAG, "Setting email: "+ emailEditText);

        //Login Button
        Button b1 = (Button)findViewById(R.id.loginbtn);
        b1.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String emailinput = emailEditText.getText().toString();

                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString("DefaultEmail", emailinput);
                        editor.commit();


                        if (!emailinput.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(emailinput).matches()) {
                            Toast.makeText(LoginActivity.this, R.string.goodEmail, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);

                        } else {
                            Toast.makeText(LoginActivity.this, R.string.badEmail, Toast.LENGTH_LONG).show();
                        }

                        }
                }
        );

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
        Toast.makeText(LoginActivity.this, s, Toast.LENGTH_LONG).show();
    }
}