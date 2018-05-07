package com.ethandsouza2gmail.javafinalproj;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class registrarScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_screen);
    }

    @Override
    public void onBackPressed()
    {
        final AlertDialog.Builder logOutScreen = new AlertDialog.Builder(this);

        logOutScreen.setMessage("Are you sure you want to log out?");
        logOutScreen.setPositiveButton("Log Out", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                registrarScreen.super.onBackPressed();
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
