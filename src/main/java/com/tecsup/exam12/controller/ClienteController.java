package com.tecsup.exam12.controller;

import com.tecsup.exam12.entity.Cliente;
import com.tecsup.exam12.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    // ✅ Listar todos los clientes
    @GetMapping
    public String listarClientes(Model model) {
        model.addAttribute("clientes", clienteService.listarTodos());
        return "clientes/listar";
    }

    // ✅ Listar solo clientes activos
    @GetMapping("/activos")
    public String listarClientesActivos(Model model) {
        model.addAttribute("clientes", clienteService.listarActivos());
        return "clientes/listar";
    }

    // ✅ Mostrar formulario para nuevo cliente
    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        Cliente cliente = new Cliente();
        cliente.setEstado("activo"); // Estado por defecto
        model.addAttribute("cliente", cliente);
        return "clientes/formulario";
    }

    // ✅ Guardar cliente (nuevo o editar)
    @PostMapping("/guardar")
    public String guardarCliente(@ModelAttribute Cliente cliente) {
        clienteService.guardar(cliente);
        return "redirect:/clientes";
    }

    // ✅ Mostrar formulario para editar cliente
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Cliente cliente = clienteService.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        model.addAttribute("cliente", cliente);
        return "clientes/formulario";
    }

    // ✅ Eliminar cliente (cambiar estado a inactivo)
    @GetMapping("/eliminar/{id}")
    public String eliminarCliente(@PathVariable Long id) {
        clienteService.eliminar(id);
        return "redirect:/clientes";
    }

    // ✅ Activar cliente
    @GetMapping("/activar/{id}")
    public String activarCliente(@PathVariable Long id) {
        clienteService.activar(id);
        return "redirect:/clientes";
    }

    // ✅ Buscar clientes por nombre o apellido
    @GetMapping("/buscar")
    public String buscarClientes(@RequestParam String texto, Model model) {
        model.addAttribute("clientes", clienteService.buscarPorNombreOApellido(texto));
        return "clientes/listar";
    }
}