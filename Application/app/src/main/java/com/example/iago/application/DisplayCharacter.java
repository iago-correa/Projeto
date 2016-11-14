package com.example.iago.application;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextSwitcher;
import android.widget.TextView;

import java.util.ArrayList;

public class DisplayCharacter extends Activity {

    private DBHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_character);

        mydb = new DBHelper(this);

        mydb.inicializaDB();

        ArrayList classes = mydb.getAll("classes");


        for(int i=0;i<classes.size();i++) {

            TextView tv1 = new TextView(this);
            tv1.setTextSize(25);
            tv1.setText((String) classes.get(i));
            GridLayout gl = (GridLayout) findViewById(R.id.gridClasses);
            gl.addView(tv1,0);
            GridLayout.LayoutParams param =new GridLayout.LayoutParams();
            param.height = GridLayout.LayoutParams.WRAP_CONTENT;
            param.width = GridLayout.LayoutParams.WRAP_CONTENT;
            param.setGravity(Gravity.CENTER_VERTICAL);
            param.columnSpec = GridLayout.spec(0);
            param.rowSpec = GridLayout.spec(i);
            tv1.setLayoutParams(param);

            TextView tv2 = new TextView(this);
            tv2.setTextSize(20);
            tv2.setText("Nivel");
            gl.addView(tv2,0);
            GridLayout.LayoutParams param2 =new GridLayout.LayoutParams();
            param2.height = GridLayout.LayoutParams.WRAP_CONTENT;
            param2.width = GridLayout.LayoutParams.WRAP_CONTENT;
            param2.setGravity(Gravity.CENTER_VERTICAL);
            param2.columnSpec = GridLayout.spec(1);
            param2.rowSpec = GridLayout.spec(i);
            param2.setMarginStart(10);
            tv2.setLayoutParams (param2);

            ImageButton ib = new ImageButton(this);
            ib.setBackgroundResource(R.drawable.roundedbutton);
            ib.setImageResource(R.drawable.plus);
            gl.addView(ib,0);
            GridLayout.LayoutParams param3 =new GridLayout.LayoutParams();
            param3.setGravity(Gravity.CENTER_VERTICAL);
            param3.setMarginStart(10);
            param3.height = 35;
            param3.width = 35;
            param3.columnSpec = GridLayout.spec(2);
            ib.setLayoutParams (param3);

        }

    }

}




