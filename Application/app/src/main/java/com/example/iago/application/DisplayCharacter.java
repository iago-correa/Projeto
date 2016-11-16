package com.example.iago.application;

import android.app.Activity;
import android.app.Dialog;
import android.content.ClipData;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewDebug;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextSwitcher;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;

public class DisplayCharacter extends AppCompatActivity {

    private DBHelper mydb;
    int charid = 0;
    int i = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_character);

        mydb = new DBHelper(this);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            charid = (int)extras.getSerializable("charid");
        }

        View.OnClickListener LevelUpButton = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int classe = (Integer)(v.getTag());
                if (classe == 14)
                    classe = 1;
                mydb.levelUp(charid, classe);
                textupdate();
                Intent intent = new Intent(getApplicationContext(), DisplayTalentos.class);
                intent.putExtra("charid", charid);
                startActivity(intent);
            }
        };

        textupdate();

        ArrayList classes = mydb.getAll("classes");
        for(i=0;i<classes.size();i++) {
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
            tv2.setTag(i);
            tv2.setText(Integer.toString(mydb.getClassLevel(charid,i+1)));
            gl.addView(tv2,0);
            GridLayout.LayoutParams param2 =new GridLayout.LayoutParams();
            param2.height = GridLayout.LayoutParams.WRAP_CONTENT;
            param2.width = GridLayout.LayoutParams.WRAP_CONTENT;
            param2.setGravity(Gravity.CENTER_VERTICAL);
            param2.columnSpec = GridLayout.spec(1);
            param2.rowSpec = GridLayout.spec(i);
            param2.setMarginStart(10);
            tv2.setLayoutParams (param2);

            final ImageButton ib = new ImageButton(this);
            ib.setTag(i+2);
            ib.setOnClickListener(LevelUpButton);
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

    void textupdate () {

        Cursor res = mydb.getData("personagem",charid);

        String nomeChar = "";
        int racaid = 0;
        int[] value = new int [6];

        res.moveToFirst();
        while (res.isAfterLast() == false) {
            nomeChar = res.getString(res.getColumnIndex(DBHelper.CHAR_COLUMN_NAME));
            racaid = res.getInt(res.getColumnIndex(DBHelper.CHAR_COLUMN_RAÇA));
            value[0] = res.getInt(res.getColumnIndex(DBHelper.CHAR_COLUMN_FOR));
            value[1] = res.getInt(res.getColumnIndex(DBHelper.CHAR_COLUMN_DES));
            value[2] = res.getInt(res.getColumnIndex(DBHelper.CHAR_COLUMN_CON));
            value[3] = res.getInt(res.getColumnIndex(DBHelper.CHAR_COLUMN_INT));
            value[4] = res.getInt(res.getColumnIndex(DBHelper.CHAR_COLUMN_SAB));
            value[5] = res.getInt(res.getColumnIndex(DBHelper.CHAR_COLUMN_CAR));
            res.moveToNext();
        }

        TextView textName = (TextView)findViewById(R.id.textName);
        textName.setText(nomeChar);

        TextView textRaca = (TextView)findViewById(R.id.textRaca);
        textRaca.setText(mydb.getName("raca",racaid));

        TextView textValuePVs = (TextView)findViewById(R.id.textValuePV);
        textValuePVs.setText(Integer.toString(mydb.PVs(charid)));

        TextView textValueCA = (TextView)findViewById(R.id.textValueCA);
        textValueCA.setText(Integer.toString(mydb.CA(charid)));

        TextView textValueBBA = (TextView)findViewById(R.id.textValueBBA);
        textValueBBA.setText(Integer.toString(mydb.BBA(charid)));

        TextView textValueFort = (TextView)findViewById(R.id.textValueFort);
        textValueFort.setText(Integer.toString(mydb.Fort(charid)));

        TextView textValueRef = (TextView)findViewById(R.id.textValueRef);
        textValueRef.setText(Integer.toString(mydb.Ref(charid)));

        TextView textValueVon = (TextView)findViewById(R.id.textValueVon);
        textValueVon.setText(Integer.toString(mydb.Von(charid)));

        TextView textValueFOR = (TextView)findViewById(R.id.textValueFOR);
        textValueFOR.setText(Integer.toString(value[0]));

        TextView textValueDES = (TextView)findViewById(R.id.textValueDES);
        textValueDES.setText(Integer.toString(value[1]));

        TextView textValueCON = (TextView)findViewById(R.id.textValueCON);
        textValueCON.setText(Integer.toString(value[2]));

        TextView textValueINT = (TextView)findViewById(R.id.textValueINT);
        textValueINT.setText(Integer.toString(value[3]));

        TextView textValueSAB = (TextView)findViewById(R.id.textValueSAB);
        textValueSAB.setText(Integer.toString(value[4]));

        TextView textValueCAR = (TextView)findViewById(R.id.textValueCAR);
        textValueCAR.setText(Integer.toString(value[5]));

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_character, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent = null;

        switch (item.getItemId()) {
            case R.id.actionHabilidades:
                intent = new Intent(getApplicationContext(), DisplayHabilidades.class);
                intent.putExtra("charid", charid);
                startActivity(intent);
                return true;

            case R.id.actionPerícias:
                intent = new Intent(getApplicationContext(), DisplayPericias.class);
                intent.putExtra("charid", charid);
                startActivity(intent);
                return true;

            case R.id.actionAtaques:
                intent = new Intent(getApplicationContext(), DisplayAtaques.class);
                intent.putExtra("charid", charid);
                startActivity(intent);
                return true;

            case R.id.actionTalentos:
                intent = new Intent(getApplicationContext(), DisplayTalentos.class);
                intent.putExtra("charid", charid);
                startActivity(intent);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

}




