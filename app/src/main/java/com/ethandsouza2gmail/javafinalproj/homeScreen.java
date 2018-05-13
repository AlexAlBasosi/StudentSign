package com.ethandsouza2gmail.javafinalproj;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class homeScreen extends AppCompatActivity
{
    public FloatingActionButton createRequest;
    public ListView subjectsAvailable;
    List<String> subjectList;
    List<Subject> subjectArr;


    public void init()
    {
        RequestQueue queue = Volley.newRequestQueue(this);

        final String url = "http://10.0.2.2:3000/api/Subject";

        JsonArrayRequest getRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>()
                {
                    @Override
                    public void onResponse(JSONArray response) {
                        // display response
//                        try {
//                            Toast.makeText(getApplicationContext(), response.getJSONObject(0).toString(),
//                                    Toast.LENGTH_SHORT).show();
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }

                        subjectArr = new ArrayList<Subject>(response.length());
                        subjectList = new ArrayList<String>(response.length());

                        for (int i = 0; i < response.length(); i++)
                        {
                            try {
                                subjectArr.add(new Subject(response.getJSONObject(i)));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        for (int i = 0; i < response.length(); i++)
                        {
                            subjectList.add(subjectArr.get(i).getCode());
                        }

                        subjectsAvailable.setAdapter(new ArrayAdapter<String>(homeScreen.this, android.R.layout.simple_list_item_1, subjectList));
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "error",
                                Toast.LENGTH_SHORT).show();
                        Log.d("Error", error.toString());
                    }
                }
        );

        queue.add(getRequest);

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
                goToExistingRequestScreen.putExtra("code", subjectList.get(position));
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
