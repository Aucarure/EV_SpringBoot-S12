package com.tecsup.exam12.aspect;

import com.tecsup.exam12.entity.Cliente;
import com.tecsup.exam12.entity.Mesa;
import com.tecsup.exam12.service.BitacoraService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AuditoriaAspect {

    private static final Logger logger = LoggerFactory.getLogger(AuditoriaAspect.class);

    @Autowired
    private BitacoraService bitacoraService;

    // Obtener usuario autenticado
    private String obtenerUsuarioActual() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated()) {
            return auth.getName();
        }
        return "SYSTEM";
    }

    // ===== AUDITORÍA PARA CLIENTE =====

    @AfterReturning(
            pointcut = "execution(* com.tecsup.exam12.service.ClienteService.guardar(..))",
            returning = "result"
    )
    public void auditarGuardarCliente(JoinPoint joinPoint, Object result) {
        try {
            Cliente cliente = (Cliente) result;
            String usuario = obtenerUsuarioActual();
            String accion = "GUARDAR CLIENTE: " + cliente.getNombres() + " " + cliente.getApellidos() +
                    " (DNI: " + cliente.getDni() + ")";

            bitacoraService.registrar(usuario, accion, "Cliente", cliente.getIdCliente());
            logger.info("Auditoría registrada: {}", accion);
        } catch (Exception e) {
            logger.error("Error al auditar guardar cliente: {}", e.getMessage());
        }
    }

    @AfterReturning(
            pointcut = "execution(* com.tecsup.exam12.service.ClienteService.eliminar(..)) && args(id)",
            argNames = "joinPoint,id"
    )
    public void auditarEliminarCliente(JoinPoint joinPoint, Long id) {
        try {
            String usuario = obtenerUsuarioActual();
            String accion = "ELIMINAR CLIENTE (cambio a inactivo) - ID: " + id;

            bitacoraService.registrar(usuario, accion, "Cliente", id);
            logger.info("Auditoría registrada: {}", accion);
        } catch (Exception e) {
            logger.error("Error al auditar eliminar cliente: {}", e.getMessage());
        }
    }

    @AfterReturning(
            pointcut = "execution(* com.tecsup.exam12.service.ClienteService.eliminarPermanente(..)) && args(id)",
            argNames = "joinPoint,id"
    )
    public void auditarEliminarPermanenteCliente(JoinPoint joinPoint, Long id) {
        try {
            String usuario = obtenerUsuarioActual();
            String accion = "ELIMINAR PERMANENTE CLIENTE - ID: " + id;

            bitacoraService.registrar(usuario, accion, "Cliente", id);
            logger.info("Auditoría registrada: {}", accion);
        } catch (Exception e) {
            logger.error("Error al auditar eliminar permanente cliente: {}", e.getMessage());
        }
    }

    @AfterReturning(
            pointcut = "execution(* com.tecsup.exam12.service.ClienteService.activar(..)) && args(id)",
            argNames = "joinPoint,id"
    )
    public void auditarActivarCliente(JoinPoint joinPoint, Long id) {
        try {
            String usuario = obtenerUsuarioActual();
            String accion = "ACTIVAR CLIENTE - ID: " + id;

            bitacoraService.registrar(usuario, accion, "Cliente", id);
            logger.info("Auditoría registrada: {}", accion);
        } catch (Exception e) {
            logger.error("Error al auditar activar cliente: {}", e.getMessage());
        }
    }

    // ===== AUDITORÍA PARA MESA =====

    @AfterReturning(
            pointcut = "execution(* com.tecsup.exam12.service.MesaService.guardar(..))",
            returning = "result"
    )
    public void auditarGuardarMesa(JoinPoint joinPoint, Object result) {
        try {
            Mesa mesa = (Mesa) result;
            String usuario = obtenerUsuarioActual();
            String accion = "GUARDAR MESA: Número " + mesa.getNumero() +
                    " (Capacidad: " + mesa.getCapacidad() + ", Estado: " + mesa.getEstado() + ")";

            bitacoraService.registrar(usuario, accion, "Mesa", mesa.getIdMesa());
            logger.info("Auditoría registrada: {}", accion);
        } catch (Exception e) {
            logger.error("Error al auditar guardar mesa: {}", e.getMessage());
        }
    }

    @AfterReturning(
            pointcut = "execution(* com.tecsup.exam12.service.MesaService.cambiarEstado(..)) && args(id, nuevoEstado)",
            argNames = "joinPoint,id,nuevoEstado"
    )
    public void auditarCambiarEstadoMesa(JoinPoint joinPoint, Long id, String nuevoEstado) {
        try {
            String usuario = obtenerUsuarioActual();
            String accion = "CAMBIAR ESTADO MESA - ID: " + id + " -> Nuevo estado: " + nuevoEstado;

            bitacoraService.registrar(usuario, accion, "Mesa", id);
            logger.info("Auditoría registrada: {}", accion);
        } catch (Exception e) {
            logger.error("Error al auditar cambiar estado mesa: {}", e.getMessage());
        }
    }

    @AfterReturning(
            pointcut = "execution(* com.tecsup.exam12.service.MesaService.eliminar(..)) && args(id)",
            argNames = "joinPoint,id"
    )
    public void auditarEliminarMesa(JoinPoint joinPoint, Long id) {
        try {
            String usuario = obtenerUsuarioActual();
            String accion = "ELIMINAR MESA - ID: " + id;

            bitacoraService.registrar(usuario, accion, "Mesa", id);
            logger.info("Auditoría registrada: {}", accion);
        } catch (Exception e) {
            logger.error("Error al auditar eliminar mesa: {}", e.getMessage());
        }
    }
}