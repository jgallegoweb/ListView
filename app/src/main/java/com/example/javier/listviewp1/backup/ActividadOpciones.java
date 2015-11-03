package com.example.javier.listviewp1.backup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.example.javier.listviewp1.R;

import org.w3c.dom.Text;

public class ActividadOpciones extends AppCompatActivity {

    private Switch sSync, tSync;
    private TextView tvFechaSync;
    private Preferencias pc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_opciones);
        init();
    }

    private void init(){
        sSync = (Switch)findViewById(R.id.sSync);
        tSync = (Switch)findViewById(R.id.tSync);
        tvFechaSync = (TextView)findViewById(R.id.tvFechaSync);
        pc = new Preferencias(this);
        cargarActuales();
        cargarEventos();
    }

    private void cargarActuales(){
        if(pc.isAutoSync()){ sSync.setChecked(true); }
        if(pc.getTipoSync()=="total"){ tSync.setChecked(true); }
        tvFechaSync.setText(pc.getFechaSync());
    }

    private void cargarEventos(){
        sSync.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                pc.setAutoSync(isChecked);
            }
        });

        tSync.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                pc.setTipoSync((isChecked) ? "total" : "parcial");
            }
        });
    }
}
