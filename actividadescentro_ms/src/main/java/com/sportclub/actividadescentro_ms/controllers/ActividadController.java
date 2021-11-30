package com.sportclub.actividadescentro_ms.controllers;

import com.sportclub.actividadescentro_ms.models.Horario;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sportclub.actividadescentro_ms.repositories.ActividadRepository;
import com.sportclub.actividadescentro_ms.models.Actividad;
import com.sportclub.actividadescentro_ms.exceptions.ActividadNotFoundException;
import com.sportclub.actividadescentro_ms.exceptions.CentroNotFoundException;
import com.sportclub.actividadescentro_ms.repositories.HorarioRepository;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ActividadController {
    private final ActividadRepository actRepo;
    private final HorarioRepository horRepo;
    private HorarioController horControl;

    public ActividadController (ActividadRepository actRepo, HorarioRepository horRepo){
        this.actRepo = actRepo;
        this.horRepo = horRepo;
        this.horControl = new HorarioController(horRepo);
    }

    @GetMapping("/actividad/{nombre}")
    Actividad getActividad(@PathVariable String nombre){
        return actRepo.findById(nombre).orElseThrow(() ->
                new ActividadNotFoundException
                        ("No se encontró una actividad con el nombre: " + nombre));
    }

    @GetMapping("/actividades/{centro}")
    List<Actividad> getActividades(@PathVariable String centro){

        List<Actividad> allActividades = actRepo.findAll();

        List<Actividad>actCentro = new ArrayList<Actividad>();

        for (Actividad a:allActividades) {
            if(a.getCentros().contains(centro)){
                actCentro.add(a);
            }
        }
        if(actCentro.size()==0){
            throw new CentroNotFoundException("El Centro no tiene Actividades asociadas");
        }
        return actCentro;
    }

    @PostMapping("/actividad")
    Actividad newActividad(@RequestBody Actividad actividad){

        int h = actividad.getHorarioTmp().getHora();
        int m = actividad.getHorarioTmp().getMin();
        String d = actividad.getHorarioTmp().getDia();

        List<Horario> hors = horRepo.findByDia(d);
        boolean flag = true;

        Horario tmp = null;

        for (Horario x: hors) {
            if(x.getHora()== h && x.getMin()==m){
                flag = false;
                tmp = x;
                break;
            }
        }
        if(flag) {
            tmp = horControl.newHorario(new Horario(d, h, m));
            System.out.println("Entró a crear Horario");
            // throw new HorarioNotFoundException("Horario no existe");
        }

        Actividad act = actRepo.findByNombre(actividad.getNombre());

        if(act!=null){
            System.out.println("actividad es difernte de null");
            act.insertHorario(tmp);
            return actRepo.save(act);
        }else{
            System.out.println("actividad es null");
            actividad.insertHorario(tmp);
            return actRepo.save(actividad);
        }
    }

    @PutMapping("/actividadcentro/{nombre}")
    ResponseEntity<Actividad> insertCentro(@PathVariable String nombre, @RequestBody String c){
        Actividad act = actRepo.findById(nombre)
                .orElseThrow(() -> new ActividadNotFoundException
                        ("No existe la actividad: " + nombre));

        act.addCentro(c);
        final Actividad updatedActividad = actRepo.save(act);
        return ResponseEntity.ok(updatedActividad);
    }
}
