package mx.com.cesarcorona.proyeccciondegastos.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.crash.FirebaseCrash;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.VerificationError;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import mx.com.cesarcorona.proyeccciondegastos.R;
import mx.com.cesarcorona.proyeccciondegastos.activities.MainWizardActivity;
import mx.com.cesarcorona.proyeccciondegastos.pojo.Comentario;

/**
 * Created by ccabrera on 11/08/17.
 */

public class WizardFragmentFinal extends Fragment implements Step {

    public static String TAG = WizardFragmentFinal.class.getSimpleName();
    public static final int SALIR = 1;
    public static final int AGAIN = 0;
    private static final int NOT_SELECTED = 9;
    private static final long SPLASH_SCREEN_DELAY = 1000;


    private int optionSelected = NOT_SELECTED;
    private LinearLayout salirLayout, againLayout;
    private EditText comentarionsText;
    private OnLastInterface lastInterface;

    private ProgressDialog pDialog;
    private Button enviarComentario;

    public static final String COMENTARIOS_REFERENCE = "comentarios";



    public void setLastInterface(OnLastInterface lastInterface) {
        this.lastInterface = lastInterface;
    }

    public interface  OnLastInterface{
        void OnFinalConfiguration(String comentary,int action);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.wizard_fragment_final, container, false);
        salirLayout = (LinearLayout) v.findViewById(R.id.linear_salir);
        againLayout = (LinearLayout) v.findViewById(R.id.linear_nueva_pro);
        comentarionsText = (EditText) v.findViewById(R.id.comentario_text);
        enviarComentario = (Button)v.findViewById(R.id.comentario_button) ;



        enviarComentario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showpDialog();
                sendComentarios();
            }
        });

        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Por favor espera...");
        pDialog.setCancelable(false);



        salirLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showpDialog();
                optionSelected = SALIR;
                finish();


            }
        });

        againLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showpDialog();
                optionSelected = AGAIN;
                beginAgain();

            }
        });

        return v;
    }

    @Override
    public VerificationError verifyStep() {
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


    private void beginAgain(){

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Thread.currentThread()
                        .setName(this.getClass().getSimpleName() + ": " + Thread.currentThread().getName());
                hidepDialog();
                    Intent mainActivityntent = new Intent(getActivity(),MainWizardActivity.class);
                    startActivity(mainActivityntent);
                getActivity().finish();



            }
        };

        Timer timer = new Timer();
        timer.schedule(task, 2000);



    }


    private void finish(){

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Thread.currentThread()
                        .setName(this.getClass().getSimpleName() + ": " + Thread.currentThread().getName());
                hidepDialog();
                getActivity().finish();



            }
        };

        Timer timer = new Timer();
        timer.schedule(task, 2000);



    }





    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }


    private void sendComentarios(){


        final DatabaseReference database = FirebaseDatabase.getInstance().getReference(COMENTARIOS_REFERENCE);

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Toast.makeText(getActivity(),"Comentario enviado",Toast.LENGTH_LONG).show();
                hidepDialog();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                FirebaseCrash.log("Error al mandar comentariosn:" + databaseError.getMessage());
                hidepDialog();

            }
        });


        Comentario comentario = new Comentario();
        comentario.setComentario(comentarionsText.getText().toString());

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c.getTime());
        comentario.setFecha(formattedDate);
        database.push().setValue(comentario);






    }



}