package mx.com.cesarcorona.proyeccciondegastos.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.crash.FirebaseCrash;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;


import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;



import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;


import mx.com.cesarcorona.proyeccciondegastos.R;
import mx.com.cesarcorona.proyeccciondegastos.adapters.WizardAdapter;
import mx.com.cesarcorona.proyeccciondegastos.fragments.WizardFragmentFinal;
import mx.com.cesarcorona.proyeccciondegastos.fragments.WizardFragmentProyeccionPasada;
import mx.com.cesarcorona.proyeccciondegastos.fragments.WizardFragmentoCompartir;
import mx.com.cesarcorona.proyeccciondegastos.fragments.WizardFragmentoPersonas;
import mx.com.cesarcorona.proyeccciondegastos.fragments.WizardFragmentoProyeccionFuturo;
import mx.com.cesarcorona.proyeccciondegastos.pojo.Comentario;
import mx.com.cesarcorona.proyeccciondegastos.pojo.Person;
import mx.com.cesarcorona.proyeccciondegastos.pojo.RowProyeccion;

import static mx.com.cesarcorona.proyeccciondegastos.fragments.WizardFragmentFinal.AGAIN;
import static mx.com.cesarcorona.proyeccciondegastos.fragments.WizardFragmentFinal.SALIR;

