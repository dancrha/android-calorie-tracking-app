package com.example.androidapplications;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.ImageButton;

import com.google.android.material.snackbar.Snackbar;

public class ListItemsActivity extends AppCompatActivity {


    final String logTAG = "ListItemsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_items);
        Log.i(logTAG, "In onCreate()");

        ImageButton b1 = (ImageButton)findViewById(R.id.imgbtn);
        b1.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                                startActivityForResult(takePictureIntent, 10);
                            }
                        }
                }
        );

        Switch swtch = (Switch) findViewById(R.id.switch1);
        swtch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b == true){
                    Toast.makeText(ListItemsActivity.this, "Switch is On", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(ListItemsActivity.this, "Switch is Off", Toast.LENGTH_LONG).show();
                }
            }
            }
        );

        CheckBox checkbox = (CheckBox) findViewById(R.id.checkbox);
        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean c) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ListItemsActivity.this);

                builder.setMessage(R.string.dialogmsg)
                        .setTitle(R.string.dialogmsg)
                        .setPositiveButton(R.string.okbtn, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface builder, int id) {
                                //OK button
                                Intent resultIntent = new Intent(ListItemsActivity.this, MainActivity.class);
                                resultIntent.putExtra("Response", getString(R.string.messagePassed));
                                setResult(ListItemsActivity.RESULT_OK, resultIntent);
                                finish();
                            }
                        })
                        .setNegativeButton(R.string.cancelbtn, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface builder, int id) {
                                //Cancel button
                                builder.dismiss();
                            }
                        })
                        .show();
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ImageButton btnImg = findViewById(R.id.imgbtn);
            btnImg.setImageBitmap(imageBitmap);
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
        Toast.makeText(ListItemsActivity.this, s, Toast.LENGTH_LONG).show();
    }
}