package com.tecsup.exam12.controller;

import com.tecsup.exam12.entity.Mesa;
import com.tecsup.exam12.service.MesaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/mesas")
public class MesaController {

    @Autowired
    private MesaService mesaService;

    // ✅ Listar todas las mesas
    @GetMapping
    public String listarMesas(Model model) {
        model.addAttribute("mesas", mesaService.listarTodas());
        return "mesas/listar";
    }

    // ✅ Listar solo mesas disponibles
    @GetMapping("/disponibles")
    public String listarMesasDisponibles(Model model) {
        model.addAttribute("mesas", mesaService.listarDisponibles());
        return "mesas/disponibles";
    }

    // ✅ Listar mesas ocupadas
    @GetMapping("/ocupadas")
    public String listarMesasOcupadas(Model model) {
        model.addAttribute("mesas", mesaService.listarOcupadas());
        return "mesas/listar";
    }

    // ✅ Mostrar formulario para nueva mesa
    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        Mesa mesa = new Mesa();
        mesa.setEstado("disponible"); // Estado por defecto
        model.addAttribute("mesa", mesa);
        return "mesas/formulario";
    }

    // ✅ Guardar mesa (nueva o editar)
    @PostMapping("/guardar")
    public String guardarMesa(@ModelAttribute Mesa mesa) {
        mesaService.guardar(mesa);
        return "redirect:/mesas";
    }

    // ✅ Mostrar formulario para editar mesa
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Mesa mesa = mesaService.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Mesa no encontrada"));
        model.addAttribute("mesa", mesa);
        return "mesas/formulario";
    }

    // ✅ Cambiar estado de mesa
    @GetMapping("/cambiar-estado/{id}/{estado}")
    public String cambiarEstado(@PathVariable Long id, @PathVariable String estado) {
        mesaService.cambiarEstado(id, estado);
        return "redirect:/mesas";
    }

    // ✅ Ocupar mesa
    @GetMapping("/ocupar/{id}")
    public String ocuparMesa(@PathVariable Long id) {
        mesaService.ocuparMesa(id);
        return "redirect:/mesas";
    }

    // ✅ Liberar mesa
    @GetMapping("/liberar/{id}")
    public String liberarMesa(@PathVariable Long id) {
        mesaService.liberarMesa(id);
        return "redirect:/mesas";
    }

    // ✅ Reservar mesa
    @GetMapping("/reservar/{id}")
    public String reservarMesa(@PathVariable Long id) {
        mesaService.reservarMesa(id);
        return "redirect:/mesas";
    }

    // ✅ Poner mesa en mantenimiento
    @GetMapping("/mantenimiento/{id}")
    public String mantenimientoMesa(@PathVariable Long id) {
        mesaService.mantenimientoMesa(id);
        return "redirect:/mesas";
    }

    // ✅ Eliminar mesa
    @GetMapping("/eliminar/{id}")
    public String eliminarMesa(@PathVariable Long id) {
        mesaService.eliminar(id);
        return "redirect:/mesas";
    }

    // ✅ Buscar mesas por capacidad
    @GetMapping("/buscar")
    public String buscarMesas(@RequestParam Integer capacidad, Model model) {
        model.addAttribute("mesas", mesaService.buscarPorCapacidad(capacidad));
        return "mesas/listar";
    }
}