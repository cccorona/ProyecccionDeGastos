package mx.com.cesarcorona.proyeccciondegastos.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.NumberPicker;

import com.google.gson.Gson;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.VerificationError;

import java.util.LinkedList;

import mx.com.cesarcorona.proyeccciondegastos.AddPersonDialog;
import mx.com.cesarcorona.proyeccciondegastos.R;
import mx.com.cesarcorona.proyeccciondegastos.adapters.PersonAdapter;
import mx.com.cesarcorona.proyeccciondegastos.adapters.PersonaAdapter;
import mx.com.cesarcorona.proyeccciondegastos.pojo.Person;

import static mx.com.cesarcorona.proyeccciondegastos.pojo.Person.HOMBRE;
import static mx.com.cesarcorona.proyeccciondegastos.pojo.Person.MUJER;

/**
 * Created by ccabrera on 11/08/17.
 */

public class WizardFragmentoPersonas extends Fragment implements Step {

    public static String TAG = WizardFragmentoPersonas.class.getSimpleName();
    public static String DATA = "data";
    public static String PRIMA ="prima";



    private OnDataInformationInterface onDataInformationInterface;
    private LinkedList<Person> personas;
    private PersonAdapter personAdapter ;
    private double primaAnual;
    private EditText primaAnualTextView;

    public interface  OnDataInformationInterface{
        void OnFilledData(LinkedList<Person> persons,double primaAnual);
    }

    public void setOnDataInformationInterface(OnDataInformationInterface onDataInformationInterface) {
        this.onDataInformationInterface = onDataInformationInterface;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        personas = new LinkedList<>();
        personAdapter = new PersonAdapter(personas,getActivity());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.wizard_fragment_personas, container, false);
        Button minusButton = (Button) v.findViewById(R.id.minus_button);
        Button plusButton = (Button) v.findViewById(R.id.plus_button);
        final EditText numPersons = (EditText) v.findViewById(R.id.num_persons);
        final ListView listPersoonas = (ListView)v.findViewById(R.id.list_personas);


        listPersoonas.setAdapter(personAdapter);
        minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(personas!= null && personas.size() >=1){
                    personas.removeLast();
                    numPersons.setText(""+personas.size());
                    personAdapter = new PersonAdapter(personas,getActivity());
                    listPersoonas.setAdapter(personAdapter);
                }
            }
        });

        plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Person personToAdd = new Person();
                    personas.add(personToAdd);
                    numPersons.setText(""+personas.size());
                    personAdapter = new PersonAdapter(personas,getActivity());
                    listPersoonas.setAdapter(personAdapter);
            }
        });

        primaAnualTextView = (EditText) v.findViewById(R.id.prima_anual);





        return v;
    }

    @Override
    public VerificationError verifyStep() {
        //return null if the user can go to the next step, create a new VerificationError instance otherwise
        personas = personAdapter.getListaPersonas();
        if(personas ==null || personas.size() ==0){
            return  new VerificationError("Ingrese al menos un asegurado");

        }else if(primaAnualTextView.length() == 0){
            return  new VerificationError("Ingrese su prima anual");
        }else if(Double.parseDouble(primaAnualTextView.getText().toString())<=0){
            return  new VerificationError("La actual prima debe ser mayor a 0");

        }

        for(Person personToValidate:personas){
            if(personToValidate.getEdad()<= 0 || personToValidate.getEdad()>= 80){
                return  new VerificationError("El asegurado debe tener entre 0 y 80 años");
            }else if(!personToValidate.getGenero().equalsIgnoreCase(HOMBRE) && !personToValidate.getGenero().equalsIgnoreCase(MUJER)){
                return  new VerificationError("El asegurado debe ser M para masculino o F para femenino");

            }
        }
        primaAnual = Double.parseDouble(primaAnualTextView.getText().toString());
        if(onDataInformationInterface!= null){
            onDataInformationInterface.OnFilledData(personas,primaAnual);
        }
        return null;
    }

    @Override
    public void onSelected() {
        //update UI when selected
    }

    @Override
    public void onError(@NonNull VerificationError error) {
        //handle error inside of the fragment, e.g. show error on EditText
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Gson jsonDAta = new Gson();
        outState.putString(DATA,jsonDAta.toJson(personas));
        outState.putDouble(PRIMA,primaAnual);
        super.onSaveInstanceState(outState);
    }






}