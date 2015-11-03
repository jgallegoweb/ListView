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

import com.example.javier.listviewp1.backup.ActividadOpciones;
import com.example.javier.listviewp1.backup.Preferencias;
import com.example.javier.listviewp1.backup.Sincronizador;

import java.util.ArrayList;
import java.util.Collections;

public class Principal extends AppCompatActivity {
    private Adaptador adaptador;
    private ListView lv;
    private ArrayList<Contacto> contactos;
    private int posicion_editada;
    private Preferencias preferencias;
    private Sincronizador sincronizador;
    public static final String DOC_BACKUP = "backupcontactos";
    public static final String DOC_RECOLECTOR = "recolector";

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
            case R.id.sync:
                sincroniza();
                contactos = sincronizador.getBackUp();
                mostrarLista();
                break;
            case R.id.opciones:
                verOpciones();
                break;
        }

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CREADOR){
            if(resultCode == Activity.RESULT_OK){
                Bundle p = data.getExtras();
                Contacto nuevo = (Contacto)p.getSerializable("contacto");
                sincronizador.add(nuevo);
                contactos = sincronizador.getBackUp();
                ordenarAscendente();
                adaptador.notifyDataSetChanged();
                tostada(getString(R.string.exito_guardar));
            }
        }else if(requestCode == EDITOR || requestCode == VISOR){
            if(resultCode == Activity.RESULT_OK){
                Bundle p = data.getExtras();
                Contacto editado = (Contacto)p.getSerializable("contacto");
                sincronizador.set(posicion_editada, editado);
                contactos = sincronizador.getBackUp();
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

    /***********************************************************************************************
     * INIT ****************************************************************************************
     **********************************************************************************************/

    private void init(){
        lv = (ListView)findViewById(R.id.lvContactos);
        preferencias = new Preferencias(this);
        sincronizador = new Sincronizador(this);
        sincronizador.cargar();
        if (preferencias.isAutoSync()){
            sincroniza();
        }
        contactos = sincronizador.getBackUp();

        if(contactos.size()==0){
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle(R.string.confirmar);
            LayoutInflater inflater = LayoutInflater.from(this);
            final View vista = inflater.inflate(R.layout.dialogo_back, null);
            TextView tvMensaje = (TextView) vista.findViewById(R.id.tvDialgoBack);
            tvMensaje.setText(getString(R.string.no_back));
            alert.setView(vista);
            alert.setPositiveButton(R.string.crear,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            nuevoBackUp();
                            mostrarLista();
                        }
                    });
            alert.setNegativeButton(R.string.cancelar, null);
            alert.show();
        }
        mostrarLista();
    }

    private void mostrarLista(){
        adaptador = new Adaptador(this, R.layout.elemento, contactos);
        lv.setAdapter(adaptador);
        lv.setTag(contactos);
        registerForContextMenu(lv);
    }

    /***********************************************************************************************
     * STARTACTIVITIES *****************************************************************************
     **********************************************************************************************/
    private void verOpciones(){
        Intent intent = new Intent(this, ActividadOpciones.class);
        startActivity(intent);
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

    public void verContacto(int posicion){
        Intent intent = new Intent(this, VistaContacto.class);
        Bundle p = new Bundle();
        p.putSerializable("contacto", contactos.get(posicion));
        intent.putExtras(p);
        posicion_editada = posicion;
        startActivityForResult(intent, VISOR);
    }

    /***********************************************************************************************
     * MODIFICACIONES LISTA ************************************************************************
     **********************************************************************************************/
     private void borrarContacto(int posicion){
         sincronizador.remove(posicion);
         contactos = sincronizador.getBackUp();
         adaptador.notifyDataSetChanged();
         tostada(getString(R.string.exito_borrar));
    }


    /***********************************************************************************************
     * DIALOGOS ************************************************************************************
     **********************************************************************************************/
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

    /***********************************************************************************************
     * ORDENAR *************************************************************************************
     **********************************************************************************************/
    private void ordenarAscendente(){
        Collections.sort(contactos);
        adaptador.notifyDataSetChanged();
    }

    private void ordenarDescendente(){
        Collections.sort(contactos, Collections.reverseOrder());
        adaptador.notifyDataSetChanged();
    }

    /***********************************************************************************************
     * BACKUP **************************************************************************************
     **********************************************************************************************/

    private void nuevoBackUp(){
        contactos = (ArrayList<Contacto>)GestionContacto.getLista(this);
        sincronizador.setBackUp(contactos);
        sincronizador.setRecolector(contactos);
        sincronizador.guardar();
        actualizarFecha();
    }

    private void actualizarFecha(){
        preferencias.setActualFechaSync();
    }

    private void sincroniza(){
        if(preferencias.getTipoSync()=="total"){
            sincronizador.sustituir();
        }else{
            sincronizador.sincronizar();
        }
    }

    private void tostada(String texto){
        Toast.makeText(this, texto, Toast.LENGTH_LONG).show();
    }
}