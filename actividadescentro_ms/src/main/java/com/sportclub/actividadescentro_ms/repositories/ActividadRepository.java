package com.sportclub.actividadescentro_ms.repositories;

import com.sportclub.actividadescentro_ms.models.Actividad;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface ActividadRepository  extends MongoRepository<Actividad, String>{
    Actividad findByNombre(String nombre);
}
