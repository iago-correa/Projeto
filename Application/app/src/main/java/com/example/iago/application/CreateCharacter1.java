package com.example.iago.application;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Iago on 18/10/2016.
 */

public class CreateCharacter1 extends Activity {

    private DBHelper mydb;
    private Button buttonCreate;
    private ImageButton buttonInfoClass, buttonInfoRaca;
    private ImageButton buttonPlusAtr1, buttonMinusAtr1;
    private ImageButton buttonPlusAtr2, buttonMinusAtr2;
    private ImageButton buttonPlusAtr3, buttonMinusAtr3;
    private ImageButton buttonPlusAtr4, buttonMinusAtr4;
    private ImageButton buttonPlusAtr5, buttonMinusAtr5;
    private ImageButton buttonPlusAtr6, buttonMinusAtr6;
    private Spinner spinnerTraceOne, spinnerTraceTwo;
    private int[] atributes = {10,10,10,10,10,10};
    private int pontos = 20;

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

    public int[] plusAtr(int val, int pontos){

        switch(val) {
            case 7: case 8: case 9: case 10: case 11: case 12: case 13:
                if(pontos>=1) {
                    val += 1;
                    pontos -= 1;
                }
                break;
            case 14: case 15:
                if(pontos>=2) {
                    val += 1;
                    pontos -= 2;
                }
                break;
            case 16: case 17:
                if(pontos>=3) {
                    val += 1;
                    pontos -= 3;
                }
                break;
            default:
                break;
        }

        int[] retorno = {val,pontos};
        return retorno;

    }

    public int[] minusAtr(int val, int pontos){

        switch(val) {
            case 8:
                val -= 1;
                pontos += 2;
                break;
            case 9:
                val -= 1;
                pontos += 1;
                break;
            case 10:
                val -= 1;
                pontos += 1;
                break;
            case 11:
                val -= 1;
                pontos += 1;
                break;
            case 12:
                val -= 1;
                pontos += 1;
                break;
            case 13:
                val -= 1;
                pontos += 1;
                break;
            case 14:
                val -= 1;
                pontos += 1;
                break;
            case 15:
                val -= 1;
                pontos += 2;
                break;
            case 16:
                val -= 1;
                pontos += 2;
                break;
            case 17:
                val -= 1;
                pontos += 3;
                break;
            case 18:
                val -= 1;
                pontos += 3;
                break;
            default:
                break;
        }

        int[] retorno = {val,pontos};
        return retorno;


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_character1);

        mydb = new DBHelper(this);

        try {

            mydb.openDataBase();

        }catch(SQLException sqle){

            throw sqle;

        }

        ArrayList classes = mydb.getAll("classes");
        final ArrayList classesId = mydb.getAllId("classes");
        ArrayList racas = mydb.getAll("racas");
        final ArrayList racasId = mydb.getAllId("racas");

        final Spinner spinner = (Spinner) findViewById(R.id.spinnerRaca);
        final Spinner spinner2 = (Spinner) findViewById(R.id.spinnerClasse);

