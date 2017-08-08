package mx.com.cesarcorona.proyeccciondegastos.pojo;

import java.io.Serializable;

/**
 * Created by ccabrera on 08/08/17.
 */

public class Persona implements Serializable {


    public static int MALE = 1;
    public static int FEMLAE = 0;

    private String name;
    private int age;
    private int gender;


    public Persona(String name, int age, int gender) {
        this.name = name;
        this.age = age;
        this.gender = gender;
    }


    public Persona() {
    }

    public Persona(int age, int gender) {
        this.age = age;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }
}
