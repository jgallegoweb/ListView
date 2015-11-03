package com.example.javier.listviewp1.Util;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by Javier on 25/10/2015.
 */
public class Dialogo {
    private Context contexto;
    private int layout;
    private AlertDialog.Builder dialogo;
    private LayoutInflater inflater;
    private View vista;

    public Dialogo(Context contexto, int layout) {
        this.contexto = contexto;
        this.layout = layout;
        dialogo= new AlertDialog.Builder(this.contexto);
        inflater = LayoutInflater.from(this.contexto);
        vista = inflater.inflate(layout, null);
        dialogo.setView(vista);
    }
}