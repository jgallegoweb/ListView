package com.example.javier.listviewp1.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.javier.listviewp1.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Javier on 17/10/2015.
 */
public class AdaptadorVistaContacto extends ArrayAdapter<String> {
    private Context contexto;
    private int recurso;
    private LayoutInflater infladorLayout;
    private ArrayList<String> telefonos;

    static class ViewHolder{
        public TextView tvTelefonoVista;
    }

    public AdaptadorVistaContacto(Context context, int resource, ArrayList<String> objects) {
        super(context, resource, objects);
        this.contexto = context;
        this.recurso = resource;
        this.infladorLayout = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.telefonos = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder v = new ViewHolder();
        String telefono = telefonos.get(position);
        if(convertView==null){
            convertView = infladorLayout.inflate(recurso, null);
            v.tvTelefonoVista = (TextView) convertView.findViewById(R.id.tvElementoTelefono);
            convertView.setTag(v);
        }else{
            v = (ViewHolder) convertView.getTag();
        }
        v.tvTelefonoVista.setText(telefono);

        return convertView;
    }
}
