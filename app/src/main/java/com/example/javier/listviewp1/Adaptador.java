package com.example.javier.listviewp1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

/**
 * Created by Javier on 07/10/2015.
 */
public class Adaptador extends ArrayAdapter<Contacto>{

    private Context contexto;
    private int recurso;
    private LayoutInflater infladorLayout;
    private List<Contacto> contactos;

    static class ViewHolder{
        public TextView tvNombre, tvTelefono;
        public ImageView ivPerfil;
        public Button btAccion;
    }

    public Adaptador(Context context, int resource, List<Contacto> objects) {
        super(context, resource, objects);
        this.contexto = context;
        this.recurso = resource;
        this.infladorLayout = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.contactos = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder v = new ViewHolder();
        if(convertView==null){
            convertView = infladorLayout.inflate(recurso, null);
            v.tvNombre = (TextView) convertView.findViewById(R.id.tvNombre);
            v.tvTelefono = (TextView) convertView.findViewById(R.id.tvTelefono);
            v.ivPerfil = (ImageView) convertView.findViewById(R.id.ivPerfil);
            v.btAccion = (Button) convertView.findViewById(R.id.btAccion);
            convertView.setTag(v);
        }else{
            v = (ViewHolder) convertView.getTag();
        }
        v.tvNombre.setText(contactos.get(position).getNombre());
        if((Integer) contactos.get(position).getTelefono().size()>0){
            v.tvTelefono.setText(contactos.get(position).getTelefono().get(0));
            String t = (Integer) contactos.get(position).getTelefono().size() > 1 ? "+" : "-";
            v.btAccion.setText(t);
        }


        return convertView;
    }
}
