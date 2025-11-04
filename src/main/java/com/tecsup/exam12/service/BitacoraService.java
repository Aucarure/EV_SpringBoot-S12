package com.tecsup.exam12.service;

import com.tecsup.exam12.entity.Bitacora;
import com.tecsup.exam12.repository.BitacoraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class BitacoraService {

    @Autowired
    private BitacoraRepository bitacoraRepository;

    // Registrar acción en bitácora
    public void registrar(String usuario, String accion, String entidad, Long idEntidad) {
        Bitacora bitacora = new Bitacora();
        bitacora.setUsuario(usuario);
        bitacora.setAccion(accion);
        bitacora.setFechaHora(LocalDateTime.now());
        bitacora.setEntidad(entidad);
        bitacora.setIdEntidad(idEntidad);
        bitacoraRepository.save(bitacora);
    }

    // Listar todas las acciones
    public List<Bitacora> listarTodas() {
        return bitacoraRepository.findAll();
    }

    // Listar acciones por usuario
    public List<Bitacora> listarPorUsuario(String usuario) {
        return bitacoraRepository.findByUsuario(usuario);
    }

    // Listar acciones por entidad
    public List<Bitacora> listarPorEntidad(String entidad) {
        return bitacoraRepository.findByEntidad(entidad);
    }

    // Listar acciones por entidad e ID
    public List<Bitacora> listarPorEntidadEId(String entidad, Long idEntidad) {
        return bitacoraRepository.findByEntidadAndIdEntidad(entidad, idEntidad);
    }
}