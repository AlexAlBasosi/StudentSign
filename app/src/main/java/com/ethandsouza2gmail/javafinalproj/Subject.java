package com.ethandsouza2gmail.javafinalproj;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Ethan Dsouza on 12-May-18.
 */

public class Subject
{
    private String code;
    private String name;
    private int sigReq;
    private int sigGot;

    public Subject(JSONObject in)
    {
        try {
            code = in.getString("subjectCode");
            name = in.getString("subjectName");
            sigReq = in.getInt("actualNumberOfSignatures");
            sigGot = in.getInt("expectedNumberOfSignatures");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public String getCode()
    {
        return code;
    }
}