public class MainWizardActivity extends AppCompatActivity implements
        StepperLayout.StepperListener,
        WizardFragmentoPersonas.OnDataInformationInterface,WizardFragmentoCompartir.OnshareInterface,
        WizardFragmentoProyeccionFuturo.ProyectionInterface,WizardFragmentFinal.OnLastInterface {


    public final static int WIZARD_INTRO = 0;
    public final static int WIZARD_PERSONAS = 1;
    public final static int WIZARD_PROYECCION = 2;
    public final static int WIZARD_PROYECCION_PASADA = 3;
    public final static int WIZARD_COMPARTIR = 4;
    public final static int WIZARD_FINALIZAR = 5;

    private final static int EMAIL_REQUEST =600;



    private LinkedList<RowProyeccion> rowProyeccions;

    private static final String EXCEL_FILE_LOCATION = "file:///assets/files/data.xlsx";
    public static final String COMENTARIOS_REFERENCE = "comentarios";



    private StepperLayout mStepperLayout;


    private LinkedList<Person> personasAseguradas;
    private double primaAnualM ;
    private String nombrec,correoElectronico;
    private String comentarios;
    private int finalOption;
    private String pdfFile ;

    private ProgressDialog pDialog;








    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_wizard);
        mStepperLayout = (StepperLayout) findViewById(R.id.stepperLayout);
        mStepperLayout.setAdapter(new WizardAdapter(getSupportFragmentManager(), this));
        mStepperLayout.setListener(this);
        personasAseguradas = new LinkedList<>();
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Por favor espera...");
        pDialog.setCancelable(false);





    }


        @Override
    public void onCompleted(View completeButton) {
            showpDialog();
            performFinalActions();
            hidepDialog();

    }

    @Override
    public void onError(VerificationError verificationError) {
        Toast.makeText(this, "onError! -> " + verificationError.getErrorMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStepSelected(int newStepPosition) {
       // Toast.makeText(this, "onStepSelected! -> " + newStepPosition, Toast.LENGTH_SHORT).show();
        if(newStepPosition == WIZARD_PROYECCION){
                 WizardFragmentoProyeccionFuturo step = (WizardFragmentoProyeccionFuturo) mStepperLayout.getAdapter().findStep(WIZARD_PROYECCION);
                 step.setPersons(personasAseguradas);
                 step.setPrimaAnual(primaAnualM);
                 step.calculateProyeccion();

        }else if(newStepPosition == WIZARD_PROYECCION_PASADA){
            WizardFragmentProyeccionPasada step = (WizardFragmentProyeccionPasada) mStepperLayout.getAdapter().findStep(WIZARD_PROYECCION_PASADA);
            step.setProyeccions(rowProyeccions);
            step.loadProyeccion();
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





    @Override
    public void OnProyectionFinished(LinkedList<RowProyeccion> proyeccions) {
        rowProyeccions = proyeccions;
    }

    @Override
    public void OnFinalConfiguration(String comentary, int action) {
        comentarios = comentary;
        finalOption = action;
    }

    private void performFinalActions(){


        if(correoElectronico != null){
            if(generatePDF()){
                sendEmailWithProyection();
            }

        }








    }

    private boolean generatePDF(){
        boolean OK = true;
        File sdCard = Environment.getExternalStorageDirectory();
        String path = sdCard.getAbsolutePath() + "/temp/proyeccion.pdf";
        File file = new File(path);
        Document documento = new Document();
        try {
            file.getParentFile().mkdir();
            file.createNewFile();
            FileOutputStream ficheroPdf = new FileOutputStream(file);
            // Se asocia el documento al OutputStream y se indica que el espaciado entre
            // lineas sera de 20. Esta llamada debe hacerse antes de abrir el documento
            PdfWriter.getInstance(documento,ficheroPdf).setInitialLeading(20);

            // Se abre el documento.
            documento.open();

            documento.add(new Paragraph("PROYECCION DE PRIMAS DE GASTOS MEDICOS ",
                    FontFactory.getFont("arial",   // fuente
                            22,                            // tamaño
                            Font.ITALIC,                   // estilo
                            BaseColor.CYAN)));             // color

            Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.janem_ogo);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();



            Image foto = Image.getInstance(byteArray);
            foto.scaleToFit(100, 100);
            foto.setAlignment(Chunk.ALIGN_MIDDLE);
            documento.add(foto);


            PdfPTable tabla = new PdfPTable(3);
            tabla.addCell("Año");
            tabla.addCell("Primas");
            tabla.addCell("Pesos 2017");


            for(RowProyeccion fila :rowProyeccions){
                tabla.addCell(""+fila.getAno());
                tabla.addCell(""+fila.getPesos());
                tabla.addCell(""+fila.getPesos2017());

            }
            documento.add(tabla);
            pdfFile = file.getAbsolutePath();
            documento.close();





        } catch (FileNotFoundException e) {
            FirebaseCrash.report(e);
            OK = false;
        } catch (DocumentException e) {
            FirebaseCrash.report(e);
            OK = false;
        } catch (MalformedURLException e) {
            FirebaseCrash.report(e);
            OK =false ;
        } catch (IOException e) {
            FirebaseCrash.report(e);
            OK = false;
        }

        return  OK;
    }

    private void sendEmailWithProyection(){
        File filelocation = new File(pdfFile);
        Uri path = Uri.fromFile(filelocation);
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent .setType("vnd.android.cursor.dir/email");
        String to[] = {correoElectronico};
        emailIntent .putExtra(Intent.EXTRA_EMAIL, to);
        emailIntent .putExtra(Intent.EXTRA_STREAM, path);
        emailIntent .putExtra(Intent.EXTRA_SUBJECT, "Proyeccion" +nombrec);
        startActivityForResult(Intent.createChooser(emailIntent , "Mandar correo..."),EMAIL_REQUEST);
    }

    private void sendComentarios(){


        final DatabaseReference database = FirebaseDatabase.getInstance().getReference(COMENTARIOS_REFERENCE);

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                 finisOrStart();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                FirebaseCrash.log("Error al mandar comentariosn:" + databaseError.getMessage());

            }
        });


        Comentario comentario = new Comentario();
        comentario.setComentario(comentarios);
        if(correoElectronico != null){
            comentario.setCorreoelectronico(correoElectronico);
        }
        if(nombrec != null){
            comentario.setNombrePersona(nombrec);
        }
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c.getTime());
        comentario.setFecha(formattedDate);
        database.push().setValue(comentario);






    }


    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }


    private void resetAll(){
        personasAseguradas = new LinkedList<>();
        correoElectronico = null ;

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == EMAIL_REQUEST){
            if(comentarios != null){
                sendComentarios();

            }else{
                finisOrStart();
            }
        }
    }



    private void finisOrStart(){

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Thread.currentThread()
                        .setName(this.getClass().getSimpleName() + ": " + Thread.currentThread().getName());
                if(finalOption == AGAIN){
                    resetAll();
                    Intent mainActivityntent = new Intent(MainWizardActivity.this,MainWizardActivity.class);
                    startActivity(mainActivityntent);
                    finish();
                }else{
                    finish();
                }


            }
        };

        Timer timer = new Timer();
        timer.schedule(task, 2000);



    }




}
