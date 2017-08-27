package mx.com.cesarcorona.proyeccciondegastos.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.stepstone.stepper.Step;
import com.stepstone.stepper.VerificationError;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;

import mx.com.cesarcorona.proyeccciondegastos.R;
import mx.com.cesarcorona.proyeccciondegastos.adapters.ProyeccionAdapter;
import mx.com.cesarcorona.proyeccciondegastos.adapters.ProyeccionPasadaAdapter;
import mx.com.cesarcorona.proyeccciondegastos.pojo.RowProyeccion;

/**
 * Created by ccabrera on 11/08/17.
 */

public class WizardFragmentProyeccionPasada extends Fragment implements Step {


    private LinkedList<RowProyeccion> proyeccions;
    private ProyeccionPasadaAdapter proyeccionAdapter;

    private ListView proyeccionList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.wizard_fragment_proyeccion_pasada, container, false);
        proyeccionList = (ListView) v.findViewById(R.id.proyeccion_lista);
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c.getTime());


        TextView date_proyeccion = (TextView) v.findViewById(R.id.date_proyeccion);
        date_proyeccion.setText(formattedDate);



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


    public LinkedList<RowProyeccion> getProyeccions() {
        return proyeccions;
    }

    public void setProyeccions(LinkedList<RowProyeccion> proyeccions) {
        this.proyeccions = proyeccions;
    }

    public ProyeccionPasadaAdapter getProyeccionAdapter() {
        return proyeccionAdapter;
    }

    public void setProyeccionAdapter(ProyeccionPasadaAdapter proyeccionAdapter) {
        this.proyeccionAdapter = proyeccionAdapter;
    }

    public void loadProyeccion(){
        proyeccionAdapter = new ProyeccionPasadaAdapter(proyeccions,getActivity());
        proyeccionList.setAdapter(proyeccionAdapter);

    }

}