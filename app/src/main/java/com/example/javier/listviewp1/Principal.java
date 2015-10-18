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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Principal extends AppCompatActivity {
    private Adaptador adaptador;
    private ArrayList<Contacto> contactos;
    private int posicion_editada;
    static final int CREADOR = 1, EDITOR = 2, VISOR = 3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_principal);
        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case R.id.ordenar_asc:
                ordenarAscendente();
                break;
            case R.id.ordenar_des:
                ordenarDescendente();
                break;
        }

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
        Bundle p = new Bundle();
        Contacto c = new Contacto(-1, "", new ArrayList<String>());
        p.putSerializable("contacto", c);
        intent.putExtras(p);
        startActivityForResult(intent, CREADOR);
    }

    private void verEditar(int posicion){
        Intent intent = new Intent(this, Creador.class);
        Bundle p = new Bundle();
        p.putSerializable("contacto", contactos.get(posicion));
        posicion_editada = posicion;
        intent.putExtras(p);
        startActivityForResult(intent, EDITOR);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CREADOR){
            if(resultCode == Activity.RESULT_OK){
                Bundle p = data.getExtras();
                Contacto nuevo = (Contacto)p.getSerializable("contacto");
                contactos.add(nuevo);
                ordenarAscendente();
                adaptador.notifyDataSetChanged();
                tostada(getString(R.string.exito_guardar));
            }
        }else if(requestCode == EDITOR || requestCode == VISOR){
            if(resultCode == Activity.RESULT_OK){
                Bundle p = data.getExtras();
                Contacto editado = (Contacto)p.getSerializable("contacto");
                contactos.set(posicion_editada, editado);
                ordenarAscendente();
                adaptador.notifyDataSetChanged();
                tostada(getString(R.string.exito_guardar));
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
                verEditar(vistaInfo.position);
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
        tostada(getString(R.string.exito_borrar));
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
        posicion_editada = posicion;
        startActivityForResult(intent, VISOR);
    }

    private void ordenarAscendente(){
        Collections.sort(contactos);
        adaptador.notifyDataSetChanged();
    }

    private void ordenarDescendente(){
        Collections.sort(contactos, Collections.reverseOrder());
        adaptador.notifyDataSetChanged();
    }

    private void tostada(String texto){
        Toast.makeText(this, texto, Toast.LENGTH_LONG).show();
    }

}