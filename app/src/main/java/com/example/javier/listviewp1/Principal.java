package com.example.javier.listviewp1;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

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
        registerForContextMenu(lv);
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

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_contextual, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();
        AdapterView.AdapterContextMenuInfo vistaInfo = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();

        switch(id){
            case R.id.menu_contextual_ver:
                verContacto(vistaInfo.position);
                break;
            case R.id.menu_contextual_editar:

                break;
            case R.id.menu_contextual_eliminar:
                confirmarBorrar(vistaInfo.position);
                break;
        }

        return super.onContextItemSelected(item);
    }

    private void borrarContacto(int posicion){
        contactos.remove(posicion);
        adaptador.notifyDataSetChanged();
    }

    private void confirmarBorrar(final int posicion){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(R.string.confirmar);
        LayoutInflater inflater = LayoutInflater.from(this);
        final View vista = inflater.inflate(R.layout.dialogo_ver, null);
        TextView tvMensaje = (TextView) vista.findViewById(R.id.tvMensaje);
        tvMensaje.setText(getString(R.string.mensaje_borrar)+" "+contactos.get(posicion).getNombre());
        alert.setView(vista);
        alert.setPositiveButton(R.string.aceptar,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        borrarContacto(posicion);
                    }
                });
        alert.setNegativeButton(R.string.cancelar, null);
        alert.show();
    }

    public void verContacto(int posicion){
        Intent intent = new Intent(this, VistaContacto.class);
        Bundle p = new Bundle();
        p.putSerializable("contacto", contactos.get(posicion));
        intent.putExtras(p);
        startActivity(intent);
    }
}