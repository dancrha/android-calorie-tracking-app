package com.example.androidapplications;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;


public class TestToolbar extends AppCompatActivity {
    final String logTAG = "TestToolbar";
    Snackbar snackbar_email;
    String message = "You selected item 1";
    Snackbar option1_snackbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_toolbar);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar_id);
        setSupportActionBar(myToolbar);

        FloatingActionButton floatingActionBtn = (FloatingActionButton) findViewById(R.id.fab);
        View parentLayout = (View) findViewById(R.id.layout_id);


        floatingActionBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        snackbar_email = Snackbar.make(parentLayout, "Example Snackbar Message!", Snackbar.LENGTH_SHORT);
                        snackbar_email.show();
                    }
                }
        );
    }


    @Override
    public boolean onCreateOptionsMenu (Menu m){
        // Inflate the menu items for use in the Toolbar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, m);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem mi){
        int id = mi.getItemId();
        View parentLayout = (View) findViewById(R.id.layout_id);


        switch(id){
            case R.id.action_one:
                Log.d(logTAG, "Option 1 selected.");
                option1_snackbar = Snackbar.make(parentLayout, message, Snackbar.LENGTH_SHORT);
                option1_snackbar.show();
                break;
            case R.id.action_two:
                Log.d(logTAG, "Option 2 selected.");
                case2();
                break;

            //Toolbar Item 3
            case R.id.action_three:
                Log.d(logTAG, "Option 3 selected.");
                case3(option1_snackbar);
                break;

            //Toolbar item 4
            case R.id.action_about:
                Log.d(logTAG, "Option 4 selected.");
                Toast.makeText(this, "Version 1.0, by Daniel Crha", Toast.LENGTH_LONG).show();
        }
            return true;
    }

    public void case2(){
        AlertDialog.Builder builder = new AlertDialog.Builder(TestToolbar.this);
        builder.setTitle(R.string.dialog_title);
        // Add the buttons
        builder.setPositiveButton(R.string.pos_btn, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
                finish();
            }
        });
        builder.setNegativeButton(R.string.neg_btn, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface builder, int id) {
                // User cancelled the dialog
                builder.dismiss();
            }
        });
        // Create the AlertDialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void case3(Snackbar option1_snackbar){

        android.app.AlertDialog.Builder customDialog =
                new android.app.AlertDialog.Builder(TestToolbar.this);
        // Get the layout inflater
        LayoutInflater inflater = TestToolbar.this.getLayoutInflater();
        final View view = inflater.inflate(R.layout.custom_dialog, null);

        customDialog.setView(view)
                .setPositiveButton(R.string.pos_btn,
                        new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        EditText edit = view.findViewById(R.id.dialog_edittext_id);
                        message = edit.getText().toString();
                        option1_snackbar.setText(message);

                    }
                })
                .setNegativeButton(R.string.neg_btn,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });
        Dialog dialog = customDialog.create();
        dialog.show();
    }
}