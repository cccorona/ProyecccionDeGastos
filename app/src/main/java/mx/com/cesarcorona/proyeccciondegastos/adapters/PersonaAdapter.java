package mx.com.cesarcorona.proyeccciondegastos.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.util.LinkedList;

import mx.com.cesarcorona.proyeccciondegastos.AddPersonDialog;
import mx.com.cesarcorona.proyeccciondegastos.R;
import mx.com.cesarcorona.proyeccciondegastos.pojo.Persona;

/**
 * Created by ccabrera on 08/08/17.
 */

public class PersonaAdapter extends BaseAdapter {

    private LinkedList<Persona> personas;
    private Context context;
    private OnActionPersonInterface onActionPersonInterface;


    public PersonaAdapter(LinkedList<Persona> personas, Context context) {
        this.personas = personas;
        this.context = context;
    }

    public interface OnActionPersonInterface{
        void OnEditPerson(Persona persona);
        void OnDeletePersons(Persona persona);
    }

    public void setOnActionPersonInterface(OnActionPersonInterface onActionPersonInterface) {
        this.onActionPersonInterface = onActionPersonInterface;
    }

    @Override
    public int getCount() {
        return personas.size();
    }

    @Override
    public Persona getItem(int position) {
        return  personas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.person_item_layout,parent,false);
        TextView nameTextView = (TextView) rootView.findViewById(R.id.person_name);
        nameTextView.setText(personas.get(position).getName());
        TextView ageTextView = (TextView) rootView.findViewById(R.id.person_age);
        ageTextView.setText(""+personas.get(position).getAge());
        ImageView genderImage = (ImageView) rootView.findViewById(R.id.person_gender);
        if(personas.get(position).getGender()== Persona.FEMLAE){
            genderImage.setImageResource(R.drawable.female_icon);
        }else{
            genderImage.setImageResource(R.drawable.male_icon);

        }

        ImageView editPersonButton = (ImageView) rootView.findViewById(R.id.edit_person);
        ImageView erasePersonButton = (ImageView) rootView.findViewById(R.id.erase_person);
        editPersonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onActionPersonInterface != null){
                    onActionPersonInterface.OnEditPerson(personas.get(position));
                }
            }
        });

        erasePersonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onActionPersonInterface != null){
                    onActionPersonInterface.OnDeletePersons(personas.get(position));
                }
            }
        });


        return  rootView;


    }
}
