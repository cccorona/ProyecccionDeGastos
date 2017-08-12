package mx.com.cesarcorona.proyeccciondegastos;

import android.graphics.Color;
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

import mx.com.cesarcorona.proyeccciondegastos.fragments.DataFragment;
import mx.com.cesarcorona.proyeccciondegastos.fragments.FamiliaFragment;
import mx.com.cesarcorona.proyeccciondegastos.fragments.HomeFragment;
import mx.com.cesarcorona.proyeccciondegastos.fragments.ResumenFragment;
import mx.com.cesarcorona.proyeccciondegastos.pojo.Persona;


public class MainActivity extends AppCompatActivity implements AddPersonDialog.OnAddPersonInterface {


    LinearLayout homeTab, resumenTab, dataTab, familiaTab;
    ImageView addPersonButton;
    Fragment currentFragment;
    int currentPosition;
    ImageView iconHome,iconResumen,iconData,iconFamilia;
    TextView textHome, textResumen, textData, textFamilia;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        currentFragment = new HomeFragment();
        currentPosition =1;
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

        iconHome = (ImageView) findViewById(R.id.home_icon);
        iconData = (ImageView) findViewById(R.id.data_icon);
        iconResumen = (ImageView) findViewById(R.id.resumen_icon);
        iconFamilia = (ImageView) findViewById(R.id.familia_icon);

        textHome = (TextView)findViewById(R.id.home_text);
        textResumen = (TextView)findViewById(R.id.resumen_text);
        textData = (TextView)findViewById(R.id.data_text);
        textFamilia = (TextView)findViewById(R.id.familia_text);





        homeTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeFragment homeFragment = (HomeFragment) getSupportFragmentManager().findFragmentByTag(HomeFragment.TAG);
                if(homeFragment == null){
                    homeFragment = new HomeFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_fragment,homeFragment,HomeFragment.TAG).addToBackStack(null).commit();

                }else{
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_fragment,homeFragment).addToBackStack(null).commit();
                }

                iconHome.setColorFilter(Color.argb(0, 0, 0, 0)); // Black Tint
                iconData.setColorFilter(Color.argb(255, 255, 255, 255)); // White Tint
                iconResumen.setColorFilter(Color.argb(255, 255, 255, 255)); // White Tint
                iconFamilia.setColorFilter(Color.argb(255, 255, 255, 255)); // White Tint

                textHome.setTextColor(Color.BLACK);
                textResumen.setTextColor(Color.WHITE);
                textData.setTextColor(Color.WHITE);
                textFamilia.setTextColor(Color.WHITE);


            }
        });

        resumenTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ResumenFragment resumenFragment = (ResumenFragment) getSupportFragmentManager().findFragmentByTag(ResumenFragment.TAG);
                if(resumenFragment == null){
                    resumenFragment = new ResumenFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_fragment,resumenFragment,ResumenFragment.TAG).addToBackStack(null).commit();

                }else{
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_fragment,resumenFragment).addToBackStack(null).commit();
                }

                iconHome.setColorFilter(Color.argb(255, 255, 255, 255)); // Black Tint
                iconData.setColorFilter(Color.argb(255, 255, 255, 255)); // White Tint
                iconResumen.setColorFilter(Color.argb(0, 0, 0, 0)); // White Tint
                iconFamilia.setColorFilter(Color.argb(255, 255, 255, 255)); // White Tint

                textHome.setTextColor(Color.WHITE);
                textResumen.setTextColor(Color.BLACK);
                textData.setTextColor(Color.WHITE);
                textFamilia.setTextColor(Color.WHITE);
            }
        });

        dataTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataFragment dataFragment = (DataFragment) getSupportFragmentManager().findFragmentByTag(DataFragment.TAG);
                if(dataFragment == null){
                    dataFragment = new DataFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_fragment,dataFragment,DataFragment.TAG).addToBackStack(null).commit();

                }else{
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_fragment,dataFragment).addToBackStack(null).commit();
                }

                iconHome.setColorFilter(Color.argb(255, 255, 255, 255)); // Black Tint
                iconData.setColorFilter(Color.argb(0, 0, 0, 0)); // White Tint
                iconResumen.setColorFilter(Color.argb(255, 255, 255, 255)); // White Tint
                iconFamilia.setColorFilter(Color.argb(255, 255, 255, 255)); // White Tint

                textHome.setTextColor(Color.WHITE);
                textResumen.setTextColor(Color.WHITE);
                textData.setTextColor(Color.BLACK);
                textFamilia.setTextColor(Color.WHITE);

            }
        });

        familiaTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FamiliaFragment familiaFragment = (FamiliaFragment) getSupportFragmentManager().findFragmentByTag(FamiliaFragment.TAG);
                if(familiaFragment == null){
                    familiaFragment = new FamiliaFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_fragment,familiaFragment,FamiliaFragment.TAG).addToBackStack(null).commit();

                }else{
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_fragment,familiaFragment).addToBackStack(null).commit();
                }

                iconHome.setColorFilter(Color.argb(255, 255, 255, 255)); // Black Tint
                iconData.setColorFilter(Color.argb(255, 255, 255, 255)); // White Tint
                iconResumen.setColorFilter(Color.argb(255, 255, 255, 255)); // White Tint
                iconFamilia.setColorFilter(Color.argb(0,0,0, 0)); // White Tint

                textHome.setTextColor(Color.WHITE);
                textResumen.setTextColor(Color.WHITE);
                textData.setTextColor(Color.WHITE);
                textFamilia.setTextColor(Color.BLACK);

            }
        });



    }



    private void startListening(){


    }


    @Override
    public void OnPersonAdded(Persona persona) {
      HomeFragment homeFragment = (HomeFragment) getSupportFragmentManager().findFragmentByTag(HomeFragment.TAG);
        if(homeFragment !=null){
            homeFragment.addPerson(persona);
        }
    }
}
