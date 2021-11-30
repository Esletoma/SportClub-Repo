package com.sportclub.actividadescentro_ms.models;
import org.springframework.data.annotation.Id;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

public class Actividad {

    @Id
    private String nombre;
    private List<Horario> horarios;
    private int duracion;
    private int categoria;
    private List<String> centros;
    private Horario horarioTmp;

    public Actividad (String nombre, Horario horarioTmp, int duracion, int categoria){
        this.nombre = nombre;
        this.duracion = duracion;
        this.categoria = categoria;
        this.centros = new ArrayList<String>();
        this.horarios = new ArrayList<Horario>();
        this.horarioTmp = horarioTmp;
        //insertHorario(horarioTmp);
    }

    public void setNombre (String nombre){
        this.nombre = nombre;
    }

    public String getNombre(){
        return this.nombre;
    }

    public List<Horario> getHorarios(){
        return this.horarios;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public int getCategoria() {
        return categoria;
    }

    public void setCategoria(int categoria) {
        this.categoria = categoria;
    }

    public void addCentro(String centro){
        this.centros.add(centro);
    }

    public List<String> getCentros() {
        return centros;
    }

    public Horario getHorarioTmp() {
        return horarioTmp;
    }

    public void insertHorario(Horario h){
        if(!horarios.contains(h)){
            horarios.add(h);
        }
    }
}
