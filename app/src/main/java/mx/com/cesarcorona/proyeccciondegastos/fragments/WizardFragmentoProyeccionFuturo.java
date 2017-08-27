package mx.com.cesarcorona.proyeccciondegastos.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.stepstone.stepper.Step;
import com.stepstone.stepper.VerificationError;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import jxl.Cell;
import jxl.NumberCell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import mx.com.cesarcorona.proyeccciondegastos.R;
import mx.com.cesarcorona.proyeccciondegastos.adapters.ProyeccionAdapter;
import mx.com.cesarcorona.proyeccciondegastos.pojo.Person;
import mx.com.cesarcorona.proyeccciondegastos.pojo.RowProyeccion;

import static mx.com.cesarcorona.proyeccciondegastos.pojo.Person.HOMBRE;

/**
 * Created by ccabrera on 11/08/17.
 */

public class WizardFragmentoProyeccionFuturo extends Fragment implements Step {


    public  static  String TAG = WizardFragmentoProyeccionFuturo.class.getSimpleName();

    private LinkedList<Person> persons;
    private LinkedList<RowProyeccion> proyeccions;
    private ProyeccionAdapter proyeccionAdapter;
    private double primaAnual;
    private double primaCalculada;
    private double relacionEntrePrimas;
    private static final int CURRENT_YEAR = 2017;



    private Workbook workbook = null;
    private Sheet familiaSheet,dataSheet;
    private LinkedHashMap<Integer,Double> saludTotal;
    private LinkedHashMap<Integer,Double> saludReal;
    private LinkedHashMap<Integer,Integer> anos;
    private LinkedHashMap<Integer,Double> hombrePrima;
    private LinkedHashMap<Integer,Double> mujerPrima;
    private LinkedHashMap<Integer,Double> hombrePrimaOld;
    private LinkedHashMap<Integer,Double> mujerPrimaOld;
    private LinkedHashMap<Integer,Double> primasTotalPorAno;
    private LinkedHashMap<Integer,Double> primasTotalSaludTotal;
    private LinkedHashMap<Integer,Double> primasTotalSaludReal;


    private ProgressDialog pDialog;
    private ListView proyeccionList;
    private ProyectionInterface proyectionInterface;


    public void setProyectionInterface(ProyectionInterface proyectionInterface) {
        this.proyectionInterface = proyectionInterface;
    }

    public interface ProyectionInterface{
        void OnProyectionFinished(LinkedList<RowProyeccion> proyeccions);
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        saludTotal = new LinkedHashMap<>();
        saludReal = new LinkedHashMap<>();
        hombrePrima = new LinkedHashMap<>();
        anos = new LinkedHashMap<>();
        mujerPrima = new LinkedHashMap<>();
        hombrePrimaOld = new LinkedHashMap<>();
        mujerPrimaOld = new LinkedHashMap<>();
        primasTotalPorAno = new LinkedHashMap<>();
        primasTotalSaludReal = new LinkedHashMap<>();
        primasTotalSaludTotal = new LinkedHashMap<>();
        primasTotalPorAno = new LinkedHashMap<>();
        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Por favor espera...");
        pDialog.setCancelable(false);
        proyeccions = new LinkedList<>();




        //loadData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.wizard_fragment_proyeccion, container, false);
        proyeccionList = (ListView) v.findViewById(R.id.proyeccion_lista);
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c.getTime());


        TextView date_proyeccion = (TextView) v.findViewById(R.id.date_proyeccion);
        date_proyeccion.setText(formattedDate);


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

    public double getPrimaAnual() {
        return primaAnual;
    }

    public void setPrimaAnual(double primaAnual) {
        this.primaAnual = primaAnual;
    }

    public LinkedList<Person> getPersons() {
        return persons;
    }

    public void setPersons(LinkedList<Person> persons) {
        this.persons = persons;
    }

    public void calculateProyeccion(){
           showpDialog();
           loadData();
           for(Person person:persons){
               if(person.getGenero().equalsIgnoreCase(HOMBRE)){
                   person.setPrimaCalculada(hombrePrimaOld.get(person.getEdad()));
               }else{
                   person.setPrimaCalculada(mujerPrimaOld.get(person.getEdad()));

               }
               primaCalculada+= person.getPrimaCalculada();
           }

           relacionEntrePrimas = primaCalculada /primaAnual ;

           for(Person person:persons){
               for(Integer year:anos.keySet()){
                   Integer keyYearToSearch = person.getEdad() +anos.get(year) - CURRENT_YEAR ;
                   if(person.getGenero().equalsIgnoreCase(HOMBRE)){
                       person.agregarPrimaPorAno(anos.get(year),hombrePrima.get(keyYearToSearch)/relacionEntrePrimas);
                   }else{
                       person.agregarPrimaPorAno(anos.get(year),mujerPrima.get(keyYearToSearch)/relacionEntrePrimas);

                   }
               }
           }

           for(Integer year:anos.keySet()){
               double sumaTotalPorAno = 0;
               for(Person person:persons){
                   sumaTotalPorAno+= person.getPrimaPorAño().get(anos.get(year));
               }
               primasTotalPorAno.put(anos.get(year),sumaTotalPorAno);
           }

           boolean isFirst = true;
           int initialKey = 5;
           for(Integer ano:primasTotalPorAno.keySet()){

               if(isFirst){
                   primasTotalSaludTotal.put(ano,primasTotalPorAno.get(ano));
                   primasTotalSaludReal.put(ano,primasTotalPorAno.get(ano));
                   isFirst = false ;
               }else {
                   primasTotalSaludTotal.put(ano,primasTotalPorAno.get(ano) * saludTotal.get(initialKey));
                   primasTotalSaludReal.put(ano,primasTotalPorAno.get(ano) * saludReal.get(initialKey));

                   initialKey ++ ;
               }

           }


           for(Integer key:primasTotalSaludTotal.keySet()){
             proyeccions.add(new RowProyeccion(primasTotalSaludTotal.get(key),primasTotalSaludReal.get(key),
                     key));
           }

           proyeccionAdapter = new ProyeccionAdapter(proyeccions,getActivity());
           proyeccionList.setAdapter(proyeccionAdapter);



       hidepDialog();

        if(proyectionInterface!= null){
            proyectionInterface.OnProyectionFinished(proyeccions);
        }

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
                anos.put(cell.getRow(),(int)((NumberCell)cell).getValue());
                startCell++;
            }

            dataSheet = workbook.getSheet("DATA");

            startCell =4;
            finishCell =103;
            while ((startCell <= finishCell)){
                Cell cell = null;
                Cell valueCell = null;
                cell = dataSheet.getCell(10,startCell);
                valueCell = dataSheet.getCell(13,startCell);//For future
                hombrePrima.put((int)((NumberCell)cell).getValue(),((NumberCell)valueCell).getValue());
                valueCell = dataSheet.getCell(14,startCell);
                mujerPrima.put((int)((NumberCell)cell).getValue(),((NumberCell)valueCell).getValue());
                valueCell = dataSheet.getCell(20,startCell);
                mujerPrimaOld.put((int)((NumberCell)cell).getValue(),((NumberCell)valueCell).getValue());
                valueCell = dataSheet.getCell(19,startCell);
                hombrePrimaOld.put((int)((NumberCell)cell).getValue(),((NumberCell)valueCell).getValue());
                startCell++;

            }
            Log.e("HOLA.","BOLA");


        } catch (BiffException e) {
            Log.d("Inflacacion",e.getMessage());

        } catch (IOException e) {
            Log.d("Inflacacion",e.getMessage());
        }
    }


    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }


}