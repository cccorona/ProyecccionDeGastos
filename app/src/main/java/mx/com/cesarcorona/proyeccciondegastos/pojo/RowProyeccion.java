package mx.com.cesarcorona.proyeccciondegastos.pojo;

import java.io.Serializable;

/**
 * Created by ccabrera on 19/08/17.
 */

public class RowProyeccion implements Serializable{

    private double pesos;
    private double pesos2017;
    private int ano;


    public RowProyeccion(double pesos, double pesos2017, int ano) {
        this.pesos = pesos;
        this.pesos2017 = pesos2017;
        this.ano = ano;
    }

    public double getPesos() {
        return pesos;
    }

    public void setPesos(double pesos) {
        this.pesos = pesos;
    }

    public double getPesos2017() {
        return pesos2017;
    }

    public void setPesos2017(double pesos2017) {
        this.pesos2017 = pesos2017;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }
}
