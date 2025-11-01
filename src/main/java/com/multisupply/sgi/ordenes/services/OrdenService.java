package com.multisupply.sgi.ordenes.services;

import com.multisupply.sgi.ordenes.entities.dtos.OrdenDTO;
import com.multisupply.sgi.ordenes.entities.models.Orden;
import com.multisupply.sgi.ordenes.repositories.OrdenRepository;
import com.multisupply.sgi.productos.entities.models.Producto;
import com.multisupply.sgi.productos.repositories.ProductoRepository;
import com.multisupply.sgi.usuarios.entities.models.Usuario;
import com.multisupply.sgi.usuarios.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrdenService {
    
    private final OrdenRepository ordenRepository;
    private final ProductoRepository productoRepository;
    private final UsuarioRepository usuarioRepository;
    
    // ======================================================
    // IMPORTANTE: NO SE VALIDA NADA EN EL SERVICIO, SOLO EN EL DTO O CREAR VALIDACIONES PERSONALIZADAS.
    // IMPORTANTE: SE APLICA LÓGICA BÁSICA
    // IMPORTANTE: IR POCO A POCO
    // ======================================================
    
    /**
     * Registrar una nueva orden.
     * - Se obtiene el producto desde la base de datos.
     * - Se calcula el monto total = precio * cantidad.
     * - Si la orden es de tipo SALIDA, se debe restar stock.
     * - Si la orden es de tipo ENTRADA, se debe sumar stock.
     * - Se asigna el usuario autenticado como creador.
     * - Se registra la fecha de creación.
     */
    public OrdenDTO crearOrden(OrdenDTO dto) {
        // TODO: Implementar creación de orden + actualización de stock
        return null;
    }
    
    /**
     * Listar todas las órdenes existentes.
     * - Se puede filtrar por tipo, motivo, fecha o usuario.
     * - Idealmente, se ordenan por fecha de creación descendente.
     * - Se devuelve una lista de DTOs.
     */
    public List<OrdenDTO> listarOrdenes() {
        // TODO: Implementar consulta de órdenes
        return List.of();
    }
    
    /**
     * Buscar una orden por ID.
     * - Sirve para mostrar el detalle de una transacción.
     * - Si no existe, se lanza una excepción.
     */
    public OrdenDTO obtenerOrdenPorId(String id) {
        // TODO: Implementar búsqueda individual
        return null;
    }
    
    /**
     * Actualizar una orden.
     * - En general, las órdenes no deberían modificarse una vez creadas,
     *   pero puede ser necesario corregir errores humanos.
     * - Se debe recalcular el monto si cambia la cantidad o el producto.
     * - También debe actualizar el stock del producto asociado.
     */
    public OrdenDTO actualizarOrden(String id, OrdenDTO dto) {
        // TODO: Implementar actualización con lógica de stock
        return null;
    }
    
    /**
     * Eliminar lógicamente una orden.
     * - No se borra físicamente de la base de datos.
     * - Se marca la fecha y el usuario que eliminó.
     * - Si es una orden SALIDA, se debe revertir el stock (sumar cantidad).
     * - Si es una orden ENTRADA, se debe revertir restando el stock.
     */
    public void eliminarOrden(String id, String emailUsuario) {
        // TODO: Implementar eliminación lógica y reversión de stock
    }
    
    
    private OrdenDTO mapToDTO(Orden orden) {
        OrdenDTO dto = new OrdenDTO();
        dto.setId(orden.getId());
        dto.setCantidad(orden.getCantidad());
        dto.setMonto(orden.getMonto());
        dto.setMotivo(orden.getMotivo());
        dto.setTipo(orden.getTipo());
        
        if (orden.getProducto() != null) {
            dto.setProductoId(orden.getProducto().getId());
            dto.setProductoNombre(orden.getProducto().getNombre());
        }
        
        dto.setFechaCreacion(orden.getFechaCreacion());
        dto.setCreadoPor(orden.getCreadoPor() != null ? orden.getCreadoPor().getEmail() : null);
        dto.setFechaActualizacion(orden.getFechaActualizacion());
        dto.setActualizadoPor(orden.getActualizadoPor() != null ? orden.getActualizadoPor().getEmail() : null);
        dto.setFechaEliminacion(orden.getFechaEliminacion());
        dto.setEliminadoPor(orden.getEliminadoPor() != null ? orden.getEliminadoPor().getEmail() : null);
        
        return dto;
    }
    
    private Orden mapToEntity(OrdenDTO dto, Producto producto, Usuario usuarioActual) {
        Orden orden = new Orden();
        orden.setId(dto.getId());
        orden.setCantidad(dto.getCantidad());
        orden.setMonto(dto.getMonto());
        orden.setMotivo(dto.getMotivo());
        orden.setTipo(dto.getTipo());
        orden.setProducto(producto);
        orden.setCreadoPor(usuarioActual);
        orden.setFechaCreacion(dto.getFechaCreacion() != null ? dto.getFechaCreacion() : LocalDateTime.now());
        return orden;
    }
    
}
