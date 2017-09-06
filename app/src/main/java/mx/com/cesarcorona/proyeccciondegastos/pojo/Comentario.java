package mx.com.cesarcorona.proyeccciondegastos.pojo;

import java.io.Serializable;

/**
 * Created by ccabrera on 05/09/17.
 */

public class Comentario implements Serializable {


    private String comentario;
    private String correoelectronico;
    private String fecha;
    private String nombrePersona;


    public Comentario(String comentario, String correoelectronico, String fecha, String nombrePersona) {
        this.comentario = comentario;
        this.correoelectronico = correoelectronico;
        this.fecha = fecha;
        this.nombrePersona = nombrePersona;
    }

    public Comentario() {
    }


    public String getComentario() {

        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getCorreoelectronico() {
        return correoelectronico;
    }

    public void setCorreoelectronico(String correoelectronico) {
        this.correoelectronico = correoelectronico;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getNombrePersona() {
        return nombrePersona;
    }

    public void setNombrePersona(String nombrePersona) {
        this.nombrePersona = nombrePersona;
    }
}
