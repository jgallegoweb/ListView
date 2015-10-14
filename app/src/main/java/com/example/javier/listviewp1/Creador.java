package com.example.javier.listviewp1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

public class Creador extends AppCompatActivity {
    private ArrayList<Contacto> contactos;
    private EditText etNombre, etTelefono;
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private void init(){
        etNombre = (EditText)findViewById(R.id.etNombre);
        etTelefono = (EditText)findViewById(R.id.etTelefono);
    }

    public void guardar(View v){
        ArrayList<String> tel = new ArrayList<>();
        tel.add(etTelefono.getText().toString());
        Contacto nuevo = new Contacto(-1, etNombre.getText().toString(), tel);

        Intent intent = new Intent();
        Bundle p = new Bundle();
        p.putSerializable("nuevoContacto", nuevo);
        intent.putExtras(p);
        setResult(RESULT_OK, intent);
        finish();
    }
}
