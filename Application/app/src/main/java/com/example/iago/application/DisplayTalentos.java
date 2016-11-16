package com.example.iago.application;

import android.app.Activity;
import android.os.Bundle;

public class DisplayTalentos extends Activity {

    private DBHelper mydb;
    int charid = 0;
    int i = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_talentos);

        mydb = new DBHelper(this);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            charid = (int)extras.getSerializable("charid");
        }

        

    }

}
