package com.example.javier.listviewp1;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class Principal extends AppCompatActivity {
    private Adaptador adaptador;
    private ArrayList<Contacto> contactos;
    static final int CREADOR = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_principal);
        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void init(){
        final ListView lv = (ListView)findViewById(R.id.lvContactos);
        contactos = (ArrayList<Contacto>) GestionContacto.getLista(this);
        adaptador = new Adaptador(this, R.layout.elemento, contactos);
        lv.setAdapter(adaptador);
        lv.setTag(contactos);
    }

    public void verInsertar(View v){
        Intent intent = new Intent(this, Creador.class);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CREADOR){
            if(resultCode == Activity.RESULT_OK){
                Bundle p = data.getExtras();
                Contacto nuevo = (Contacto)p.getSerializable("nuevoContacto");
                contactos.add(nuevo);

                adaptador.notifyDataSetChanged();
            }
        }

    }
}
