package com.tecsup.exam12.service;

import com.tecsup.exam12.entity.Mesa;
import com.tecsup.exam12.repository.MesaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MesaService {

    @Autowired
    private MesaRepository mesaRepository;

    // Listar todas las mesas
    public List<Mesa> listarTodas() {
        return mesaRepository.findAll();
    }

    // Listar mesas disponibles
    public List<Mesa> listarDisponibles() {
        return mesaRepository.findByEstado("disponible");
    }

    // Listar mesas ocupadas
    public List<Mesa> listarOcupadas() {
        return mesaRepository.findByEstado("ocupada");
    }

    // Listar mesas por estado
    public List<Mesa> listarPorEstado(String estado) {
        return mesaRepository.findByEstado(estado);
    }

    // Buscar mesa por ID
    public Optional<Mesa> buscarPorId(Long id) {
        return mesaRepository.findById(id);
    }

    // Buscar mesa por número
    public Optional<Mesa> buscarPorNumero(Integer numero) {
        return mesaRepository.findByNumero(numero);
    }

    // Buscar mesas por capacidad mínima
    public List<Mesa> buscarPorCapacidad(Integer capacidad) {
        return mesaRepository.findByCapacidadGreaterThanEqual(capacidad);
    }

    // Guardar mesa (crear o actualizar)
    public Mesa guardar(Mesa mesa) {
        return mesaRepository.save(mesa);
    }

    // Cambiar estado de mesa
    public void cambiarEstado(Long id, String nuevoEstado) {
        Optional<Mesa> mesa = mesaRepository.findById(id);
        if (mesa.isPresent()) {
            Mesa m = mesa.get();
            m.setEstado(nuevoEstado);
            mesaRepository.save(m);
        }
    }

    // Ocupar mesa
    public void ocuparMesa(Long id) {
        cambiarEstado(id, "ocupada");
    }

    // Liberar mesa
    public void liberarMesa(Long id) {
        cambiarEstado(id, "disponible");
    }

    // Reservar mesa
    public void reservarMesa(Long id) {
        cambiarEstado(id, "reservada");
    }

    // Poner mesa en mantenimiento
    public void mantenimientoMesa(Long id) {
        cambiarEstado(id, "mantenimiento");
    }

    // Eliminar mesa
    public void eliminar(Long id) {
        mesaRepository.deleteById(id);
    }
}