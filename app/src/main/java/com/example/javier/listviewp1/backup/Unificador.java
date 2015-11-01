package com.example.javier.listviewp1.backup;

import com.example.javier.listviewp1.Contacto;

import java.util.ArrayList;

/**
 * Created by Javier on 28/10/2015.
 */
public class Unificador {
    private ArrayList<Contacto> back, recolector, nuevo;

    public Unificador(ArrayList<Contacto> back, ArrayList<Contacto> recolector, ArrayList<Contacto> nuevo) {
        this.back = back;
        this.recolector = recolector;
        this.nuevo = nuevo;
    }

    public void getNuevoBackUp(){
        for(Contacto n : this.nuevo){
            for(Contacto b : this.nuevo){
                if(n.equals(b)){
                    ArrayList<String> bt = b.getTelefono();
                    ArrayList<String> nt = n.getTelefono();
                    for(String tbt : bt){

                    }
                }
            }
        }
    }

    private boolean ok(String telefono){
        return true;
    }
}
