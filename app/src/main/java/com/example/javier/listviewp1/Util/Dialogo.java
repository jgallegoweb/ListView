package com.example.javier.listviewp1.Util;

/**
 * Created by Javier on 25/10/2015.
 */
public class Dialogo {
}


/*

private void confirmarBorrar(final int posicion){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(R.string.confirmar);
        LayoutInflater inflater = LayoutInflater.from(this);
        final View vista = inflater.inflate(R.layout.dialogo_ver, null);
        TextView tvMensaje = (TextView) vista.findViewById(R.id.tvMensaje);
        tvMensaje.setText(getString(R.string.mensaje_borrar)+" "+contactos.get(posicion).getNombre());
        alert.setView(vista);
        alert.setPositiveButton(R.string.aceptar,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        borrarContacto(posicion);
                    }
                });
        alert.setNegativeButton(R.string.cancelar, null);
        alert.show();
    }
 */