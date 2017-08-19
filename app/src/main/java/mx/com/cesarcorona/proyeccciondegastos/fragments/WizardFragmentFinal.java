package mx.com.cesarcorona.proyeccciondegastos.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.stepstone.stepper.Step;
import com.stepstone.stepper.VerificationError;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import mx.com.cesarcorona.proyeccciondegastos.R;

/**
 * Created by ccabrera on 11/08/17.
 */

public class WizardFragmentFinal extends Fragment implements Step {

    public static String TAG = WizardFragmentFinal.class.getSimpleName();
    public static final int SALIR = 1;
    private static final int AGAIN = 0;
    private static final long SPLASH_SCREEN_DELAY = 1000;


    private int optionSelected;
    private LinearLayout salirLayout, againLayout;
    private EditText comentarionsText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.wizard_fragment_final, container, false);
        salirLayout = (LinearLayout) v.findViewById(R.id.linear_salir);
        againLayout = (LinearLayout) v.findViewById(R.id.linear_nueva_pro);

        salirLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                optionSelected = SALIR;
                salirLayout.setBackgroundResource(R.color.colorAccent);
                againLayout.setBackgroundResource(R.color.colorPrimaryDark);
            }
        });

        againLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                optionSelected = AGAIN;
                againLayout.setBackgroundResource(R.color.colorAccent);
                salirLayout.setBackgroundResource(R.color.colorPrimaryDark);
            }
        });

        return v;
    }

    @Override
    public VerificationError verifyStep() {
        if(comentarionsText.getText().length()>0){
            //send Email ;
        }
        if(optionSelected == SALIR){
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    Thread.currentThread()
                            .setName(this.getClass().getSimpleName() + ": " + Thread.currentThread().getName());
                    getActivity().finish();

                }
            };

            Timer timer = new Timer();
            timer.schedule(task, SPLASH_SCREEN_DELAY);
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

}