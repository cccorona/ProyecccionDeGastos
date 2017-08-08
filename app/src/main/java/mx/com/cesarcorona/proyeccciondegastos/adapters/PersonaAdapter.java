package mx.com.cesarcorona.proyeccciondegastos.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.io.ByteArrayInputStream;
import java.util.LinkedList;

import mx.com.cesarcorona.proyeccciondegastos.pojo.Persona;

/**
 * Created by ccabrera on 08/08/17.
 */

public class PersonaAdapter extends BaseAdapter {

    private LinkedList<Persona> personas;
    private Context context;

    @Override
    public int getCount() {
        return personas.size();
    }

    @Override
    public Persona getItem(int position) {
        return  personas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
