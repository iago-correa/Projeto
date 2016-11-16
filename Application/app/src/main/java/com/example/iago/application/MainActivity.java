package com.example.iago.application;

import android.content.res.AssetManager;
import android.database.SQLException;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private ImageView obj;
    private DBHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mydb = new DBHelper(this);

        try {

            mydb.inicializaDB();

        } catch (IOException ioe) {

            throw new Error("Unable to create database");

        }

        try {

            mydb.openDataBase();

        }catch(SQLException sqle){

            throw sqle;

        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        obj = (ImageView)findViewById(R.id.cover);
        obj.setOnClickListener(new  View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(),DisplayCharacters.class);
                startActivity(intent);

            }
        });



    }
}
