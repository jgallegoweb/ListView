package com.example.javier.listviewp1.backup;

import android.content.Context;
import android.util.Xml;

import com.example.javier.listviewp1.Contacto;
import com.example.javier.listviewp1.Principal;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Javier on 25/10/2015.
 */
public class GestionBackUp {
    public static void crearXML(Context contexto, ArrayList<Contacto> contactos) throws IOException {
        FileOutputStream fosxml = new FileOutputStream(new File(contexto.getFilesDir(),"backupcontactos.xml"));

        XmlSerializer docxml = Xml.newSerializer();
        docxml.setOutput(fosxml, "UTF-8");
        docxml.startDocument(null, Boolean.valueOf(true));
        docxml.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);

        docxml.startTag(null, "agenda");

        for(Contacto c : contactos){
            docxml.startTag(null, "contacto");
            docxml.attribute(null, "estado", "valor");
            docxml.attribute(null, "id", c.getId() + "");
            docxml.startTag(null, "nombre");
            docxml.text(c.getNombre());
            docxml.endTag(null, "nombre");
            docxml.startTag(null, "telefonos");
            for(String tel : c.getTelefono()){
                docxml.startTag(null, "telefono");
                docxml.text(tel);
                docxml.endTag(null, "telefono");
            }
            docxml.endTag(null, "telefonos");
            docxml.endTag(null, "contacto");
        }

        docxml.endDocument();
        docxml.flush();
        fosxml.close();
    }

    public static ArrayList<Contacto> leerXML(Context contexto) throws IOException, XmlPullParserException {
        ArrayList<Contacto> contactos = new ArrayList<>();
        XmlPullParser lectorxml = Xml.newPullParser();
        lectorxml.setInput(new FileInputStream(new File(contexto.getFilesDir(),"backupcontactos.xml")),"utf-8");
        int evento = lectorxml.getEventType();
        String id, texto;
        Contacto c = new Contacto();
        while (evento != XmlPullParser.END_DOCUMENT){
            if(evento == XmlPullParser.START_TAG){
                String etiqueta = lectorxml.getName();
                if(etiqueta.compareTo("contacto")==0){
                    c = new Contacto();
                    id = lectorxml.getAttributeValue(null, "id");
                    c.setId(Integer.parseInt(id));
                }else if(etiqueta.compareTo("nombre")==0){
                    c.setNombre(lectorxml.nextText());
                }else if(etiqueta.compareTo("telefonos")==0){
                    c.setTelefono(new ArrayList<String>());
                }else if(etiqueta.compareTo("telefono")==0){
                    c.addTelefono(lectorxml.nextText());
                }
            }else if(evento == XmlPullParser.END_TAG){
                String etiqueta = lectorxml.getName();
                if(etiqueta.compareTo("contacto")==0){
                    contactos.add(c);
                }
            }
            evento = lectorxml.next();
        }
        return contactos;
    }
}
