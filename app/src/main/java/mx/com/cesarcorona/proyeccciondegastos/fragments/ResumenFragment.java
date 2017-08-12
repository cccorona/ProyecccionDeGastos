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

/**
 * Created by ccabrera on 08/08/17.
 */

public class ResumenFragment extends Fragment {


    public static String TAG = ResumenFragment.class.getSimpleName();



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.fragment_resumen, container, false);

        return rootView;
    }


}
