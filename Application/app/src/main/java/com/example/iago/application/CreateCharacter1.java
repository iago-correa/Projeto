package com.example.iago.application;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import java.util.ArrayList;

/**
 * Created by Iago on 18/10/2016.
 */
public class CreateCharacter1 extends Activity {

    private DBHelper mydb;
    private Button obj;

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

        obj = (Button)findViewById(R.id.buttonCreate);
        obj.setOnClickListener(new  View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),DisplayCharacter.class);
                startActivity(intent);
            }
        });

    }

}
