package com.ethandsouza2gmail.javafinalproj;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class existingRequest extends AppCompatActivity {

    TextView subjectName;
    TextView progressNum;
    Button endorse;
    Button submitRequest;
    ProgressBar progressBar;
    RequestQueue queue;
    String code;

    String subjectList[];

    public void init()
    {
        subjectName = (TextView) findViewById(R.id.subjectName);
        progressNum = (TextView) findViewById(R.id.progressNum);
        endorse = (Button) findViewById(R.id.endorse);
        submitRequest = (Button) findViewById(R.id.submitRequest);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        code = getIntent().getExtras().getString("code");


        updateValues(code);
//        queue = Volley.newRequestQueue(this);


//        final String url = "http://10.0.2.2:3000/api/Subject/" + code;
//
//        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
//                new Response.Listener<JSONObject>()
//                {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        // display response
//
//                        try {
//                            subjectName.setText(code + ": " + response.getString("subjectName"));
//                            updateValues(response);
////                            progressNum.setText(response.getInt("actualNumberOfSignatures") + "/" + response.getInt("expectedNumberOfSignatures"));
////                            progressBar.setMax(response.getInt("expectedNumberOfSignatures"));
////                            progressBar.setProgress(response.getInt("actualNumberOfSignatures"));
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//                },
//                new Response.ErrorListener()
//                {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(getApplicationContext(), "error",
//                                Toast.LENGTH_SHORT).show();
//                        Log.d("Error", error.toString());
//                    }
//                }
//        );
//
//        queue.add(getRequest);
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

                String url = "http://10.0.2.2:3000/api/Sign";

                StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>()
                        {
                            @Override
                            public void onResponse(String response) {
                                // response
                                Log.d("Response", response);

//                                progressNum.setText(response.getInt("actualNumberOfSignatures") + "/" + response.getInt("expectedNumberOfSignatures"));
//                                progressBar.setMax(response.getInt("expectedNumberOfSignatures"));
//                                progressBar.setProgress(response.getInt("actualNumberOfSignatures"));
                            }
                        },
                        new Response.ErrorListener()
                        {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // error
                                Log.d("Error.Response", error.toString());
                            }
                        }
                ) {
                    @Override
                    protected Map<String, String> getParams()
                    {
                        Map<String, String>  params = new HashMap<String, String>();
                        params.put("$class", "org.acme.javaproject.Sign");
                        params.put("subject", code);

                        return params;
                    }
                };

                queue.add(postRequest);
                updateValues(code);
                //add transaction
            }
        });
        submitRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitRequest.setEnabled(false);

                String url = "http://10.0.2.2:3000/api/ProposeANewSubject";

                StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>()
                        {
                            @Override
                            public void onResponse(String response) {
                                // response
                                Log.d("Response", response);
                            }
                        },
                        new Response.ErrorListener()
                        {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // error
                                Log.d("Error.Response", error.toString());
                            }
                        }
                ) {
                    @Override
                    protected Map<String, String> getParams()
                    {
                        Map<String, String>  params = new HashMap<String, String>();
                        params.put("$class", "org.acme.javaproject.ProposeANewSubject");
                        params.put("subject", code);

                        return params;
                    }
                };

                queue.add(postRequest);
            }
        });
    }

    public void updateValues(final String code)
    {

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        queue = Volley.newRequestQueue(this);


        final String url = "http://10.0.2.2:3000/api/Subject/" + code;

        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        // display response

                        try {
                            subjectName.setText(code + ": " + response.getString("subjectName"));
                            progressNum.setText(response.getInt("actualNumberOfSignatures") + "/" + response.getInt("expectedNumberOfSignatures"));
                            progressBar.setMax(response.getInt("expectedNumberOfSignatures"));
                            progressBar.setProgress(response.getInt("actualNumberOfSignatures"));

                            if (response.getInt("actualNumberOfSignatures") >= response.getInt("expectedNumberOfSignatures"))
                                submitRequest.setEnabled(true);
                            Log.d("response2", response.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

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

    }
}
