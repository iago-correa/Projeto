package com.example.iago.application;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

/**
 * Created by Iago on 18/10/2016.
 */
public class CreateCharacter1 extends Activity {

    private DBHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_character1);

        mydb = new DBHelper(this);

        mydb.inicializaDB();

        ArrayList classes = mydb.getAll("classes");
        ArrayList racas = mydb.getAll("racas");

        Spinner spinner = (Spinner) findViewById(R.id.spinnerRaca);
        Spinner spinner2 = (Spinner) findViewById(R.id.spinnerClasse);

        ArrayAdapter arrayAdapter1 = new ArrayAdapter(this,android.R.layout.simple_spinner_item, classes);
        ArrayAdapter arrayAdapter2 = new ArrayAdapter(this,android.R.layout.simple_spinner_item, racas);

        arrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        arrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(arrayAdapter2);
        spinner2.setAdapter(arrayAdapter1);

    }

}
