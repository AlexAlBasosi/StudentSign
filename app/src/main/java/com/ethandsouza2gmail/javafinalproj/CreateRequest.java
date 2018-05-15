package com.ethandsouza2gmail.javafinalproj;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

public class CreateRequest extends AppCompatActivity {

    Spinner availableSubjects;
    Button submitRequest;
    RequestQueue queue;
    boolean ifExists;
    String code;
    String name;

    public void init()
    {
        availableSubjects = (Spinner) findViewById(R.id.availableSubjects);
        submitRequest = (Button) findViewById(R.id.submitRequest);

        submitRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (availableSubjects.getSelectedItemPosition() == 0)
                {
                    Toast.makeText(CreateRequest.this, "Please select a subject", Toast.LENGTH_SHORT).show();
                }
                else {
                    int i = availableSubjects.getSelectedItemPosition();
                    String subjects[] = getResources().getStringArray(R.array.subjects);
                    String s = subjects[i];
                    code = s.substring(0, 4) + s.substring(5, 8);
                    name = s.substring(10);

                    System.out.println(code);
                    System.out.println(name);

                    queue = Volley.newRequestQueue(CreateRequest.this);

                    final String url = "http://10.0.2.2:3000/api/Subject";

                    JsonArrayRequest getRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                            new Response.Listener<JSONArray>()
                            {
                                @Override
                                public void onResponse(JSONArray response) {
                                    // display response

                                    for (int i = 0; i < response.length(); i++) {
                                        try {
                                            if (code.equals(response.getJSONObject(i).getString("subjectCode"))){
                                                ifExists = true;
                                            } else {
                                                ifExists = false;
                                            }

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    if (ifExists) {
                                        Toast.makeText(CreateRequest.this, "Subject request already exists!", Toast.LENGTH_SHORT).show();
                                    } else {
                                        queue = Volley.newRequestQueue(CreateRequest.this);

                                        String url = "http://10.0.2.2:3000/api/Subject";

                                        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                                                new Response.Listener<String>() {
                                                    @Override
                                                    public void onResponse(String response) {
                                                        // response
                                                        Log.d("Response", response);

                                                    }
                                                },
                                                new Response.ErrorListener() {
                                                    @Override
                                                    public void onErrorResponse(VolleyError error) {
                                                        // error
                                                        Log.d("Error.Response", error.toString());
                                                    }
                                                }
                                        ) {
                                            @Override
                                            protected Map<String, String> getParams () {
                                                Map<String, String> params = new HashMap<String, String>();
                                                params.put("$class", "org.acme.javaproject.Subject");
                                                params.put("subjectCode", code);
                                                params.put("subjectName", name);
                                                params.put("actualNumberOfSignatures", "1");
                                                params.put("expectedNumberOfSignatures", "20");

                                                return params;
                                            }
                                        };

                                        queue.add(postRequest);

                                    }
                                }
                            },
                            new Response.ErrorListener()
                            {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.d("Error.Response", "Error!");
                            }
                    });

                    queue.add(getRequest);

                    try {
                        Thread.sleep(4000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    Intent goToExistingRequestScreen = new Intent(CreateRequest.this, existingRequest.class);
                    goToExistingRequestScreen.putExtra("code",code);
//                    goToExistingRequestScreen.putExtra("name",name);
                    startActivity(goToExistingRequestScreen);}
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_request);
        init();
    }
}
