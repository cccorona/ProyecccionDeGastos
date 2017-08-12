package mx.com.cesarcorona.proyeccciondegastos.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.LinkedList;

import mx.com.cesarcorona.proyeccciondegastos.AddPersonDialog;
import mx.com.cesarcorona.proyeccciondegastos.R;
import mx.com.cesarcorona.proyeccciondegastos.adapters.PersonaAdapter;
import mx.com.cesarcorona.proyeccciondegastos.pojo.Persona;


public class HomeFragment extends Fragment implements PersonaAdapter.OnActionPersonInterface {


    public static String TAG = HomeFragment.class.getSimpleName();

    private LinkedList<Persona> personas;
    private ListView listPersonas;
    private PersonaAdapter personaAdapter;
    private TextView noInfoTextView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        personas = new LinkedList<>();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.fragment_home, container, false);
        listPersonas = (ListView) rootView.findViewById(R.id.listview_persons);
        noInfoTextView = (TextView) rootView.findViewById(R.id.no_info_textview);
        personaAdapter = new PersonaAdapter(personas,getActivity());
        personaAdapter.setOnActionPersonInterface(HomeFragment.this);
        listPersonas.setAdapter(personaAdapter);
        return rootView;
    }


    public void addPerson(Persona personaToAdd){
        personas.add(personaToAdd);
        personaAdapter = new PersonaAdapter(personas,getActivity());
        personaAdapter.setOnActionPersonInterface(HomeFragment.this);

        listPersonas.setAdapter(personaAdapter);
        if(personas.size()>0){
            noInfoTextView.setVisibility(View.GONE);
            listPersonas.setVisibility(View.VISIBLE);
        }
    }



    @Override
    public void OnEditPerson(Persona persona) {
        AddPersonDialog editPErsonDialog = new AddPersonDialog();
        Bundle arguments = new Bundle();
        arguments.putSerializable(AddPersonDialog.KEY_PERSON,persona);
        editPErsonDialog.setArguments(arguments);
        editPErsonDialog.show(getChildFragmentManager(),AddPersonDialog.TAG);
    }

    @Override
    public void OnDeletePersons(Persona persona) {
         personas.remove(persona);
        personaAdapter = new PersonaAdapter(personas,getActivity());
        personaAdapter.setOnActionPersonInterface(HomeFragment.this);
        listPersonas.setAdapter(personaAdapter);
        if(personas.size()==0){
            noInfoTextView.setVisibility(View.VISIBLE);
            listPersonas.setVisibility(View.GONE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
