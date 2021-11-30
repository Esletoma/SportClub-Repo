package com.sportclub.actividadescentro_ms.models;
import org.springframework.data.annotation.Id;

public class Horario {

    @Id
    private String id;
    private String dia;
    private int hora;
    private int min;

    public Horario(/*String id,*/ String dia, int hora, int min){
        //this.id = id;
        this.dia = dia;
        this.hora = hora;
        this.min = min;
    }

    public String getId() {
        return id;
    }

    public String getDia() {
        return dia;
    }

    public int getHora() {
        return hora;
    }

    public int getMin() {
        return min;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public void setHora(int hora) {
        this.hora = hora;
    }

    public void setMin(int min) {
        this.min = min;
    }
}
