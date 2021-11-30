package com.sportclub.actividadescentro_ms.controllers;

import com.sportclub.actividadescentro_ms.exceptions.ActividadNotFoundException;
import com.sportclub.actividadescentro_ms.exceptions.CentroNotFoundException;
import com.sportclub.actividadescentro_ms.exceptions.HorarioNotFoundException;
import com.sportclub.actividadescentro_ms.models.Actividad;
import com.sportclub.actividadescentro_ms.models.Horario;
import com.sportclub.actividadescentro_ms.repositories.HorarioRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class HorarioController {

    private final HorarioRepository horRepo;

    public HorarioController(HorarioRepository horRepo){
        this.horRepo = horRepo;
    }

    @PostMapping("/horario")
    Horario newHorario(@RequestBody Horario horario){
        List<Horario> hors = horRepo.findByDia(horario.getDia());
        System.out.println("lenght hors en Horario Controller: "+hors.size());
        boolean flag = false;
        for (Horario x: hors) {
            if (x.getHora() == horario.getHora() && x.getMin()== horario.getMin()){
                flag = true;
                break;
            }
        }
        if(flag){
            throw new HorarioNotFoundException("El Horario ya existe");
        }
        return horRepo.save(horario);
    }

    @GetMapping("/horarios")
    List<Horario> getHorarios(){
        List<Horario> hors = horRepo.findAll();

        if(hors==null||hors.size()==0){
            throw new HorarioNotFoundException("No existen horarios en la BD");
        }
        return hors;
    }

    @GetMapping("/horario/{dia}")
    List<Horario> getHorarioPorDía(@PathVariable String dia){

        List<Horario> hors = horRepo.findByDia(dia);

        if(hors==null||hors.size()==0){
            throw new HorarioNotFoundException("No existe ningún horario para el día: "+ dia);
        }
        return hors;
    }

}
