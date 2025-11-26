package com.multisupply.sgi.reportes.services;

import com.multisupply.sgi.ordenes.entities.models.Orden;
import com.multisupply.sgi.productos.entities.models.Producto;
import com.multisupply.sgi.reportes.dtos.ReporteOrdenDTO;
import com.multisupply.sgi.reportes.dtos.ReporteProductoDTO;
import com.multisupply.sgi.reportes.dtos.ReporteUsuarioDTO;
import com.multisupply.sgi.usuarios.entities.models.Perfil;
import com.multisupply.sgi.usuarios.entities.models.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReporteMapperService {
    
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    
    // PRODUCTOS
    public List<ReporteProductoDTO> mapProductos(List<Producto> productos) {
        
        return productos.stream().map(p -> new ReporteProductoDTO(
            p.getId(),
            p.getNombre(),
            p.getStock(),
            p.getPrecio(),
            p.getCategoria(),
            p.getEstado(),
            p.getFechaCreacion() != null ? p.getFechaCreacion().format(formatter) : "-",
            p.getCreadoPor(),
            p.getFechaActualizacion() != null ? p.getFechaActualizacion().format(formatter) : "-",
            p.getActualizadoPor()
        )).toList();
    }
    
    // ORDENES
    public List<ReporteOrdenDTO> mapOrdenes(List<Orden> ordenes) {
        
        return ordenes.stream().map(o -> new ReporteOrdenDTO(
            o.getId(),
            o.getProducto().getNombre(),
            o.getCantidad(),
            o.getMonto(),
            o.getMotivo(),
            o.getTipo(),
            o.getFechaCreacion().format(formatter),
            o.getCreadoPor(),
            o.getFechaActualizacion() != null ? o.getFechaActualizacion().format(formatter) : "-",
            o.getActualizadoPor()
        )).toList();
    }
    
    // USUARIOS / PERFILES
    public List<ReporteUsuarioDTO> mapUsuarios(List<Usuario> usuarios, List<Perfil> perfiles) {
        
        return usuarios.stream().map(u -> {
            Perfil p = perfiles.stream()
                .filter(x -> x.getUsuario().getId().equals(u.getId()))
                .findFirst()
                .orElse(null);
            
            return new ReporteUsuarioDTO(
                u.getId(),
                u.getUsername(),
                u.getEmail(),
                u.getRol().getNombre().getDisplayName(),
                u.isActivo() ? "Activo" : "Inactivo",
                p != null ? p.getDni() : "-",
                p != null ? p.getTelefono() : "-",
                p != null ? p.getFechaNacimiento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "-",
                p != null ? p.getPaises().getDisplayName() : "-",
                u.getFechaCreacion().format(formatter)
            );
        }).toList();
    }

}
