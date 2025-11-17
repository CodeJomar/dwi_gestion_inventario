package com.multisupply.sgi.productos.services;

import com.multisupply.sgi.productos.services.FileStorageService;
import com.multisupply.sgi.productos.entities.dtos.ProductoDTO;
import com.multisupply.sgi.productos.entities.enums.CategoriaProducto;
import com.multisupply.sgi.productos.entities.enums.EstadoProducto;
import com.multisupply.sgi.productos.entities.models.Producto;
import com.multisupply.sgi.productos.repositories.ProductoRepository;
import com.multisupply.sgi.usuarios.entities.models.Usuario;
import com.multisupply.sgi.usuarios.repositories.UsuarioRepository;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final UsuarioRepository usuarioRepository;
    private final FileStorageService fileStorageService;

    public Page<ProductoDTO> listarProductosPaginado(
            String busqueda, String categoria, String estado,
            Double precioMin, Double precioMax, Pageable pageable) {

        Specification<Producto> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.hasText(busqueda)) {
                predicates.add(cb.like(cb.lower(root.get("nombre")), "%" + busqueda.toLowerCase() + "%"));
            }

            if (StringUtils.hasText(categoria)) {
                try {
                    predicates.add(cb.equal(root.get("categoria"), CategoriaProducto.valueOf(categoria)));
                } catch (IllegalArgumentException e) {

                }
            }

            if (StringUtils.hasText(estado)) {
                try {
                    predicates.add(cb.equal(root.get("estado"), EstadoProducto.valueOf(estado)));
                } catch (IllegalArgumentException e) {

                }
            }

            if (precioMin != null && precioMin > 0) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("precio"), BigDecimal.valueOf(precioMin)));
            }

            if (precioMax != null && precioMax > 0) {
                predicates.add(cb.lessThanOrEqualTo(root.get("precio"), BigDecimal.valueOf(precioMax)));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        return productoRepository.findAll(spec, pageable)
                .map(this::mapToDTO);
    }

    public List<ProductoDTO> listarProductosActivos() {
        return productoRepository.findAllByEstado(EstadoProducto.ACTIVO).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public ProductoDTO crearProducto(ProductoDTO dto, String emailUsuario) {

        if (productoRepository.findByNombre(dto.getNombre()).isPresent()) {
            throw new IllegalArgumentException("Ya existe un producto con el nombre: " + dto.getNombre());
        }

        Usuario usuarioActual = usuarioRepository.findByEmail(emailUsuario)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado."));

        Producto producto = mapToEntity(dto, usuarioActual);
        producto.setId(null);
        producto.setEstado(EstadoProducto.ACTIVO);
        Producto productoGuardado = productoRepository.save(producto);
        return mapToDTO(productoGuardado);
    }

    @Transactional
    public ProductoDTO actualizarProducto(ProductoDTO dto, String emailUsuario, boolean nuevaImagen) {
        Producto producto = productoRepository.findById(dto.getId())
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado con ID: " + dto.getId()));

        Usuario usuarioActual = usuarioRepository.findByEmail(emailUsuario)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado."));

        producto.setNombre(dto.getNombre());
        producto.setDescripcion(dto.getDescripcion());
        producto.setPrecio(dto.getPrecio());
        producto.setStock(dto.getStock());
        producto.setCategoria(dto.getCategoria());
        producto.setEstado(dto.getEstado());

        if (nuevaImagen) {
            producto.setImagen(dto.getImagen());
        }

        producto.setActualizadoPor(usuarioActual);
        Producto productoActualizado = productoRepository.save(producto);
        return mapToDTO(productoActualizado);
    }

    @Transactional
    public void eliminarProducto(String id, String emailUsuario) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado con ID: " + id));

        Usuario usuarioActual = usuarioRepository.findByEmail(emailUsuario)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado."));

        producto.setEstado(EstadoProducto.INACTIVO);
        producto.setActualizadoPor(usuarioActual);
        productoRepository.save(producto);
    }

    public ProductoDTO obtenerProductoPorId(String id) {
        return productoRepository.findById(id)
                .map(this::mapToDTO)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado con ID: " + id));
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
        return producto;
    }
}