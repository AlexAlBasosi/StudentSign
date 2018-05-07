package com.ethandsouza2gmail.javafinalproj;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.*;

public class CreateRequest extends AppCompatActivity {

    Spinner availableSubjects;

    public void init()
    {
        availableSubjects = (Spinner) findViewById(R.id.availableSubjects);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_request);
        init();


    }
}
