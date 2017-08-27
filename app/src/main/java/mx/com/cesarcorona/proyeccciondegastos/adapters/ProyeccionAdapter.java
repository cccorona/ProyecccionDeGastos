package mx.com.cesarcorona.proyeccciondegastos.adapters;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;

import java.util.LinkedHashMap;
import java.util.LinkedList;

import mx.com.cesarcorona.proyeccciondegastos.R;
import mx.com.cesarcorona.proyeccciondegastos.pojo.Person;
import mx.com.cesarcorona.proyeccciondegastos.pojo.RowProyeccion;

import static mx.com.cesarcorona.proyeccciondegastos.pojo.Person.HOMBRE;
import static mx.com.cesarcorona.proyeccciondegastos.pojo.Person.MUJER;

/**
 * Created by ccabrera on 26/08/17.
 */

public class ProyeccionAdapter extends BaseAdapter {


    private LinkedList<RowProyeccion> listaproyecciones;
    private Context context;


    public ProyeccionAdapter(LinkedList<RowProyeccion> proyecciones, Context context) {
        this.listaproyecciones = proyecciones;
        this.context = context;
    }



    public void saveData(){

    }





    @Override
    public int getCount() {
        return listaproyecciones.size()-10;
    }

    @Override
    public RowProyeccion getItem(int position) {
        return listaproyecciones.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.proyeccion_item_layout,parent,false);
        EditText fecha1 = (EditText) rootView.findViewById(R.id.fecha1);
        EditText value1 = (EditText) rootView.findViewById(R.id.value1);
        EditText fecha2 = (EditText) rootView.findViewById(R.id.fecha2);
        EditText value2 = (EditText) rootView.findViewById(R.id.value2);

        fecha1.setText(""+listaproyecciones.get(position).getAno());
        value1.setText(""+(int)listaproyecciones.get(position).getPesos());
        fecha2.setText(""+listaproyecciones.get(position+10).getAno());
        value2.setText(""+(int)listaproyecciones.get(position+10).getPesos());
        return rootView;

    }








}
