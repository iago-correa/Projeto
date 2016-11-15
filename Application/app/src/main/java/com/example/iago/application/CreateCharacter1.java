package com.example.iago.application;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Iago on 18/10/2016.
 */

public class CreateCharacter1 extends Activity {

    private DBHelper mydb;
    private Button buttonCreate;
    private ImageButton buttonInfoClass, buttonInfoRaca;

    public void dialogBox(String content){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(content);
        alertDialogBuilder.setTitle("Descrição");
        alertDialogBuilder.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_character1);

        mydb = new DBHelper(this);

        mydb.inicializaDB();

        ArrayList classes = mydb.getAll("classes");
        ArrayList racas = mydb.getAll("racas");

        final Spinner spinner = (Spinner) findViewById(R.id.spinnerRaca);
        final Spinner spinner2 = (Spinner) findViewById(R.id.spinnerClasse);

        ArrayAdapter arrayAdapter1 = new ArrayAdapter(this,android.R.layout.simple_spinner_item, classes);
        ArrayAdapter arrayAdapter2 = new ArrayAdapter(this,android.R.layout.simple_spinner_item, racas);

        arrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        arrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(arrayAdapter2);
        spinner2.setAdapter(arrayAdapter1);

        buttonCreate = (Button)findViewById(R.id.buttonCreate);
        buttonCreate.setOnClickListener(new  View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),DisplayCharacter.class);
                startActivity(intent);
            }
        });

        buttonInfoClass = (ImageButton)findViewById(R.id.buttonInfoClasse);
        buttonInfoClass.setOnClickListener(new  View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String nome = (String)spinner2.getSelectedItem();
                final String desc = mydb.getDescr(nome,"classe");
                dialogBox(desc.replace("\"", ""));
            }
        });

        buttonInfoRaca = (ImageButton)findViewById(R.id.buttonInfoRaca);
        buttonInfoRaca.setOnClickListener(new  View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String nome = (String)spinner.getSelectedItem();
                final String desc = mydb.getDescr(nome,"raca");
                dialogBox(desc.replace("\"", ""));
            }
        });

    }

}
