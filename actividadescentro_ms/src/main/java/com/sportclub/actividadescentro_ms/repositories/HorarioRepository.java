package com.sportclub.actividadescentro_ms.repositories;

import com.sportclub.actividadescentro_ms.models.Horario;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface HorarioRepository extends MongoRepository <Horario, String> {
    //boolean existsHorario(String dia, int hora, int min);
    List<Horario> findByDia(String dia);
}