        ArrayAdapter arrayAdapter1 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, classes);
        ArrayAdapter arrayAdapter2 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, racas);

        arrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        arrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(arrayAdapter2);
        spinner2.setAdapter(arrayAdapter1);

        buttonCreate = (Button) findViewById(R.id.buttonCreate);

        spinnerTraceOne = (Spinner) findViewById(R.id.spinnerTrace1);
        spinnerTraceTwo = (Spinner) findViewById(R.id.spinnerTrace2);

        final String[] sts = {"FOR", "DES", "CON", "INT", "SAB", "CAR"};

        ArrayAdapter<String> adapterSts = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sts);
        adapterSts.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTraceOne.setAdapter(adapterSts);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                String raca = (String) spinner.getSelectedItem();

                if (raca.equals("Humano")) {

                    GridLayout grid = (GridLayout) findViewById(R.id.mainGrid);
                    GridLayout.LayoutParams param = new GridLayout.LayoutParams();
                    param.height = GridLayout.LayoutParams.WRAP_CONTENT;
                    param.width = GridLayout.LayoutParams.MATCH_PARENT;
                    param.columnSpec = GridLayout.spec(0);
                    param.rowSpec = GridLayout.spec(9);
                    buttonCreate.setLayoutParams(param);

                    spinnerTraceOne.setVisibility(View.VISIBLE);

                    String[] sts2 = new String[5];
                    int j = 0;

                    for (int i = 0; i < sts.length; i++) {
                        if (!sts[i].equals("FOR")) {
                            sts2[j] = sts[i];
                            j++;
                        }
                    }

                    ArrayAdapter<String> adapterSts = new ArrayAdapter<String>(CreateCharacter1.this, android.R.layout.simple_spinner_item, sts2);
                    adapterSts.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerTraceTwo.setAdapter(adapterSts);

                    spinnerTraceTwo.setVisibility(View.VISIBLE);


                } else {

                    GridLayout grid = (GridLayout) findViewById(R.id.mainGrid);
                    GridLayout.LayoutParams param = new GridLayout.LayoutParams();
                    param.height = GridLayout.LayoutParams.WRAP_CONTENT;
                    param.width = GridLayout.LayoutParams.MATCH_PARENT;
                    param.columnSpec = GridLayout.spec(0);
                    param.rowSpec = GridLayout.spec(7);
                    buttonCreate.setLayoutParams(param);

                    spinnerTraceOne.setVisibility(View.INVISIBLE);
                    spinnerTraceTwo.setVisibility(View.INVISIBLE);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        spinnerTraceOne.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                String raca = (String) spinner.getSelectedItem();

                if (raca.equals("Humano")) {

                    GridLayout grid = (GridLayout) findViewById(R.id.mainGrid);
                    GridLayout.LayoutParams param = new GridLayout.LayoutParams();
                    param.height = GridLayout.LayoutParams.WRAP_CONTENT;
                    param.width = GridLayout.LayoutParams.MATCH_PARENT;
                    param.columnSpec = GridLayout.spec(0);
                    param.rowSpec = GridLayout.spec(9);
                    buttonCreate.setLayoutParams(param);

                    String[] sts2 = new String[5];
                    int j = 0;

                    for (int i = 0; i < sts.length; i++) {
                        if (!sts[i].equals((String) spinnerTraceOne.getSelectedItem())) {
                            sts2[j] = sts[i];
                            j++;
                        }
                    }

                    ArrayAdapter<String> adapterSts = new ArrayAdapter<String>(CreateCharacter1.this, android.R.layout.simple_spinner_item, sts2);
                    adapterSts.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerTraceTwo.setAdapter(adapterSts);

                    spinnerTraceTwo.setVisibility(View.VISIBLE);

                } else {

                    GridLayout grid = (GridLayout) findViewById(R.id.mainGrid);
                    GridLayout.LayoutParams param = new GridLayout.LayoutParams();
                    param.height = GridLayout.LayoutParams.WRAP_CONTENT;
                    param.width = GridLayout.LayoutParams.MATCH_PARENT;
                    param.columnSpec = GridLayout.spec(0);
                    param.rowSpec = GridLayout.spec(7);
                    buttonCreate.setLayoutParams(param);

                    spinnerTraceOne.setVisibility(View.INVISIBLE);
                    spinnerTraceTwo.setVisibility(View.INVISIBLE);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        buttonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText nameText = (EditText) findViewById(R.id.nameText);
                String nome = nameText.getText().toString();

                int id = 0;
                int raçaid = Integer.parseInt((String)racasId.get((int)spinner.getSelectedItemId()));
                if( (id=mydb.insertPersonagem(nome,atributes[0],atributes[1],atributes[2],atributes[3],atributes[4],atributes[5],raçaid)) > 0 ){
                    int classid = Integer.parseInt((String)classesId.get((int)spinner2.getSelectedItemId()));
                    if(mydb.levelUp(id,classid)){
                        String raca = (String) spinner.getSelectedItem();

                        if(raca.equals("Humano")){
                            mydb.attChange(id,(String)spinnerTraceOne.getSelectedItem(),2);
                            mydb.attChange(id,(String)spinnerTraceTwo.getSelectedItem(),2);
                        }else{
                            mydb.raceUpdate(id);
                        }

                        Intent intent = new Intent(getApplicationContext(), DisplayCharacter.class);
                        intent.putExtra("charid", id);
                        startActivity(intent);

                    }
                }

            }
        });

        buttonInfoClass = (ImageButton) findViewById(R.id.buttonInfoClasse);
        buttonInfoClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome = (String) spinner2.getSelectedItem();
                final String desc = mydb.getDescr(nome, "classe");
                dialogBox(desc.replace("\"", ""));
            }
        });

        buttonInfoRaca = (ImageButton) findViewById(R.id.buttonInfoRaca);
        buttonInfoRaca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome = (String) spinner.getSelectedItem();
                final String desc = mydb.getDescr(nome, "raca");
                dialogBox(desc.replace("\"", ""));
            }
        });


        buttonPlusAtr1 = (ImageButton) findViewById(R.id.buttonPlusAtr1);
        buttonPlusAtr1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    int[] retorno = plusAtr(atributes[0],pontos);
                    atributes[0] = retorno[0];
                    pontos = retorno[1];

                    TextView valAtr1 = (TextView)findViewById(R.id.valAtr1);
                    valAtr1.setText(String.valueOf(atributes[0]));
                    TextView modAtr1 = (TextView)findViewById(R.id.modAtr1);
                    int mod = (atributes[0]-10)/2;
                    if (mod>0){
                        modAtr1.setText("+"+String.valueOf(mod));
                    }else{
                        modAtr1.setText(String.valueOf(mod));
                    }

                    TextView tv = (TextView)findViewById(R.id.textName);
                    tv.setText(String.valueOf(pontos));

                }
            });

        buttonMinusAtr1 = (ImageButton) findViewById(R.id.buttonMinusAtr1);
        buttonMinusAtr1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int[] retorno = minusAtr(atributes[0],pontos);
                atributes[0] = retorno[0];
                pontos = retorno[1];

                TextView valAtr1 = (TextView)findViewById(R.id.valAtr1);
                valAtr1.setText(String.valueOf(atributes[0]));
                TextView modAtr1 = (TextView)findViewById(R.id.modAtr1);
                int mod = (atributes[0]-10)/2;
                if (mod>0){
                    modAtr1.setText("+"+String.valueOf(mod));
                }else{
                    modAtr1.setText(String.valueOf(mod));
                }

                TextView tv = (TextView)findViewById(R.id.textName);
                tv.setText(String.valueOf(pontos));

            }
        });

        buttonPlusAtr2 = (ImageButton) findViewById(R.id.buttonPlusAtr2);
        buttonPlusAtr2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int[] retorno = plusAtr(atributes[1],pontos);
                atributes[1] = retorno[0];
                pontos = retorno[1];

                TextView valAtr2 = (TextView)findViewById(R.id.valAtr2);
                valAtr2.setText(String.valueOf(atributes[1]));
                TextView modAtr2 = (TextView)findViewById(R.id.modAtr2);
                int mod = (atributes[1]-10)/2;
                if (mod>0){
                    modAtr2.setText("+"+String.valueOf(mod));
                }else{
                    modAtr2.setText(String.valueOf(mod));
                }

                TextView tv = (TextView)findViewById(R.id.textName);
                tv.setText(String.valueOf(pontos));

            }
        });

        buttonMinusAtr2 = (ImageButton) findViewById(R.id.buttonMinusAtr2);
        buttonMinusAtr2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int[] retorno = minusAtr(atributes[1],pontos);
                atributes[1] = retorno[0];
                pontos = retorno[1];

                TextView valAtr2 = (TextView)findViewById(R.id.valAtr2);
                valAtr2.setText(String.valueOf(atributes[1]));
                TextView modAtr2 = (TextView)findViewById(R.id.modAtr2);
                int mod = (atributes[1]-10)/2;
                if (mod>0){
                    modAtr2.setText("+"+String.valueOf(mod));
                }else{
                    modAtr2.setText(String.valueOf(mod));
                }

                TextView tv = (TextView)findViewById(R.id.textName);
                tv.setText(String.valueOf(pontos));

            }
        });

        buttonPlusAtr3 = (ImageButton) findViewById(R.id.buttonPlusAtr3);
        buttonPlusAtr3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int[] retorno = plusAtr(atributes[2],pontos);
                atributes[2] = retorno[0];
                pontos = retorno[1];

                TextView valAtr3 = (TextView)findViewById(R.id.valAtr3);
                valAtr3.setText(String.valueOf(atributes[2]));
                TextView modAtr3 = (TextView)findViewById(R.id.modAtr3);
                int mod = (atributes[2]-10)/2;
                if (mod>0){
                    modAtr3.setText("+"+String.valueOf(mod));
                }else{
                    modAtr3.setText(String.valueOf(mod));
                }

                TextView tv = (TextView)findViewById(R.id.textName);
                tv.setText(String.valueOf(pontos));

            }
        });

        buttonMinusAtr3 = (ImageButton) findViewById(R.id.buttonMinusAtr3);
        buttonMinusAtr3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int[] retorno = minusAtr(atributes[2],pontos);
                atributes[2] = retorno[0];
                pontos = retorno[1];

                TextView valAtr3 = (TextView)findViewById(R.id.valAtr3);
                valAtr3.setText(String.valueOf(atributes[2]));
                TextView modAtr3 = (TextView)findViewById(R.id.modAtr3);
                int mod = (atributes[2]-10)/2;
                if (mod>0){
                    modAtr3.setText("+"+String.valueOf(mod));
                }else{
                    modAtr3.setText(String.valueOf(mod));
                }

                TextView tv = (TextView)findViewById(R.id.textName);
                tv.setText(String.valueOf(pontos));

            }
        });

        buttonPlusAtr4 = (ImageButton) findViewById(R.id.buttonPlusAtr4);
        buttonPlusAtr4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int[] retorno = plusAtr(atributes[3],pontos);
                atributes[3] = retorno[0];
                pontos = retorno[1];

                TextView valAtr4 = (TextView)findViewById(R.id.valAtr4);
                valAtr4.setText(String.valueOf(atributes[3]));
                TextView modAtr4 = (TextView)findViewById(R.id.modAtr4);
                int mod = (atributes[3]-10)/2;
                if (mod>0){
                    modAtr4.setText("+"+String.valueOf(mod));
                }else{
                    modAtr4.setText(String.valueOf(mod));
                }

                TextView tv = (TextView)findViewById(R.id.textName);
                tv.setText(String.valueOf(pontos));

            }
        });

        buttonMinusAtr4 = (ImageButton) findViewById(R.id.buttonMinusAtr4);
        buttonMinusAtr4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int[] retorno = minusAtr(atributes[3],pontos);
                atributes[3] = retorno[0];
                pontos = retorno[1];

                TextView valAtr4 = (TextView)findViewById(R.id.valAtr4);
                valAtr4.setText(String.valueOf(atributes[3]));
                TextView modAtr4 = (TextView)findViewById(R.id.modAtr4);
                int mod = (atributes[3]-10)/2;
                if (mod>0){
                    modAtr4.setText("+"+String.valueOf(mod));
                }else{
                    modAtr4.setText(String.valueOf(mod));
                }

                TextView tv = (TextView)findViewById(R.id.textName);
                tv.setText(String.valueOf(pontos));

            }
        });

        buttonPlusAtr5 = (ImageButton) findViewById(R.id.buttonPlusAtr5);
        buttonPlusAtr5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int[] retorno = plusAtr(atributes[4],pontos);
                atributes[4] = retorno[0];
                pontos = retorno[1];

                TextView valAtr5 = (TextView)findViewById(R.id.valAtr5);
                valAtr5.setText(String.valueOf(atributes[4]));
                TextView modAtr5 = (TextView)findViewById(R.id.modAtr5);
                int mod = (atributes[4]-10)/2;
                if (mod>0){
                    modAtr5.setText("+"+String.valueOf(mod));
                }else{
                    modAtr5.setText(String.valueOf(mod));
                }

                TextView tv = (TextView)findViewById(R.id.textName);
                tv.setText(String.valueOf(pontos));

            }
        });

        buttonMinusAtr5 = (ImageButton) findViewById(R.id.buttonMinusAtr5);
        buttonMinusAtr5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int[] retorno = minusAtr(atributes[4],pontos);
                atributes[4] = retorno[0];
                pontos = retorno[1];

                TextView valAtr5 = (TextView)findViewById(R.id.valAtr5);
                valAtr5.setText(String.valueOf(atributes[4]));
                TextView modAtr5 = (TextView)findViewById(R.id.modAtr5);
                int mod = (atributes[4]-10)/2;
                if (mod>0){
                    modAtr5.setText("+"+String.valueOf(mod));
                }else{
                    modAtr5.setText(String.valueOf(mod));
                }

                TextView tv = (TextView)findViewById(R.id.textName);
                tv.setText(String.valueOf(pontos));

            }
        });

        buttonPlusAtr6 = (ImageButton) findViewById(R.id.buttonPlusAtr6);
        buttonPlusAtr6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int[] retorno = plusAtr(atributes[5],pontos);
                atributes[5] = retorno[0];
                pontos = retorno[1];

                TextView valAtr6 = (TextView)findViewById(R.id.valAtr6);
                valAtr6.setText(String.valueOf(atributes[5]));
                TextView modAtr6 = (TextView)findViewById(R.id.modAtr6);
                int mod = (atributes[5]-10)/2;
                if (mod>0){
                    modAtr6.setText("+"+String.valueOf(mod));
                }else{
                    modAtr6.setText(String.valueOf(mod));
                }

                TextView tv = (TextView)findViewById(R.id.textName);
                tv.setText(String.valueOf(pontos));

            }
        });

        buttonMinusAtr6 = (ImageButton) findViewById(R.id.buttonMinusAtr6);
        buttonMinusAtr6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int[] retorno = minusAtr(atributes[5],pontos);
                atributes[5] = retorno[0];
                pontos = retorno[1];

                TextView valAtr6 = (TextView)findViewById(R.id.valAtr6);
                valAtr6.setText(String.valueOf(atributes[5]));
                TextView modAtr6 = (TextView)findViewById(R.id.modAtr6);
                int mod = (atributes[5]-10)/2;
                if (mod>0){
                    modAtr6.setText("+"+String.valueOf(mod));
                }else{
                    modAtr6.setText(String.valueOf(mod));
                }

                TextView tv = (TextView)findViewById(R.id.textName);
                tv.setText(String.valueOf(pontos));

            }
        });


    }


}
