package mx.com.cesarcorona.proyeccciondegastos.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;

import com.stepstone.stepper.Step;
import com.stepstone.stepper.VerificationError;

import mx.com.cesarcorona.proyeccciondegastos.R;

/**
 * Created by ccabrera on 11/08/17.
 */

public class WizardFragmentoCompartir extends Fragment implements Step , NumberPicker.OnValueChangeListener{

    private EditText recivirEmail, nombreCompleto, correoElectronico;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.wizard_fragment_compartir, container, false);
        recivirEmail = (EditText) v.findViewById(R.id.recibir_email);
        nombreCompleto = (EditText) v.findViewById(R.id.nombre_completo);
        correoElectronico = (EditText) v.findViewById(R.id.email);

        recivirEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOptions();
            }
        });


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




    public void showOptions()
    {

        final Dialog d = new Dialog(getActivity());
        d.setTitle("Seleccione:");
        d.setContentView(R.layout.dialog);
        Button b1 = (Button) d.findViewById(R.id.button1);
        Button b2 = (Button) d.findViewById(R.id.button2);
        final NumberPicker np = (NumberPicker) d.findViewById(R.id.numberPicker1);
        np.setMinValue(0);
        np.setMaxValue(1);
        np.setDisplayedValues( new String[] { "SI", "NO" } );
        np.setWrapSelectorWheel(false);
        np.setOnValueChangedListener(this);
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


    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
           if(newVal == 0){
               recivirEmail.setText("SI");
           }else{
               recivirEmail.setText("NO");

           }
    }
}