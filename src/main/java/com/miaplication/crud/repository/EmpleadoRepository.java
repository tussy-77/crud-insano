package com.miaplication.crud.repository;

import com.miaplication.crud.entity.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {

    // Buscar por nombre (coincidencia parcial)
    List<Empleado> findByNombreContainingIgnoreCase(String nombre);

    // Buscar por puesto (coincidencia parcial)
    List<Empleado> findByCargoContainingIgnoreCase(String puesto);

    // Si quieres buscar por ambos:
    List<Empleado> findByNombreContainingIgnoreCaseOrCargoContainingIgnoreCase(String nombre, String cargo);
}
