package mx.com.cesarcorona.proyeccciondegastos.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import jxl.Cell;
import jxl.NumberCell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Number;
import mx.com.cesarcorona.proyeccciondegastos.R;
import mx.com.cesarcorona.proyeccciondegastos.adapters.WizardAdapter;
import mx.com.cesarcorona.proyeccciondegastos.fragments.WizardFragmentoCompartir;
import mx.com.cesarcorona.proyeccciondegastos.fragments.WizardFragmentoPersonas;
import mx.com.cesarcorona.proyeccciondegastos.fragments.WizardFragmentoProyeccionFuturo;
import mx.com.cesarcorona.proyeccciondegastos.pojo.Person;

public class MainWizardActivity extends AppCompatActivity implements
        StepperLayout.StepperListener,
        WizardFragmentoPersonas.OnDataInformationInterface,WizardFragmentoCompartir.OnshareInterface {


    public final static int WIZARD_INTRO = 0;
    public final static int WIZARD_PERSONAS = 1;
    public final static int WIZARD_PROYECCION = 2;
    public final static int WIZARD_PROYECCION_PASADA = 3;
    public final static int WIZARD_COMPARTIR = 4;
    public final static int WIZARD_FINALIZAR = 5;


    private static final String EXCEL_FILE_LOCATION = "file:///assets/files/data.xlsx";



    private StepperLayout mStepperLayout;


    private LinkedList<Person> personasAseguradas;
    private double primaAnualM ;
    private String nombrec,correoElectronico;
    private String comentarios;
    private Workbook workbook = null;
    private Sheet familiaSheet,dataSheet;
    private LinkedHashMap<Integer,Double> saludTotal;
    private LinkedHashMap<Integer,Double> saludReal;
    private LinkedHashMap<Integer,Double> anos;
    private LinkedHashMap<Integer,Double> hombrePrima;
    private LinkedHashMap<Integer,Double> mujerPrima;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_wizard);
        mStepperLayout = (StepperLayout) findViewById(R.id.stepperLayout);
        mStepperLayout.setAdapter(new WizardAdapter(getSupportFragmentManager(), this));
        mStepperLayout.setListener(this);
        personasAseguradas = new LinkedList<>();
        saludTotal = new LinkedHashMap<>();
        saludReal = new LinkedHashMap<>();
        hombrePrima = new LinkedHashMap<>();
        anos = new LinkedHashMap<>();
        mujerPrima = new LinkedHashMap<>();


        loadData();



    }


        @Override
    public void onCompleted(View completeButton) {
        Toast.makeText(this, "onCompleted!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(VerificationError verificationError) {
        Toast.makeText(this, "onError! -> " + verificationError.getErrorMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStepSelected(int newStepPosition) {
        Toast.makeText(this, "onStepSelected! -> " + newStepPosition, Toast.LENGTH_SHORT).show();
        if(newStepPosition == WIZARD_PROYECCION){
                 WizardFragmentoProyeccionFuturo step = (WizardFragmentoProyeccionFuturo) mStepperLayout.getAdapter().findStep(WIZARD_PROYECCION);
                 step.setPersons(personasAseguradas);
                 step.setPrimaAnual(primaAnualM);
                 step.calculateProyeccion();

        }
    }

    @Override
    public void onReturn() {
        finish();
    }



    @Override
    public void OnFilledData(LinkedList<Person> persons, double primaAnual) {
        personasAseguradas = persons;
        primaAnualM = primaAnual;
    }

    @Override
    public void OnShareSelected(String mail, String nombreCompleto) {
        correoElectronico = mail;
        nombrec = nombreCompleto;
    }

    @Override
    public void onBackPressed() {

    }

    private void loadData(){
        try {
            InputStream is = getResources().openRawResource(R.raw.data);

            workbook = Workbook.getWorkbook(is);
            familiaSheet = workbook.getSheet("FAMILIA");
            int startCell = 5;
            int finishCell =25;
            while(startCell <= finishCell){
                Cell cell = null;
                cell = familiaSheet.getCell(14,startCell);
                saludTotal.put(cell.getRow(),((NumberCell)cell).getValue());
                cell = familiaSheet.getCell(15,startCell);
                saludReal.put(cell.getRow(),((NumberCell)cell).getValue());
                cell = familiaSheet.getCell(0,startCell);
                anos.put(cell.getRow(),((NumberCell)cell).getValue());
                startCell++;
            }

            dataSheet = workbook.getSheet("DATA");

            startCell =4;
            finishCell =103;
            while ((startCell <= finishCell)){
                Cell cell = null;
                Cell valueCell = null;
                cell = dataSheet.getCell(10,startCell);
                valueCell = dataSheet.getCell(17,startCell);
                hombrePrima.put((int)((NumberCell)cell).getValue(),((NumberCell)valueCell).getValue());
                valueCell = dataSheet.getCell(16,startCell);
                mujerPrima.put((int)((NumberCell)cell).getValue(),((NumberCell)valueCell).getValue());
                startCell++;

            }
         Log.e("HOLA.","BOLA");


        } catch (BiffException e) {
            Log.d("Inflacacion",e.getMessage());

        } catch (IOException e) {
            Log.d("Inflacacion",e.getMessage());
        }
    }

}
