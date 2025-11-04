package com.tecsup.exam12.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "bitacora")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bitacora {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idBitacora;

    @Column(nullable = false)
    private String usuario;

    @Column(nullable = false, length = 500)
    private String accion;

    @Column(nullable = false)
    private LocalDateTime fechaHora;

    @Column(length = 50)
    private String entidad; // Cliente, Mesa

    @Column
    private Long idEntidad;
}