package com.example.iago.application;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

/**
 * Created by Iago on 18/10/2016.
 */
public class CreateCharacter1 extends Activity {

    private com.example.iago.application.DBHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_character1);

        String[] myStringArray = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O"};

        Spinner spinner = (Spinner) findViewById(R.id.spinnerRaca);
        Spinner spinner2 = (Spinner) findViewById(R.id.spinnerClasse);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, myStringArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner2.setAdapter(adapter);

    }

}
