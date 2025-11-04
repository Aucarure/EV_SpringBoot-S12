package com.tecsup.exam12.repository;

import com.tecsup.exam12.entity.Bitacora;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BitacoraRepository extends JpaRepository<Bitacora, Long> {

    List<Bitacora> findByUsuario(String usuario);

    List<Bitacora> findByEntidad(String entidad);

    List<Bitacora> findByEntidadAndIdEntidad(String entidad, Long idEntidad);
}