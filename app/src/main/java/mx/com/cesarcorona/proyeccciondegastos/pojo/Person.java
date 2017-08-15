package mx.com.cesarcorona.proyeccciondegastos.pojo;

import java.io.Serializable;

/**
 * Created by ccabrera on 11/08/17.
 */

public class Person implements Serializable {


    public static String HOMBRE = "H";
    public static String MUJER ="M";

    private int edad;
    private String genero;


    public Person(int edad, String genero) {
        this.edad = edad;
        this.genero = genero;
    }

    public Person(){
        this.edad = 0;
        this.genero="";
    }


    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }
}
