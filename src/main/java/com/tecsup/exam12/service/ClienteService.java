package com.tecsup.exam12.service;

import com.tecsup.exam12.entity.Cliente;
import com.tecsup.exam12.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    // Listar todos los clientes
    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }

    // Listar clientes activos
    public List<Cliente> listarActivos() {
        return clienteRepository.findByEstado("activo");
    }

    // Buscar cliente por ID
    public Optional<Cliente> buscarPorId(Long id) {
        return clienteRepository.findById(id);
    }

    // Buscar cliente por DNI
    public Optional<Cliente> buscarPorDni(String dni) {
        return clienteRepository.findByDni(dni);
    }

    // Buscar clientes por nombre o apellido
    public List<Cliente> buscarPorNombreOApellido(String texto) {
        return clienteRepository.findByNombresContainingIgnoreCaseOrApellidosContainingIgnoreCase(texto, texto);
    }

    // Guardar cliente (crear o actualizar)
    public Cliente guardar(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    // Eliminar cliente (cambiar estado a inactivo)
    public void eliminar(Long id) {
        Optional<Cliente> cliente = clienteRepository.findById(id);
        if (cliente.isPresent()) {
            Cliente c = cliente.get();
            c.setEstado("inactivo");
            clienteRepository.save(c);
        }
    }

    // Eliminar permanentemente
    public void eliminarPermanente(Long id) {
        clienteRepository.deleteById(id);
    }

    // Activar cliente
    public void activar(Long id) {
        Optional<Cliente> cliente = clienteRepository.findById(id);
        if (cliente.isPresent()) {
            Cliente c = cliente.get();
            c.setEstado("activo");
            clienteRepository.save(c);
        }
    }
}