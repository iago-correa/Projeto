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

public class DisplayHabilidades extends Activity {

    // Lista de habilidades
    private final String[] ESTADOS = new String[] {
            "Acre", "Alagoas", "Amapá", "Amazonas", "Bahia", "Ceará", "Distrito Federal",
            "Espírito Santo", "Goiás", "Maranhão", "Mato Grosso", "Mato Grosso do Sul",
            "Minas Gerais", "Pará", "Paraíba", "Paraná", "Pernambuco", "Piauí", "Rio de Janeiro",
            "Rio Grande do Norte", "Rio Grande do Sul", "Rondônia", "Roraima", "Santa Catarina",
            "São Paulo", "Sergipe", "Tocantins"
    };


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_display_multi);
        ListView list = (ListView) findViewById(R.id.multiList);

        ArrayAdapter<String> lsvEstadosAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                String estado = ESTADOS[position];
                View v = convertView;

                if(v==null) {
                    LayoutInflater inflater = getLayoutInflater();
                    v = (View) inflater.inflate(R.layout.activiry_display_habilidades, null);
                }

                CheckBox check = (CheckBox) v.findViewById(R.id.chkHabilidade);

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

                TextView txv = (TextView) v.findViewById(R.id.textHabilidade);
                txv.setText(estado);

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
