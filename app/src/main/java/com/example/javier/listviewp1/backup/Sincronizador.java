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
    private String tipo;
    private boolean sincronizar;
    private ArrayList<Contacto> agenda, backUp, recolector;

    public Sincronizador(Context c, String tipo, boolean sincronizar) {
        this.contexto = c;
        this.tipo = tipo;
        this.sincronizar = sincronizar;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public boolean isSincronizar() {
        return sincronizar;
    }

    public void setSincronizar(boolean sincronizar) {
        this.sincronizar = sincronizar;
    }

    public void sincronizar(){

        agenda = (ArrayList<Contacto>)GestionContacto.getLista(contexto);
        backUp = null;
        recolector = null;
        try {
            backUp = GestionBackUp.leerXML(contexto, Principal.DOC_BACKUP);
            recolector = GestionBackUp.leerXML(contexto, Principal.DOC_RECOLECTOR);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }

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

    private void guardar(){
        try {
            GestionBackUp.crearXML(contexto, Principal.DOC_BACKUP, backUp);
            GestionBackUp.crearXML(contexto, Principal.DOC_RECOLECTOR, recolector);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
