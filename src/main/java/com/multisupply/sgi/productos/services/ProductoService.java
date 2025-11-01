package com.multisupply.sgi.productos.services;

import com.multisupply.sgi.productos.entities.dtos.ProductoDTO;
import com.multisupply.sgi.productos.entities.enums.EstadoProducto;
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
public class ProductoService {
    
    private final ProductoRepository productoRepository;
    private final UsuarioRepository usuarioRepository;
    
    // ======================================================
    // IMPORTANTE: NO SE VALIDA NADA EN EL SERVICIO, SOLO EN EL DTO O CREAR VALIDACIONES PERSONALIZADAS.
    // IMPORTANTE: SE APLICA LÓGICA BÁSICA
    // IMPORTANTE: IR POCO A POCO
    // ======================================================
    
    /**
     * Registrar un nuevo producto.
     * - Se valida que el nombre del producto no esté repetido.
     * - Se establece el usuario autenticado como "creadoPor".
     * - Se inicializa la fecha de creación automáticamente.
     * - El estado del producto se crea como ACTIVO por defecto.
     * - Se debe permitir adjuntar una imagen (ruta o URL).
     * - Retorna el ProductoDTO creado.
     */
    public ProductoDTO crearProducto(ProductoDTO dto) {
        // TODO: Implementar lógica de creación con validaciones y mapeo DTO->Entidad
        return null;
    }
    
    /**
     * Listar todos los productos existentes.
     * - Se pueden agregar filtros por estado o categoría.
     * - Idealmente, se usa paginación si hay muchos registros.
     * - Retorna una lista de DTOs.
     */
    public List<ProductoDTO> listarProductos() {
        // TODO: Implementar búsqueda y mapeo a DTO
        return List.of();
    }
    
    /**
     * Buscar un producto por su ID.
     * - Se utiliza para mostrar detalles antes de editar o eliminar.
     * - Si no existe, se lanza una excepción controlada.
     */
    public ProductoDTO obtenerProductoPorId(String id) {
        // TODO: Implementar búsqueda individual
        return null;
    }
    
    /**
     * Actualizar un producto existente.
     * - Solo ciertos campos deben poder actualizarse (nombre, descripción, precio, imagen, stock).
     * - Se registra quién hizo la modificación y en qué fecha.
     * - Se debe mantener el histórico del creador original.
     */
    public ProductoDTO actualizarProducto(String id, ProductoDTO dto) {
        // TODO: Implementar actualización parcial + auditoría
        return null;
    }
    
    /**
     * Cambiar el estado de un producto (por ejemplo, de ACTIVO a INACTIVO).
     * - No se debe eliminar físicamente el producto.
     * - Esto ayuda a mantener la integridad referencial con órdenes anteriores.
     */
    public void cambiarEstado(String id, EstadoProducto nuevoEstado) {
        // TODO: Implementar cambio de estado con fechaActualización
    }
    
    /**
     * Eliminar lógicamente un producto (estado).
     * - Se marca la fecha de eliminación y el usuario que lo eliminó.
     * - No se elimina de la base de datos para preservar trazabilidad.
     */
    public void eliminarProducto(String id, String emailUsuario) {
        // TODO: Implementar eliminación lógica
    }
    
    private ProductoDTO mapToDTO(Producto producto) {
        ProductoDTO dto = new ProductoDTO();
        dto.setId(producto.getId());
        dto.setNombre(producto.getNombre());
        dto.setStock(producto.getStock());
        dto.setImagen(producto.getImagen());
        dto.setPrecio(producto.getPrecio());
        dto.setDescripcion(producto.getDescripcion());
        dto.setCategoria(producto.getCategoria());
        dto.setEstado(producto.getEstado());
        dto.setFechaCreacion(producto.getFechaCreacion());
        dto.setCreadoPor(producto.getCreadoPor() != null ? producto.getCreadoPor().getEmail() : null);
        dto.setFechaActualizacion(producto.getFechaActualizacion());
        dto.setActualizadoPor(producto.getActualizadoPor() != null ? producto.getActualizadoPor().getEmail() : null);
        return dto;
    }
    
    private Producto mapToEntity(ProductoDTO dto, Usuario usuarioActual) {
        Producto producto = new Producto();
        producto.setId(dto.getId());
        producto.setNombre(dto.getNombre());
        producto.setStock(dto.getStock());
        producto.setImagen(dto.getImagen());
        producto.setPrecio(dto.getPrecio());
        producto.setDescripcion(dto.getDescripcion());
        producto.setCategoria(dto.getCategoria());
        producto.setEstado(dto.getEstado());
        producto.setCreadoPor(usuarioActual);
        producto.setFechaCreacion(dto.getFechaCreacion() != null ? dto.getFechaCreacion() : LocalDateTime.now());
        return producto;
    }
    
}
