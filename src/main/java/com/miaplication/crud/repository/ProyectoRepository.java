package com.miaplication.crud.repository;

import com.miaplication.crud.entity.Proyecto;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProyectoRepository extends MongoRepository<Proyecto, String> {
    // puedes agregar consultas personalizadas si quieres
}
