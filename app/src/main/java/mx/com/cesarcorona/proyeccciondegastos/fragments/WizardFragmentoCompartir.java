package mx.com.cesarcorona.proyeccciondegastos.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.stepstone.stepper.Step;
import com.stepstone.stepper.VerificationError;

import mx.com.cesarcorona.proyeccciondegastos.R;

/**
 * Created by ccabrera on 11/08/17.
 */

public class WizardFragmentoCompartir extends Fragment implements Step , NumberPicker.OnValueChangeListener{

    private EditText recivirEmail, nombreCompleto, correoElectronico;
    private OnshareInterface onshareInterface;

    public interface OnshareInterface{
        void OnShareSelected(String mail,String nombreCompleto);
    }

    public void setOnshareInterface(OnshareInterface onshareInterface) {
        this.onshareInterface = onshareInterface;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.wizard_fragment_compartir, container, false);
        recivirEmail = (EditText) v.findViewById(R.id.recibir_email);
        recivirEmail.setText("SI");
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
        VerificationError error =null;
        if(recivirEmail.getText().toString().equalsIgnoreCase("SI")){
            if(nombreCompleto.getText().length()<1 || correoElectronico.getText().length() < 1){
                Toast.makeText(getActivity(),"Debe rellenar todos los campos si desea que la proyección se " +
                        "mande a su correo",Toast.LENGTH_LONG).show();
                error  = new VerificationError("Debe rellenar todos los campos si desea que la proyección se " +
                        "mande a su correo");

            }else{
                if(!isValidEmail(correoElectronico.getText().toString())){
                    error  = new VerificationError("El correo electronico no tiene un formato valido");
                }else{
                    if(onshareInterface!= null){
                        onshareInterface.OnShareSelected(correoElectronico.getText().toString(),
                                nombreCompleto.getText().toString());
                    }
                }

            }
        }else{

        }
        return error;
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


    public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
}