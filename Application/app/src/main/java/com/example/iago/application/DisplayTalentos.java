package com.example.iago.application;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class DisplayTalentos extends Activity{

    private DBHelper mydb;
    TabHost tabHost;
    int charid=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_talentos);

        mydb = new DBHelper(this);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            charid = (int)extras.getSerializable("charid");
        }

        final TabHost host = (TabHost)findViewById(R.id.tabhost);
        host.setup();

        //Tab 1
        TabHost.TabSpec spec = host.newTabSpec("Combate");
        spec.setContent(R.id.combate);
        spec.setIndicator("Combate");
        host.addTab(spec);

        //Tab 2
        spec = host.newTabSpec("Magia");
        spec.setContent(R.id.magia);
        spec.setIndicator("Magia");
        host.addTab(spec);

        //Tab 3
        spec = host.newTabSpec("Perícia");
        spec.setContent(R.id.pericia);
        spec.setIndicator("Perícia");
        host.addTab(spec);

        //Tab 4
        spec = host.newTabSpec("Divino");
        spec.setContent(R.id.divino);
        spec.setIndicator("Divino");
        host.addTab(spec);

        //Tab 5
        spec = host.newTabSpec("Destino");
        spec.setContent(R.id.destino);
        spec.setIndicator("Destino");
        host.addTab(spec);

        //Tab 6
        spec = host.newTabSpec("Tormenta");
        spec.setContent(R.id.tormenta);
        spec.setIndicator("Tormenta");
        host.addTab(spec);

        final Context contex = this;

        host.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String arg0) {
                mydb = new DBHelper(contex);
                if(host.getCurrentTab()==0){ //combate

                    final ListView talentosCombate = (ListView)findViewById(R.id.talentosCombate);

                    final ArrayList combs = mydb.getAllFeat(charid,"Combate");
                    final ArrayList combNames = new ArrayList();
                    for(int i=0;i<combs.size();i++){
                        combNames.add(mydb.getName("talento",Integer.valueOf((String)combs.get(i))));
                    }
                    ArrayAdapter<String> myAdapter=new ArrayAdapter<String>(contex,android.R.layout.simple_list_item_1, combNames);
                    talentosCombate.setAdapter(myAdapter);

                    talentosCombate.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            int featid = 0;
                            String nomeSele = (String)((TextView) view).getText();
                            for(int i=0;i<combs.size();i++){
                                if(combNames.get(i).equals(nomeSele)){
                                    featid = Integer.parseInt((String)combs.get(i));
                                }
                            }

                            talentosCombate.setLayoutParams(new TableLayout.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.MATCH_PARENT, (float) 0.3));

                            GridLayout gl = (GridLayout)findViewById(R.id.combateSeleciona);
                            gl.setVisibility(View.VISIBLE);

                            final TextView textNomeTalentoCombate = (TextView)findViewById(R.id.textNomeTalentoCombate);
                            textNomeTalentoCombate.setText(mydb.getName("talento",featid));

                            TextView textReqTalentoCombate = (TextView)findViewById(R.id.textReqTalentoCombate);
                            Button buttonAddTalentoCombate = (Button)findViewById(R.id.buttonAddTalentoCombate);
                            if(mydb.featPrereq(charid,featid)){
                                textReqTalentoCombate.setText("Pré-requisitos preenchidos");
                                buttonAddTalentoCombate.setClickable(true);
                            }else{
                                textReqTalentoCombate.setText("Pré-requisitos não preenchidos");
                                buttonAddTalentoCombate.setClickable(false);
                            }

                            final int finalFeatid = featid;
                            buttonAddTalentoCombate.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    mydb.featBuy(charid, finalFeatid,0);

                                    Intent intent = new Intent(getApplicationContext(), DisplayCharacter.class);
                                    intent.putExtra("charid", charid);
                                    startActivity(intent);

                                }
                            });

                        }
                    });

                }else if(host.getCurrentTab()==1){ //magia



                }
            }
        });




    //padrão
    final ListView talentosCombate = (ListView)findViewById(R.id.talentosCombate);

                    final ArrayList combs = mydb.getAllFeat(charid,"Combate");
                    final ArrayList combNames = new ArrayList();
                    for(int i=0;i<combs.size();i++){
                        combNames.add(mydb.getName("talento",Integer.valueOf((String)combs.get(i))));
                    }
                    ArrayAdapter<String> myAdapter=new ArrayAdapter<String>(contex,android.R.layout.simple_list_item_1, combNames);
                    talentosCombate.setAdapter(myAdapter);

                    talentosCombate.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            int featid = 0;
                            String nomeSele = (String)((TextView) view).getText();
                            for(int i=0;i<combs.size();i++){
                                if(combNames.get(i).equals(nomeSele)){
                                    featid = Integer.parseInt((String)combs.get(i));
                                }
                            }

                            talentosCombate.setLayoutParams(new TableLayout.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.MATCH_PARENT, (float) 0.3));

                            GridLayout gl = (GridLayout)findViewById(R.id.combateSeleciona);
                            gl.setVisibility(View.VISIBLE);

                            final TextView textNomeTalentoCombate = (TextView)findViewById(R.id.textNomeTalentoCombate);
                            textNomeTalentoCombate.setText(mydb.getName("talento",featid));

                            TextView textReqTalentoCombate = (TextView)findViewById(R.id.textReqTalentoCombate);
                            Button buttonAddTalentoCombate = (Button)findViewById(R.id.buttonAddTalentoCombate);
                            if(mydb.featPrereq(charid,featid)){
                                textReqTalentoCombate.setText("Pré-requisitos preenchidos");
                                buttonAddTalentoCombate.setClickable(true);
                            }else{
                                textReqTalentoCombate.setText("Pré-requisitos não preenchidos");
                                buttonAddTalentoCombate.setClickable(false);
                            }

                            final int finalFeatid = featid;
                            buttonAddTalentoCombate.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    mydb.featBuy(charid, finalFeatid,0);

                                    Intent intent = new Intent(getApplicationContext(), DisplayCharacter.class);
                                    intent.putExtra("charid", charid);
                                    startActivity(intent);

                                }
                            });

                        }


    }

}