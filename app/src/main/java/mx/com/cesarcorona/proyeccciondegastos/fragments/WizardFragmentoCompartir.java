package mx.com.cesarcorona.proyeccciondegastos.fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.google.firebase.crash.FirebaseCrash;
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
import com.stepstone.stepper.Step;
import com.stepstone.stepper.VerificationError;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.NumberFormat;
import java.util.LinkedList;
import java.util.Locale;

import mx.com.cesarcorona.proyeccciondegastos.R;
import mx.com.cesarcorona.proyeccciondegastos.pojo.RowProyeccion;

/**
 * Created by ccabrera on 11/08/17.
 */

public class WizardFragmentoCompartir extends Fragment implements Step , NumberPicker.OnValueChangeListener{

    private EditText recivirEmail, nombreCompleto, correoElectronico;
    private OnshareInterface onshareInterface;
    private LinkedList<RowProyeccion> proyeccions;
    private String pdfFile ;
    private final static int EMAIL_REQUEST =600;
    private ProgressDialog pDialog;
    private Button enviarCorreo;





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
        enviarCorreo = (Button)v.findViewById(R.id.enviarCorreo);

        recivirEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOptions();
            }
        });

        enviarCorreo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(recivirEmail.getText().toString().equalsIgnoreCase("SI")){
                    if(nombreCompleto.getText().length()<1 || correoElectronico.getText().length() < 1){
                        Toast.makeText(getActivity(),"Debe rellenar todos los campos si desea que la proyección se " +
                                "mande a su correo",Toast.LENGTH_LONG).show();





                    }else{
                        if(!isValidEmail(correoElectronico.getText().toString())){
                            Toast.makeText(getActivity(),"El correo electronico no tiene un formato valido",Toast.LENGTH_LONG).show();
                        }else{
                            showpDialog();

                            if(generatePDF()){
                                sendEmailWithProyection();
                            }

                        }

                    }
                }else{

                }

            }
        });


        return v;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Por favor espera...");
        pDialog.setCancelable(false);
    }

    @Override
    public VerificationError verifyStep() {
        //return null if the user can go to the next step, create a new VerificationError instance otherwise
        VerificationError error =null;

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


    public LinkedList<RowProyeccion> getProyeccions() {
        return proyeccions;
    }

    public void setProyeccions(LinkedList<RowProyeccion> proyeccions) {
        this.proyeccions = proyeccions;
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


            for(RowProyeccion fila :proyeccions){
                tabla.addCell(""+fila.getAno());
                tabla.addCell("$"+ NumberFormat.getNumberInstance(Locale.US).format((int)fila.getPesos()));
                tabla.addCell("$"+NumberFormat.getNumberInstance(Locale.US).format((int)fila.getPesos2017()));

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
        String to[] = {correoElectronico.getText().toString()};
        emailIntent .putExtra(Intent.EXTRA_EMAIL, to);
        emailIntent .putExtra(Intent.EXTRA_STREAM, path);
        emailIntent .putExtra(Intent.EXTRA_SUBJECT, "Proyeccion: " +nombreCompleto.getText().toString());
        startActivityForResult(Intent.createChooser(emailIntent , "Mandar correo..."),EMAIL_REQUEST);
    }


    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == EMAIL_REQUEST){
            hidepDialog();
            hideSoftKeyboard();
            Toast.makeText(getActivity(),"Correo enviado",Toast.LENGTH_LONG).show();
        }
    }


    public void hideSoftKeyboard() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);


    }


}