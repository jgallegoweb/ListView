package com.example.javier.listviewp1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class Creador extends AppCompatActivity {
    private ArrayList<Contacto> contactos;
    private EditText etNombre, etTelefono;
    private ListView lvTelefonosCreador;
    private AdaptadorVistaContacto aVista;
    private Contacto contacto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_creador);
        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_creador, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private void init(){
        contacto =(Contacto)getIntent().getExtras().getSerializable("contacto");

        etNombre = (EditText)findViewById(R.id.etNombre);
        etTelefono = (EditText)findViewById(R.id.etTelefono);
        lvTelefonosCreador = (ListView)findViewById(R.id.lvTelefonosCreador);
        etNombre.setText(contacto.getNombre());
        aVista = new AdaptadorVistaContacto(this, R.layout.telefono, contacto.getTelefono());
        lvTelefonosCreador.setAdapter(aVista);
        lvTelefonosCreador.setTag(contacto.getTelefono());
        registerForContextMenu(lvTelefonosCreador);
    }

    public void guardar(View v){
        contacto.setNombre(etNombre.getText().toString());

        Intent intent = new Intent();
        Bundle p = new Bundle();
        p.putSerializable("contacto", contacto);
        intent.putExtras(p);
        setResult(RESULT_OK, intent);
        finish();
    }

    public void addTelefono(View v){
        contacto.getTelefono().add(etTelefono.getText().toString());
        etTelefono.setText("");
        aVista.notifyDataSetChanged();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_contextual_creador, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();
        AdapterView.AdapterContextMenuInfo vistaInfo = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();

        switch(id){
            case R.id.menu_contextual_creador_eliminar:
                contacto.removeTelefono(vistaInfo.position);
                break;
            case R.id.menu_contextual_creador_establecer:
                contacto.setTelefonoPrincipal(vistaInfo.position);
                break;
        }

        aVista.notifyDataSetChanged();
        return super.onContextItemSelected(item);
    }
}
