package mx.com.cesarcorona.proyeccciondegastos;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import mx.com.cesarcorona.proyeccciondegastos.fragments.HomeFragment;
import mx.com.cesarcorona.proyeccciondegastos.pojo.Persona;


public class MainActivity extends AppCompatActivity implements AddPersonDialog.OnAddPersonInterface {


    LinearLayout homeTab, resumenTab, dataTab, familiaTab;
    ImageView addPersonButton;
    Fragment currentFragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        currentFragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.content_fragment,currentFragment,HomeFragment.TAG).commit();
        homeTab = (LinearLayout) findViewById(R.id.home_tab);
        resumenTab = (LinearLayout) findViewById(R.id.resumen_tab);
        dataTab = (LinearLayout) findViewById(R.id.data_tab);
        familiaTab = (LinearLayout) findViewById(R.id.familia_tab);
        addPersonButton = (ImageView) findViewById(R.id.add_person_button);
        addPersonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddPersonDialog addPersonDialog = new AddPersonDialog();
                Bundle arguments = new Bundle();
                addPersonDialog.setArguments(arguments);
                addPersonDialog.setOnAddPersonInterface(MainActivity.this);
                addPersonDialog.show(getSupportFragmentManager(),AddPersonDialog.TAG);

            }
        });



    }



    private void startListening(){


    }


    @Override
    public void OnPersonAdded(Persona persona) {

    }
}
