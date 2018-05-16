package com.ethandsouza2gmail.javafinalproj;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.sql.SQLException;

public class loginPage extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private Button signIn;
    private TextView invalidLogin;

    private loginHelper db;

    public void init()
    {
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        signIn = (Button) findViewById(R.id.signIn);
        invalidLogin = (TextView) findViewById(R.id.invalidLogin);

        db = new loginHelper(this);

        try {

            db.createDB();

        }
        catch (IOException e)
        {
            throw new Error("Unable to create database");
        }

        try{
            db.openDB();
        }
        catch (SQLException e)
        {
            throw new Error("Unable to open");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        init();
        username.setText("");
        password.setText("");
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate(username.getText().toString(),password.getText().toString());
            }
        });
    }

    private void validate(String enteredUser, String enteredPass)
    {
        if (enteredUser.equals("ethan") && enteredPass.equals("pass")) {
            Intent logIn = new Intent(loginPage.this, homeScreen.class);
            startActivity(logIn);
        }
        else if (enteredUser.equals("faculty") && enteredPass.equals("pass"))
        {
            Intent logIn = new Intent(loginPage.this, facultyScreen.class);
            startActivity(logIn);
        }
        else if (enteredUser.equals("registrar") && enteredPass.equals("pass"))
        {
            Intent logIn = new Intent(loginPage.this, registrarScreen.class);
            startActivity(logIn);
        }
        else
        {
            username.setText("");
            password.setText("");
            invalidLogin.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        username.setText("");
        password.setText("");
        invalidLogin.setVisibility(View.INVISIBLE);
    }

    public void onDestroy()
    {
        super.onDestroy();
        db.close();
    }

}