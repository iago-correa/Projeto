package com.example.iago.application;

import android.app.Activity;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Iago on 18/10/2016.
 */
public class DisplayCharacters extends Activity {

    private ImageButton obj;
    private DBHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_characters);

        mydb = new DBHelper(this);

        try {

            mydb.openDataBase();

        }catch(SQLException sqle){

            throw sqle;

        }


        final ArrayList personagens = mydb.getAll("personagens");
        final ArrayList personagensId = mydb.getAllId("personagens");

        ArrayAdapter<String> myAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, personagens);
        final ListView myList=(ListView) findViewById(R.id.charsList);
        myList.setAdapter(myAdapter);

        obj = (ImageButton)findViewById(R.id.addButton);
        obj.setOnClickListener(new  View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),CreateCharacter1.class);
                startActivity(intent);
            }
        });

        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), DisplayCharacter.class);
                int charid = 0;
                String nomeSele = (String)((TextView) view).getText();
                for(int i=0;i<personagens.size();i++){
                    if(personagens.get(i).equals(nomeSele)){
                        charid = Integer.parseInt((String)personagensId.get(i));
                    }
                }
                intent.putExtra("charid", charid);
                startActivity(intent);
            }
        });


    }

}
