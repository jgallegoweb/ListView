package com.example.javier.listviewp1;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Javier on 07/10/2015.
 */
public class Contacto implements Serializable, Comparable<Contacto>{
    private long id;
    private String nombre;
    private ArrayList<String> telefono;

    public Contacto() {
    }

    public Contacto(long id, String nombre, ArrayList<String> telefono) {
        this.nombre = nombre;
        this.telefono = telefono;
    }

    public long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public ArrayList<String> getTelefono() {
        return telefono;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setTelefono(ArrayList<String> telefono) {
        this.telefono = telefono;
    }

    public void addTelefono(String telefono){ this.telefono.add(telefono); }

    public void removeTelefono(int pos){ this.telefono.remove(pos); }

    public void setTelefonoPrincipal(int pos){
        String aux = this.telefono.get(0);
        this.telefono.set(0, this.telefono.get(pos));
        this.telefono.set(pos, aux);
    }



    @Override
    public int compareTo(Contacto contacto) {
        int c = this.nombre.compareToIgnoreCase(contacto.nombre);
        return c!=0 ? c : (int)(this.id - contacto.id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Contacto contacto = (Contacto) o;

        if(this.nombre.equalsIgnoreCase(contacto.getNombre())){
            for(String tlf : this.telefono){
                if (contacto.getTelefono().indexOf(tlf)!=-1){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return "Contacto{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", telefono=" + telefono +
                '}';
    }
}
