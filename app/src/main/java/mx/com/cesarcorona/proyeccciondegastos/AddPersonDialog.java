package mx.com.cesarcorona.proyeccciondegastos;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.graphics.ColorUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jesusm.holocircleseekbar.lib.HoloCircleSeekBar;

import mx.com.cesarcorona.proyeccciondegastos.pojo.Persona;

/**
 * Created by ccabrera on 08/08/17.
 */





public class AddPersonDialog extends DialogFragment {


    public static String TAG = AddPersonDialog.class.getSimpleName();
    public static String KEY_IMAGE = "image";
    public static String IMAGE_PATH="path";
    public static String KEY_PERSON = "persona";



    private Persona personaToAdd;

    private static int MALE = 1;
    private static int FEMALE = 0;

    private int genderSelected;
    private int ageSelected;
    private String name;

    private OnAddPersonInterface onAddPersonInterface;


    public  interface OnAddPersonInterface{
        void OnPersonAdded(Persona persona);
    }

    public void setOnAddPersonInterface(OnAddPersonInterface onAddPersonInterface) {
        this.onAddPersonInterface = onAddPersonInterface;
    }

    public AddPersonDialog(){

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
         personaToAdd = (Persona) getArguments().getSerializable(KEY_PERSON);
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if(personaToAdd == null){
            personaToAdd = new Persona(30,MALE);
        }
        Dialog fulldialog = new Dialog(getContext(), R.style.FullScreenDialog);
        fulldialog.setContentView(R.layout.add_person_dialog_layout);
        final EditText nameEditText = (EditText) fulldialog.findViewById(R.id.text_name);
        final TextView maleText = (TextView) fulldialog.findViewById(R.id.tex_gender_male);
        final TextView femaleText = (TextView) fulldialog.findViewById(R.id.tex_gender_female);

        nameEditText.setText(personaToAdd.getName());
        Button cancelarButton = (Button) fulldialog.findViewById(R.id.cancel_button);
        HoloCircleSeekBar  ageSelector = (HoloCircleSeekBar) fulldialog.findViewById(R.id.picker);
        ageSelector.setInitPosition(personaToAdd.getAge());
        ageSelector.setOnSeekBarChangeListener(new HoloCircleSeekBar.OnCircleSeekBarChangeListener() {
            @Override
            public void onProgressChanged(HoloCircleSeekBar holoCircleSeekBar, int i, boolean b) {
                ageSelected = i;
            }

            @Override
            public void onStartTrackingTouch(HoloCircleSeekBar holoCircleSeekBar) {

            }

            @Override
            public void onStopTrackingTouch(HoloCircleSeekBar holoCircleSeekBar) {

            }
        });

        final RelativeLayout maleSelector = (RelativeLayout) fulldialog.findViewById(R.id.male_selector);
        final ImageView maleButton = (ImageView) fulldialog.findViewById(R.id.male_button);

        final RelativeLayout femaleSelector = (RelativeLayout) fulldialog.findViewById(R.id.female_selector);
        final ImageView femaleButton = (ImageView) fulldialog.findViewById(R.id.female_button);
        femaleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                genderSelected = MALE;
                femaleSelector.setBackgroundResource(R.color.colorAccent);
                maleSelector.setBackgroundResource(R.color.colorWhite);
                maleText.setVisibility(View.GONE);
                femaleText.setVisibility(View.VISIBLE);


            }
        });

        maleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                genderSelected = MALE;
                maleSelector.setBackgroundResource(R.color.colorAccent);
                femaleSelector.setBackgroundResource(R.color.colorWhite);
                maleText.setVisibility(View.VISIBLE);
                femaleText.setVisibility(View.GONE);


            }
        });

        if(personaToAdd.getGender()==MALE){
            maleSelector.setBackgroundResource(R.color.colorAccent);
            femaleSelector.setBackgroundResource(R.color.colorWhite);
            maleText.setVisibility(View.VISIBLE);
            femaleText.setVisibility(View.GONE);
        }else{
            femaleSelector.setBackgroundResource(R.color.colorAccent);
            maleSelector.setBackgroundResource(R.color.colorWhite);
            maleText.setVisibility(View.GONE);
            femaleText.setVisibility(View.VISIBLE);
        }


        cancelarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        Button acepterButton = (Button) fulldialog.findViewById(R.id.acepter_button);
        acepterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = nameEditText.getText().toString();
                if(onAddPersonInterface != null){
                    personaToAdd.setName(name);
                    personaToAdd.setAge(ageSelected);
                    personaToAdd.setGender(genderSelected);
                    onAddPersonInterface.OnPersonAdded(personaToAdd);
                }
                dismiss();
            }
        });

        int width = ViewGroup.LayoutParams.MATCH_PARENT;
        int height = ViewGroup.LayoutParams.MATCH_PARENT;
        fulldialog.getWindow().setLayout(width, height);
        fulldialog.setCancelable(false);


        return fulldialog;
    }
}
