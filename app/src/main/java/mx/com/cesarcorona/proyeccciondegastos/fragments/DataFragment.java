package mx.com.cesarcorona.proyeccciondegastos.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mx.com.cesarcorona.proyeccciondegastos.R;

/**
 * Created by ccabrera on 08/08/17.
 */

public class DataFragment extends Fragment {


    public static String TAG = DataFragment.class.getSimpleName();



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.data_fragment, container, false);

        return rootView;
    }


}