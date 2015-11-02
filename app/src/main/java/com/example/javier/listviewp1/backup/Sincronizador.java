package com.example.javier.listviewp1.backup;

import android.content.Context;
import android.util.Log;

import com.example.javier.listviewp1.Contacto;
import com.example.javier.listviewp1.GestionContacto;
import com.example.javier.listviewp1.Principal;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Javier on 31/10/2015.
 */
public class Sincronizador {
    private Context contexto;
    private ArrayList<Contacto> agenda, backUp, recolector;

    public Sincronizador(Context c) {
        this.contexto = c;
        agenda = null;
        backUp = null;
        recolector = null;
    }

    public ArrayList<Contacto> getAgenda() {
        return agenda;
    }

    public void setAgenda(ArrayList<Contacto> agenda) {
        this.agenda = agenda;
    }

    public ArrayList<Contacto> getBackUp() {
        return backUp;
    }

    public void setBackUp(ArrayList<Contacto> backUp) {
        this.backUp = backUp;
    }

    public ArrayList<Contacto> getRecolector() {
        return recolector;
    }

    public void setRecolector(ArrayList<Contacto> recolector) {
        this.recolector = recolector;
    }

    public void cargar(){
        try {
            backUp = GestionBackUp.leerXML(contexto, Principal.DOC_BACKUP);
            recolector = GestionBackUp.leerXML(contexto, Principal.DOC_RECOLECTOR);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
    }

    public void sincronizar(){
        agenda = (ArrayList<Contacto>)GestionContacto.getLista(contexto);
        backUp = null;
        recolector = null;
        cargar();

        for(Contacto contactoAgenda : agenda){
            Contacto contactoBack = buscaContacto(contactoAgenda, backUp);
            Contacto contactoRecolector = buscaContacto(contactoAgenda, recolector);

            if(contactoBack==null){
                if(contactoRecolector==null){
                    incluirContacto(contactoAgenda);
                }
            }else{
                ArrayList<String> telefonos = contactoAgenda.getTelefono();
                for(String t : telefonos){
                    if(contactoBack.getTelefono().indexOf(t)==-1 && contactoRecolector.getTelefono().indexOf(t)==-1){
                        contactoBack.getTelefono().add(t);
                        contactoRecolector.getTelefono().add(t);
                    }
                }
            }
        }

        Log.v("backup", backUp.toString());
        guardar();
    }

    private Contacto buscaContacto(Contacto contacto, ArrayList<Contacto> cs){
        for(Contacto c : cs){
            if (c.equals(contacto)){
                return c;
            }
        }
        return null;
    }

    private void incluirContacto(Contacto c){
        backUp.add(c);
        recolector.add(c);
    }

    public void guardar(){
        try {
            GestionBackUp.crearXML(contexto, Principal.DOC_BACKUP, backUp);
            GestionBackUp.crearXML(contexto, Principal.DOC_RECOLECTOR, recolector);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void add(Contacto c){
        getBackUp().add(c);
        getRecolector().add(c);
        guardar();
    }

    public void set(int pos, Contacto c){
        getBackUp().set(pos, c);
        guardar();
    }

    public void remove(int pos){
        getBackUp().remove(pos);
        guardar();
    }
}
