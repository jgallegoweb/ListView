package com.example.javier.listviewp1.backup;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Javier on 20/10/2015.
 */
public class Preferencias {
    private final String AUTO_SYNC = "autoSync";
    private final String TIPO_SYNC = "tipoSync";
    private final String FECHA_SYNC = "fechaSync";
    private SharedPreferences pc;

    public Preferencias(Context c) {
        pc = c.getSharedPreferences("sync", Context.MODE_PRIVATE);
    }

    public boolean isAutoSync() {
        return pc.getBoolean(AUTO_SYNC, false);
    }

    public void setAutoSync(boolean autoSync) {
        SharedPreferences.Editor ed = pc.edit();
        ed.putBoolean(AUTO_SYNC, autoSync);
        ed.commit();
    }

    public void invertAutoSync(){
        SharedPreferences.Editor ed = pc.edit();
        ed.putBoolean(AUTO_SYNC, !pc.getBoolean(AUTO_SYNC, false));
        ed.commit();
    }

    public String getFechaSync() {
        return pc.getString(FECHA_SYNC, "yyyy/mm/dd");
    }

    public void setFechaSync(String fechaSync) {
        SharedPreferences.Editor ed = pc.edit();
        ed.putString(FECHA_SYNC, fechaSync);
        ed.commit();
    }

    public void setActualFechaSync(){
        Calendar c = new GregorianCalendar();
        String d = Integer.toString(c.get(Calendar.DATE));
        String M = Integer.toString(c.get(Calendar.MONTH));
        String y = Integer.toString(c.get(Calendar.YEAR));

        String h = Integer.toString(c.get(Calendar.HOUR_OF_DAY));
        String m = Integer.toString(c.get(Calendar.MINUTE));
        String s = Integer.toString(c.get(Calendar.SECOND));

        SharedPreferences.Editor ed = pc.edit();
        ed.putString(FECHA_SYNC, y+"/"+M+"/"+d+" "+h+":"+m+":"+s);
        ed.commit();
    }

    public void setTipoSync(String tipo){
        SharedPreferences.Editor ed = pc.edit();
        ed.putString(TIPO_SYNC, tipo);
        ed.commit();
    }

    public String getTipoSync(){
        return pc.getString(TIPO_SYNC, "total");
    }
}
