package com.tecsup.exam12.repository;

import com.tecsup.exam12.entity.Mesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface MesaRepository extends JpaRepository<Mesa, Long> {

    List<Mesa> findByEstado(String estado);

    Optional<Mesa> findByNumero(Integer numero);

    List<Mesa> findByCapacidadGreaterThanEqual(Integer capacidad);
}