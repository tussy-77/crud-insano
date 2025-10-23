package com.miaplication.crud.service;

import com.miaplication.crud.entity.Proyecto;
import com.miaplication.crud.repository.ProyectoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProyectoService {

    private final ProyectoRepository proyectoRepository;

    public ProyectoService(ProyectoRepository proyectoRepository) {
        this.proyectoRepository = proyectoRepository;
    }

    public List<Proyecto> listarTodos() {
        return proyectoRepository.findAll();
    }

    public Proyecto guardar(Proyecto proyecto) {
        return proyectoRepository.save(proyecto);
    }

    public void eliminar(String id) {
        proyectoRepository.deleteById(id);
    }

    public Optional<Proyecto> obtenerPorId(String id) {
        return proyectoRepository.findById(id);
    }
}
