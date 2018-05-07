package com.ethandsouza2gmail.javafinalproj;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

public class existingRequest extends AppCompatActivity {

    TextView subjectName;
    TextView progressNum;
    Button endorse;
    Button submitRequest;
    ProgressBar progressBar;

    String subjectList[];

    public void init()
    {
        subjectName = (TextView) findViewById(R.id.subjectName);
        progressNum = (TextView) findViewById(R.id.progressNum);
        endorse = (Button) findViewById(R.id.endorse);
        submitRequest = (Button) findViewById(R.id.submitRequest);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        subjectList = getResources().getStringArray(R.array.subject_list);
        int position = Integer.parseInt(getIntent().getExtras().getString("position"));
        subjectName.setText(subjectList[position]);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_existing_request);
        init();

        endorse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endorse.setEnabled(false);
                //add transaction
            }
        });
    }
}
