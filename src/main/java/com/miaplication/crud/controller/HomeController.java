package com.miaplication.crud.controller;

import com.miaplication.crud.service.EmpleadoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final EmpleadoService empleadoService;

    public HomeController(EmpleadoService empleadoService) {
        this.empleadoService = empleadoService;
    }

    @GetMapping("/home")
    public String mostrarHome(Model model) {
        model.addAttribute("empleados", empleadoService.listarTodos());
        return "home"; // home.html
    }
}
