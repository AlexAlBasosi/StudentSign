package com.ethandsouza2gmail.javafinalproj;

import android.content.*;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.*;
import android.widget.*;



public class homeScreen extends AppCompatActivity
{

    public FloatingActionButton createRequest;
    public ListView subjectsAvailable;

    public void init()
    {
        createRequest = (FloatingActionButton) findViewById(R.id.addRequest);
        subjectsAvailable = (ListView) findViewById(R.id.subjectsAvailable);
        createRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToCreateScreen = new Intent(homeScreen.this, CreateRequest.class);
                startActivity(goToCreateScreen);
            }
        });
        subjectsAvailable.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent goToExistingRequestScreen = new Intent(homeScreen.this, existingRequest.class);
                goToExistingRequestScreen.putExtra("position",Integer.toString(position));
                startActivity(goToExistingRequestScreen);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        init();
    }

    @Override
    public void onBackPressed()
    {
        final AlertDialog.Builder logOutScreen = new AlertDialog.Builder(this);

        logOutScreen.setMessage("Are you sure you want to log out?");
        logOutScreen.setPositiveButton("Log Out", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                homeScreen.super.onBackPressed();
            }
        });
        logOutScreen.setNegativeButton("Back", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        });
        logOutScreen.show();
    }
}
