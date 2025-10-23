package com.miaplication.crud.service;

import com.miaplication.crud.entity.Empleado;
import com.miaplication.crud.repository.EmpleadoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmpleadoService {
    private final EmpleadoRepository empleadoRepository;

    public EmpleadoService(EmpleadoRepository empleadoRepository) {
        this.empleadoRepository = empleadoRepository;
    }

    // Listar todos los empleados
    public List<Empleado> listarTodos() {
        return empleadoRepository.findAll();
    }

    // Guardar o actualizar un empleado
    public Empleado guardar(Empleado empleado) {
        return empleadoRepository.save(empleado);
    }

    // Eliminar un empleado por id
    public void eliminar(Long id) {
        empleadoRepository.deleteById(id);
    }

    // 🔹 Buscar un empleado por id (nuevo método para editar)
    public Empleado buscarPorId(Long id) {
        return empleadoRepository.findById(id).orElse(null);
    }

    // 🔹 metodos de búsqueda por nombre o cargo (ya los tenías)
    public List<Empleado> buscarPorNombreOCargo(String palabra) {
        return empleadoRepository.findByNombreContainingIgnoreCaseOrCargoContainingIgnoreCase(palabra, palabra);
    }

    public List<Empleado> buscarPorCargo(String cargo) {
        return empleadoRepository.findByCargoContainingIgnoreCase(cargo);
    }

    // Si necesitas obtener un Optional en vez de Empleado directo
    public Optional<Empleado> obtenerPorId(Long id) {
        return empleadoRepository.findById(id);
    }
}
