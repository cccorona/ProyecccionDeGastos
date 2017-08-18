package mx.com.cesarcorona.proyeccciondegastos.adapters;

import android.content.Context;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;

import com.stepstone.stepper.Step;
import com.stepstone.stepper.adapter.AbstractFragmentStepAdapter;
import com.stepstone.stepper.viewmodel.StepViewModel;

import mx.com.cesarcorona.proyeccciondegastos.R;
import mx.com.cesarcorona.proyeccciondegastos.fragments.WizardFragmentFinal;
import mx.com.cesarcorona.proyeccciondegastos.fragments.WizardFragmentIntro;
import mx.com.cesarcorona.proyeccciondegastos.fragments.WizardFragmentProyeccionPasada;
import mx.com.cesarcorona.proyeccciondegastos.fragments.WizardFragmentoCompartir;
import mx.com.cesarcorona.proyeccciondegastos.fragments.WizardFragmentoPersonas;
import mx.com.cesarcorona.proyeccciondegastos.fragments.WizardFragmentoProyeccionFuturo;

import static mx.com.cesarcorona.proyeccciondegastos.activities.MainWizardActivity.WIZARD_COMPARTIR;
import static mx.com.cesarcorona.proyeccciondegastos.activities.MainWizardActivity.WIZARD_FINALIZAR;
import static mx.com.cesarcorona.proyeccciondegastos.activities.MainWizardActivity.WIZARD_INTRO;
import static mx.com.cesarcorona.proyeccciondegastos.activities.MainWizardActivity.WIZARD_PERSONAS;
import static mx.com.cesarcorona.proyeccciondegastos.activities.MainWizardActivity.WIZARD_PROYECCION;
import static mx.com.cesarcorona.proyeccciondegastos.activities.MainWizardActivity.WIZARD_PROYECCION_PASADA;

/**
 * Created by ccabrera on 11/08/17.
 */

public class WizardAdapter extends AbstractFragmentStepAdapter {



    private Context mContext;

    public WizardAdapter(FragmentManager fm, Context context) {
        super(fm, context);
        this.mContext = context;
    }

    @Override
    public Step createStep(int position) {

        Step step = null;

        switch (position){
            case WIZARD_INTRO:
             step = new WizardFragmentIntro();
                break;
            case WIZARD_PERSONAS:
                step = new WizardFragmentoPersonas();
                ((WizardFragmentoPersonas)step).setOnDataInformationInterface((WizardFragmentoPersonas.OnDataInformationInterface) mContext);
                break;
            case WIZARD_PROYECCION:
                step = new WizardFragmentoProyeccionFuturo();
                break;
            case WIZARD_PROYECCION_PASADA:
                step = new WizardFragmentProyeccionPasada();
                break;
            case WIZARD_COMPARTIR:
                step = new WizardFragmentoCompartir();
                ((WizardFragmentoCompartir)step).setOnshareInterface((WizardFragmentoCompartir.OnshareInterface)mContext);

                break;
            case WIZARD_FINALIZAR:
                step = new WizardFragmentFinal();
                break;
        }

        return step;

    }

    @Override
    public int getCount() {
        return 6;
    }

    @NonNull
    @Override
    public StepViewModel getViewModel(@IntRange(from = 0) int position) {
        //Override this method to set Step title for the Tabs, not necessary for other stepper types
        return new StepViewModel.Builder(context)
                .setTitle("Test") //can be a CharSequence instead
                .create();
    }
}