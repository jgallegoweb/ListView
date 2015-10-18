package com.example.javier.listviewp1;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
        public ImageButton ibAccion;
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
        Contacto contacto = contactos.get(position);
        if(convertView==null){
            convertView = infladorLayout.inflate(recurso, null);
            v.tvNombre = (TextView) convertView.findViewById(R.id.tvNombre);
            v.tvTelefono = (TextView) convertView.findViewById(R.id.tvTelefono);
            v.ivPerfil = (ImageView) convertView.findViewById(R.id.ivPerfil);
            v.ibAccion = (ImageButton) convertView.findViewById(R.id.ibAccion);
            convertView.setTag(v);
        }else{
            v = (ViewHolder) convertView.getTag();
        }
        v.tvNombre.setText(contacto.getNombre());
        int contadorNumeros = (Integer) contacto.getTelefono().size();
        if(contadorNumeros>0){
            v.tvTelefono.setText(contacto.getTelefono().get(0));
            /*
            int t = (Integer) contadorNumeros > 1 ? R.drawable.ic_add_black_18dp : R.drawable.ic_keyboard_arrow_right_black_18dp;
            v.ibAccion.setImageResource(t);
            */
            if(contadorNumeros==1){
                v.ibAccion.setImageResource(R.drawable.ic_add_black_18dp);
                addInsertarNumero(v.ibAccion, contacto);
            }else{
                v.ibAccion.setImageResource(R.drawable.ic_keyboard_arrow_right_black_18dp);
                addVerNumeros(v.ibAccion, contacto);
            }

        }

        return convertView;
    }

    public void addInsertarNumero(final ImageButton ib, final Contacto contacto){
        ib.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                insertarNumero(contacto);
            }
        });
    }
    public void addVerNumeros(ImageButton ib, final Contacto contacto) {
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verNumeros(contacto);
            }
        });
    }
    public void insertarNumero(final Contacto contacto){
        AlertDialog.Builder alert = new AlertDialog.Builder(contexto);
        alert.setTitle(R.string.titulo_insertar);
        LayoutInflater inflater = LayoutInflater.from(contexto);
        int res = R.layout.dialogo_insertar;
        final View vista = inflater.inflate(res, null);
        TextView tvNombreDialogo = (TextView)vista.findViewById(R.id.tvNombreDialogo);
        tvNombreDialogo.setText(contacto.getNombre());
        alert.setView(vista);
        alert.setPositiveButton(R.string.insertar,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        contacto.addTelefono(((EditText) vista.findViewById(R.id.etNuevoNumero)).getText().toString());
                        notifyDataSetChanged();
                    }
                });
        alert.setNegativeButton(R.string.cancelar, null);
        alert.show();
    }

    public void verNumeros(final Contacto contacto){
        AlertDialog.Builder alert = new AlertDialog.Builder(contexto);
        alert.setTitle(R.string.titulo_ver);
        LayoutInflater inflater = LayoutInflater.from(contexto);
        final View vista = inflater.inflate(R.layout.dialogo_ver, null);

        TextView tvNombreDialogo = (TextView) vista.findViewById(R.id.tvMensaje);
        tvNombreDialogo.setText(contacto.getNombre());

        ListView lvTelefonosCreador = (ListView)vista.findViewById(R.id.lvTelefonosDialogo);
        AdaptadorVistaContacto aVista = new AdaptadorVistaContacto(contexto, R.layout.telefono, contacto.getTelefono());
        lvTelefonosCreador.setAdapter(aVista);
        lvTelefonosCreador.setTag(contacto.getTelefono());

        alert.setView(vista);
        alert.setPositiveButton(R.string.insertar_numero,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        insertarNumero(contacto);
                    }
                });
        alert.setNegativeButton(R.string.cerrar, null);
        alert.show();
    }
}
