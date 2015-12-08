package com.example.javier.listviewp1.fragmentos;

import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.javier.listviewp1.contacto.Contacto;
import com.example.javier.listviewp1.R;
import com.example.javier.listviewp1.adaptadores.AdaptadorVistaContacto;

/**
 * Created by Javier on 08/12/2015.
 */
public class FragmentoDetalle extends Fragment {
    private TextView tvNombreVista;
    private AdaptadorVistaContacto aVista;
    private ImageView ivFoto;
    private Contacto contacto;
    private ListView lv;
    private View v;
    public FragmentoDetalle() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.framento_detalle, container, false);
        lv = (ListView)v.findViewById(R.id.lvNumerosVista);
        tvNombreVista = (TextView)v.findViewById(R.id.tvNombreVista);
        ivFoto = (ImageView)v.findViewById(R.id.ivFotoVista);
        return v;
    }
    public void setContacto(Contacto contacto){
        this.contacto = contacto;
        tvNombreVista.setText(contacto.getNombre());
        ivFoto.setImageURI(Uri.parse(contacto.getFoto()));
        aVista = new AdaptadorVistaContacto(v.getContext(), R.layout.telefono, contacto.getTelefono());
        lv.setAdapter(aVista);
        lv.setTag(contacto.getTelefono());

    }
}
