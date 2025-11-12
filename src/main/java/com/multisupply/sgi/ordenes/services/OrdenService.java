package com.multisupply.sgi.ordenes.services;

import com.multisupply.sgi.ordenes.entities.dtos.OrdenDTO;
import com.multisupply.sgi.ordenes.entities.enums.TipoOrden;
import com.multisupply.sgi.ordenes.entities.models.Orden;
import com.multisupply.sgi.ordenes.repositories.OrdenRepository;
import com.multisupply.sgi.productos.entities.enums.EstadoProducto;
import com.multisupply.sgi.productos.entities.models.Producto;
import com.multisupply.sgi.productos.repositories.ProductoRepository;
import com.multisupply.sgi.usuarios.entities.models.Usuario;
import com.multisupply.sgi.usuarios.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrdenService {

    private final OrdenRepository ordenRepository;
    private final ProductoRepository productoRepository;
    private final UsuarioRepository usuarioRepository;

    @Transactional
    public OrdenDTO crearOrden(OrdenDTO dto, String emailUsuario) {

        Producto producto = productoRepository.findById(dto.getProductoId())
                .orElseThrow(() -> new IllegalArgumentException("Producto no existe."));

        Usuario usuario = usuarioRepository.findByEmail(emailUsuario)
                .orElseThrow(() -> new IllegalArgumentException("Usuario creador no existe."));

        if (producto.getEstado() != EstadoProducto.ACTIVO) {
            throw new IllegalArgumentException("El producto seleccionado no está activo.");
        }

        int cantidad = dto.getCantidad();
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a cero.");
        }

        if (dto.getTipo() == TipoOrden.SALIDA) {
            if (producto.getStock() < cantidad) {
                throw new IllegalArgumentException("Stock insuficiente. Disponible: " + producto.getStock());
            }
            producto.setStock(producto.getStock() - cantidad);
        } else if (dto.getTipo() == TipoOrden.ENTRADA) {
            producto.setStock(producto.getStock() + cantidad);
        }

        BigDecimal monto = producto.getPrecio().multiply(new BigDecimal(cantidad));

        Orden orden = new Orden();
        orden.setCantidad(cantidad);
        orden.setMonto(monto);
        orden.setMotivo(dto.getMotivo());
        orden.setTipo(dto.getTipo());
        orden.setProducto(producto);
        orden.setCreadoPor(usuario);

        productoRepository.save(producto);
        Orden ordenGuardada = ordenRepository.save(orden);

        return mapToDTO(ordenGuardada);
    }

    public List<OrdenDTO> listarOrdenes() {
        return ordenRepository.findAll().stream()
                .sorted((o1, o2) -> o2.getFechaCreacion().compareTo(o1.getFechaCreacion()))
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public OrdenDTO obtenerOrdenPorId(String id) {
        return ordenRepository.findById(id)
                .map(this::mapToDTO)
                .orElseThrow(() -> new IllegalArgumentException("Orden no encontrada"));
    }

    public OrdenDTO actualizarOrden(String id, OrdenDTO dto) {
        // TODO: Implementar actualización con lógica de stock
        return null;
    }

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