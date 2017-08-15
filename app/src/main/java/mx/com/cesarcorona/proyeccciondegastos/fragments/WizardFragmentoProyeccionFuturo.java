package mx.com.cesarcorona.proyeccciondegastos.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stepstone.stepper.Step;
import com.stepstone.stepper.VerificationError;

import java.util.LinkedList;

import mx.com.cesarcorona.proyeccciondegastos.R;
import mx.com.cesarcorona.proyeccciondegastos.pojo.Person;

/**
 * Created by ccabrera on 11/08/17.
 */

public class WizardFragmentoProyeccionFuturo extends Fragment implements Step {


    public  static  String TAG = WizardFragmentoProyeccionFuturo.class.getSimpleName();

    private LinkedList<Person> persons;
    private double primaAnual;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.wizard_fragment_proyeccion, container, false);

        return v;
    }

    @Override
    public VerificationError verifyStep() {
        //return null if the user can go to the next step, create a new VerificationError instance otherwise
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

    public double getPrimaAnual() {
        return primaAnual;
    }

    public void setPrimaAnual(double primaAnual) {
        this.primaAnual = primaAnual;
    }

    public LinkedList<Person> getPersons() {
        return persons;
    }

    public void setPersons(LinkedList<Person> persons) {
        this.persons = persons;
    }

    public void calculateProyeccion(){

    }
}