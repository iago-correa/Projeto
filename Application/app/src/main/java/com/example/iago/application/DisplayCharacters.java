package com.example.iago.application;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

/**
 * Created by Iago on 18/10/2016.
 */
public class DisplayCharacters extends Activity {

    private ImageButton obj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_characters);

        String[] myStringArray = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O"};

        ArrayAdapter<String> myAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, myStringArray);
        ListView myList=(ListView) findViewById(R.id.charsList);
        myList.setAdapter(myAdapter);

        obj = (ImageButton)findViewById(R.id.addButton);
        obj.setOnClickListener(new  View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),CreateCharacter1.class);
                startActivity(intent);
            }
        });

    }

}
