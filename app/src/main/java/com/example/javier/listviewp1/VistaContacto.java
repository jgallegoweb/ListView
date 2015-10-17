package com.example.javier.listviewp1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class VistaContacto extends AppCompatActivity {
    private TextView tvNombreVista;
    private AdaptadorVistaContacto aVista;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_vista_contacto);
        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_vista_contacto, menu);
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
        Bundle p = getIntent().getExtras();
        Contacto contacto = (Contacto)p.getSerializable("contacto");
        tvNombreVista = (TextView)findViewById(R.id.tvNombreVista);
        tvNombreVista.setText(contacto.getNombre());

        final ListView lv = (ListView)findViewById(R.id.lvNumerosVista);
        aVista = new AdaptadorVistaContacto(this, R.layout.telefono, contacto.getTelefono());
        lv.setAdapter(aVista);
        lv.setTag(contacto.getTelefono());
        //registerForContextMenu(lv);
    }
}
