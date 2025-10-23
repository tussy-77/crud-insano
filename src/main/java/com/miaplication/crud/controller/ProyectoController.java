package com.miaplication.crud.controller;

import com.miaplication.crud.entity.Proyecto;
import com.miaplication.crud.service.ProyectoService;
import com.miaplication.crud.export.ProyectoExportService;

import jakarta.servlet.http.HttpServletResponse;
import com.itextpdf.text.DocumentException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
public class ProyectoController {

    private final ProyectoService proyectoService;
    private final ProyectoExportService proyectoExportService;

    @Autowired
    public ProyectoController(ProyectoService proyectoService, ProyectoExportService proyectoExportService) {
        this.proyectoService = proyectoService;
        this.proyectoExportService = proyectoExportService;
    }

    // ✅ Listar proyectos
    @GetMapping("/proyectos")
    public String listarProyectos(Model model) {
        model.addAttribute("proyectos", proyectoService.listarTodos());
        return "proyectos"; // proyectos.html
    }

    // ✅ Mostrar formulario para crear
    @GetMapping("/proyectos/nuevo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("proyecto", new Proyecto());
        return "agregarProyecto"; // formulario agregar/editar
    }

    // ✅ Guardar nuevo proyecto
    @PostMapping("/proyectos/nuevo")
    public String guardarProyecto(@ModelAttribute Proyecto proyecto) {
        proyectoService.guardar(proyecto);
        return "redirect:/proyectos";
    }

    // ✅ Mostrar formulario para editar
    @GetMapping("/proyectos/editar/{id}")
    public String editarProyecto(@PathVariable String id, Model model) {
        Proyecto proyecto = proyectoService.obtenerPorId(id).orElse(new Proyecto());
        model.addAttribute("proyecto", proyecto);
        return "agregarProyecto";
    }

    // ✅ Actualizar proyecto
    @PostMapping("/proyectos/editar/{id}")
    public String actualizarProyecto(@PathVariable String id, @ModelAttribute Proyecto proyecto) {
        proyecto.setId(id);
        proyectoService.guardar(proyecto);
        return "redirect:/proyectos";
    }

    // ✅ Eliminar proyecto
    @GetMapping("/proyectos/eliminar/{id}")
    public String eliminarProyecto(@PathVariable String id) {
        proyectoService.eliminar(id);
        return "redirect:/proyectos";
    }

    // ✅ Exportar a Excel
    @GetMapping("/proyectos/export/excel")
    public void exportarExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=proyectos.xlsx");
        proyectoExportService.exportarExcel(response);
    }

    // ✅ Exportar a PDF
    @GetMapping("/proyectos/export/pdf")
    public void exportarPdf(HttpServletResponse response) throws IOException, DocumentException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=proyectos.pdf");
        proyectoExportService.exportarPDF(response); // 🔹 OJO: PDF en mayúsculas
    }
}
