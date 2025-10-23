package com.miaplication.crud.controller;

import com.miaplication.crud.entity.Empleado;
import com.miaplication.crud.service.EmpleadoService;
import com.miaplication.crud.export.EmpleadoExportService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import com.itextpdf.text.DocumentException;

@Controller
public class EmpleadoFormController {

    private final EmpleadoService empleadoService;
    private final EmpleadoExportService empleadoExportService;

    // Constructor con inyección de dependencias
    public EmpleadoFormController(EmpleadoService empleadoService, EmpleadoExportService empleadoExportService) {
        this.empleadoService = empleadoService;
        this.empleadoExportService = empleadoExportService;
    }

    // 🔹 Mostrar formulario
    @GetMapping("/empleados/nuevo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("empleado", new Empleado());
        return "agregarEmpleado"; // agregarEmpleado.html
    }

    // 🔹 Guardar empleado
    @PostMapping("/empleados/nuevo")
    public String guardarEmpleado(@ModelAttribute Empleado empleado) {
        empleadoService.guardar(empleado);
        return "redirect:/home"; // vuelve al home
    }

    // 🔹 Mostrar formulario para editar (cargar datos existentes)
    @GetMapping("/empleados/editar/{id}")
    public String editarEmpleado(@PathVariable Long id, Model model) {
        Empleado empleado = empleadoService.buscarPorId(id);
        model.addAttribute("empleado", empleado);
        return "agregarEmpleado"; // reutilizas el mismo formulario
    }

    // 🔹 Guardar cambios del empleado editado
    @PostMapping("/empleados/editar/{id}")
    public String actualizarEmpleado(@PathVariable Long id, @ModelAttribute Empleado empleado) {
        empleado.setId(id);
        empleadoService.guardar(empleado);
        return "redirect:/home";
    }

    // 🔹 Eliminar empleado
    @GetMapping("/empleados/eliminar/{id}")
    public String eliminarEmpleado(@PathVariable Long id) {
        empleadoService.eliminar(id);
        return "redirect:/home";
    }

    // ✅ Exportar Excel
    @GetMapping("/empleados/export/excel")
    public void exportarExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=empleados.xlsx");
        empleadoExportService.exportarExcel(response);
    }

    // ✅ Exportar PDF
    @GetMapping("/empleados/export/pdf")
    public void exportarPDF(HttpServletResponse response) throws IOException, DocumentException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=empleados.pdf");
        empleadoExportService.exportarPDF(response);
    }
}
