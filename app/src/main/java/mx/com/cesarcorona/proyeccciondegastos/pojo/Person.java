package mx.com.cesarcorona.proyeccciondegastos.pojo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by ccabrera on 11/08/17.
 */

public class Person implements Serializable {


    public static String HOMBRE = "H";
    public static String MUJER ="M";
    public static String NONE="N";

    private int edad;
    private String genero;
    private LinkedHashMap<Integer,Double> primaPorAño;
    private double primaCalculada;


    public Person(int edad, String genero) {
        this.edad = edad;
        this.genero = genero;
    }

    public Person(){
        this.edad = 0;
        this.genero=HOMBRE;
        this.primaPorAño = new LinkedHashMap<>();
        this.primaCalculada = 0.0 ;
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

    public LinkedHashMap<Integer, Double> getPrimaPorAño() {
        return primaPorAño;
    }

    public void setPrimaPorAño(LinkedHashMap<Integer, Double> primaPorAño) {
        this.primaPorAño = primaPorAño;
    }

    public void agregarPrimaPorAno(Integer integer, Double prima){
        primaPorAño.put(integer,prima);
    }

    public double getPrimaCalculada() {
        return primaCalculada;
    }

    public void setPrimaCalculada(double primaCalculada) {
        this.primaCalculada = primaCalculada;
    }
}
