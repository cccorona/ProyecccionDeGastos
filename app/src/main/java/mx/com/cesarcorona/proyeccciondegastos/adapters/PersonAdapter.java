package mx.com.cesarcorona.proyeccciondegastos.adapters;

import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.zip.Inflater;

import mx.com.cesarcorona.proyeccciondegastos.R;
import mx.com.cesarcorona.proyeccciondegastos.fragments.HomeFragment;
import mx.com.cesarcorona.proyeccciondegastos.pojo.Person;

import static mx.com.cesarcorona.proyeccciondegastos.pojo.Person.HOMBRE;
import static mx.com.cesarcorona.proyeccciondegastos.pojo.Person.MUJER;
import static mx.com.cesarcorona.proyeccciondegastos.pojo.Person.NONE;

/**
 * Created by ccabrera on 15/08/17.
 */

public class PersonAdapter extends BaseAdapter {


    private LinkedList<Person> listaPersonas;
    private Context context;


    public PersonAdapter(LinkedList<Person> listaPersonas, Context context) {
        this.listaPersonas = listaPersonas;
        this.context = context;
    }



    public void saveData(){

    }





    @Override
    public int getCount() {
        return listaPersonas.size();
    }

    @Override
    public Person getItem(int position) {
        return listaPersonas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.person_item_table_layout,parent,false);
        EditText numero = (EditText) rootView.findViewById(R.id.number);
        EditText edad = (EditText) rootView.findViewById(R.id.edad);
        EditText genero = (EditText) rootView.findViewById(R.id.genero);
        final CheckBox masculinoCheck,femeninoCheck;
        masculinoCheck = (CheckBox) rootView.findViewById(R.id.masculino_check);
        femeninoCheck = (CheckBox) rootView.findViewById(R.id.famenino_check);



        edad.addTextChangedListener(new TextWatcher()
        {
            public void afterTextChanged(Editable s) {
                try{
                    listaPersonas.get(position).setEdad(Integer.valueOf(s.toString()));

                }catch (Exception e){

                }

            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });







        masculinoCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String genero = listaPersonas.get(position).getGenero();
                if(HOMBRE.equalsIgnoreCase(genero)){
                    masculinoCheck.setChecked(false);
                    listaPersonas.get(position).setGenero(NONE);
                }else if(MUJER.equalsIgnoreCase(genero)){
                    masculinoCheck.setChecked(true);
                    listaPersonas.get(position).setGenero(HOMBRE);
                    femeninoCheck.setChecked(false);
                }else{
                    masculinoCheck.setChecked(true);
                    femeninoCheck.setChecked(false);
                    listaPersonas.get(position).setGenero(HOMBRE);
                }

                notifyDataSetChanged();

            }
        });

        femeninoCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String genero = listaPersonas.get(position).getGenero();
                if(HOMBRE.equalsIgnoreCase(genero)){
                    masculinoCheck.setChecked(false);
                    femeninoCheck.setChecked(true);
                    listaPersonas.get(position).setGenero(MUJER);
                }else if(MUJER.equalsIgnoreCase(genero)){
                    masculinoCheck.setChecked(true);
                    listaPersonas.get(position).setGenero(NONE);
                    femeninoCheck.setChecked(false);
                }else{
                    femeninoCheck.setChecked(true);
                    masculinoCheck.setChecked(false);
                    listaPersonas.get(position).setGenero(MUJER);
                }

                notifyDataSetChanged();

            }
        });




        numero.setText(""+(1+position));
        edad.setText(""+listaPersonas.get(position).getEdad());
        genero.setText("");
        if(listaPersonas.get(position).getGenero().equalsIgnoreCase(HOMBRE)){
            masculinoCheck.setChecked(true);
            femeninoCheck.setChecked(false);

        }else if(listaPersonas.get(position).getGenero().equalsIgnoreCase(MUJER)){
            femeninoCheck.setChecked(true);
            masculinoCheck.setChecked(false);
        }

        return rootView;

    }

    public LinkedList<Person> getListaPersonas() {
        return listaPersonas;
    }

    public void setListaPersonas(LinkedList<Person> listaPersonas) {
        this.listaPersonas = listaPersonas;
    }


    public void show(final int position)
    {

        final Dialog d = new Dialog(context);
        d.setTitle("Seleccione edad");
        d.setContentView(R.layout.dialog);
        Button b1 = (Button) d.findViewById(R.id.button1);
        Button b2 = (Button) d.findViewById(R.id.button2);
        final NumberPicker np = (NumberPicker) d.findViewById(R.id.numberPicker1);
        np.setMaxValue(80);
        np.setMinValue(0);
        np.setWrapSelectorWheel(false);
        np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                listaPersonas.get(position).setEdad(newVal);
                notifyDataSetChanged();


            }
        });
        b1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                d.dismiss();
            }
        });
        b2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                d.dismiss();
            }
        });
        d.show();


    }


    public void showGenders(final int position)
    {

        final Dialog d = new Dialog(context);
        d.setTitle("Seleccione edad");
        d.setContentView(R.layout.dialog);
        Button b1 = (Button) d.findViewById(R.id.button1);
        Button b2 = (Button) d.findViewById(R.id.button2);
        final NumberPicker np = (NumberPicker) d.findViewById(R.id.numberPicker1);
        np.setMinValue(0);
        np.setMaxValue(1);
        np.setDisplayedValues( new String[] { "M", "F" } );
        np.setWrapSelectorWheel(false);
        np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                if(newVal == 0){
                    listaPersonas.get(position).setGenero(HOMBRE);
                }else{
                    listaPersonas.get(position).setGenero(MUJER);
                }
                notifyDataSetChanged();


            }
        });
        b1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                d.dismiss();
            }
        });
        b2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                d.dismiss();
            }
        });
        d.show();


    }




}
