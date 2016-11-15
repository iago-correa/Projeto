package com.example.iago.application;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class DisplayAtaques extends Activity {

    // Lista de pericias
    private final String[][] ESTADOS = new String[][] {{"Acre", "Físico", "360"},{"Amapá", "Mágico", "800"}};


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_display_multi);
        ListView list = (ListView) findViewById(R.id.multiList);

        ArrayAdapter<String> lsvEstadosAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1) {
            @Override
            public View getView(int position,View convertView, ViewGroup parent) {

                String estado = ESTADOS[position][0];
                String tipo = ESTADOS[position][1];
                String val = ESTADOS[position][2];
                View v = convertView;

                if(v==null) {
                    LayoutInflater inflater = getLayoutInflater();
                    v = (View) inflater.inflate(R.layout.activity_display_ataques, null);
                }

                CheckBox check = (CheckBox) v.findViewById(R.id.chkAtk);

                check.setTag(estado);

                check.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CheckBox chk = (CheckBox) v;
                        String estado = (String) chk.getTag();
                        if(chk.isChecked()) {
                            Toast.makeText(getApplicationContext(), "Checbox de " + estado + " marcado!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Checbox de " + estado + " desmarcado!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                TextView txvEstado = (TextView) v.findViewById(R.id.textNome);
                TextView txvTipo = (TextView) v.findViewById(R.id.textAtk);
                TextView txvVal = (TextView) v.findViewById(R.id.textDano);
                txvEstado.setText(estado);
                txvTipo.setText(tipo);
                txvVal.setText(val);

                return v;
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public int getCount() {
                return ESTADOS.length;
            }
        };
        list.setAdapter(lsvEstadosAdapter);
    }

}
