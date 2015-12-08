package com.example.javier.listviewp1.fragmentos;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.javier.listviewp1.R;

/**
 * Created by Javier on 08/12/2015.
 */
public class FragmentoLista extends Fragment {
    public FragmentoLista() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragmento_lista, container, false);
    }
}
