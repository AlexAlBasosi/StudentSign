package com.ethandsouza2gmail.javafinalproj;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static android.database.sqlite.SQLiteDatabase.*;

/**
 * Created by Ethan Dsouza on 26-Apr-18.
 */

public class loginHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "loginDetails.db";
    private final String DB_PATH = "/data/data/com.ethandsouza2gmail.javafinalproj/databases/";
    private final String TABLE_NAME = "LoginInfo";
    private final Context context;

    private SQLiteDatabase db;

    public loginHelper(Context context) {
        super(context, DB_NAME, null, 1);
        this.context = context;
    }

    public void createDB() throws IOException{
        boolean dbExists = checkDatabase();

        if (dbExists)
        {

        }
        else
        {
            this.getReadableDatabase();

            try{
                copyDatabase();
            }
            catch ( IOException e)
            {
                throw new Error("Error copyig database");
            }
        }
    }

    private boolean checkDatabase()
    {
        SQLiteDatabase checkDB = null;

        try{
            String path = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(path, null,SQLiteDatabase.OPEN_READONLY);
        }
        catch (SQLiteException e)
        {
            e.printStackTrace();
        }

        if (checkDB != null)
            checkDB.close();

        return checkDB != null ? true : false;
    }

    private void copyDatabase() throws IOException
    {
        InputStream in = context.getAssets().open(DB_NAME);
        String outName = DB_PATH + DB_NAME;
        OutputStream out = new FileOutputStream(outName);

        byte[] buffer = new byte[1024];
        int length;
        while ((length = in.read(buffer))>0){
            out.write(buffer, 0, length);
        }

        out.flush();
        out.close();
        out.close();
    }

    public void openDB() throws SQLException
    {
        String path = DB_PATH + DB_NAME;
        db = SQLiteDatabase.openDatabase(path, null,SQLiteDatabase.OPEN_READONLY);
    }

    @Override
    public synchronized void close() {

        if(db != null)
            db.close();

        super.close();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        try {
//            this.db = SQLiteDatabase.openOrCreateDatabase(String.valueOf(context.getAssets().open("LoginDetails.db")), null);
//        }
//        catch (SQLiteException e){
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public int validateUser(String user, String pass)
    {
        this.db = getReadableDatabase();
        String query = "SELECT EMAIL, PASSWORD, TYPE FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query,null);
        String dbUser = cursor.getString(0), dbPass = cursor.getString(1), dbType = cursor.getString(2);
        do{
            if (user.equals(dbUser))
            {
                if (pass.equals(dbPass))
                {
                    if (dbType.equals("Student"))
                        return 1;
                    else if (dbType.equals("Faculty"))
                        return 2;
                    else
                        return 3;
                }
            }
        }while(cursor.moveToNext());
        return -1;
    }
}
