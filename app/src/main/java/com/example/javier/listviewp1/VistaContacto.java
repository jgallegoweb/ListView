package com.example.javier.listviewp1;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class VistaContacto extends AppCompatActivity {
    private TextView tvNombreVista;
    private AdaptadorVistaContacto aVista;
    private Contacto contacto;
    private ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_vista_contacto);
        init();
    }

    private void init(){
        Bundle p = getIntent().getExtras();
        contacto = (Contacto)p.getSerializable("contacto");
        lv = (ListView)findViewById(R.id.lvNumerosVista);
        visualiza();
    }

    private void visualiza(){
        tvNombreVista = (TextView)findViewById(R.id.tvNombreVista);
        tvNombreVista.setText(contacto.getNombre());
        aVista = new AdaptadorVistaContacto(this, R.layout.telefono, contacto.getTelefono());
        lv.setAdapter(aVista);
        lv.setTag(contacto.getTelefono());
    }

    public void editar(View v){
        Intent intent = new Intent(this, Creador.class);
        Bundle p = new Bundle();
        p.putSerializable("contacto", contacto);
        intent.putExtras(p);
        startActivityForResult(intent, Principal.EDITOR);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Principal.EDITOR){
            if(resultCode == Activity.RESULT_OK){
                contacto = (Contacto)data.getExtras().getSerializable("contacto");
                Intent intent = new Intent();
                Bundle p = new Bundle();
                p.putSerializable("contacto", contacto);
                intent.putExtras(p);
                setResult(RESULT_OK, intent);
                visualiza();
            }
        }
    }
}
