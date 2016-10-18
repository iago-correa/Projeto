package com.example.iago.application;

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
import android.widget.ImageView;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private ImageView obj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
