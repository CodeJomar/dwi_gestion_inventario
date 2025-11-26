package com.multisupply.sgi.reportes.services;

import com.multisupply.sgi.ordenes.entities.enums.TipoOrden;
import com.multisupply.sgi.ordenes.repositories.OrdenRepository;
import com.multisupply.sgi.productos.entities.enums.EstadoProducto;
import com.multisupply.sgi.productos.repositories.ProductoRepository;
import com.multisupply.sgi.reportes.dtos.ReporteTarjetaDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class ReporteTarjetaService {
    
    private final ProductoRepository productoRepository;
    private final OrdenRepository ordenRepository;
    
    public long obtenerTotalProductosActivos() {
        return productoRepository.countByEstado(EstadoProducto.ACTIVO);
    }
    
    public long obtenerTotalOrdenesEntrada() {
        return ordenRepository.countByTipo(TipoOrden.ENTRADA);
    }
    
    public long obtenerTotalOrdenesSalida() {
        return ordenRepository.countByTipo(TipoOrden.SALIDA);
    }
    
    public BigDecimal obtenerTotalGanancias() {
        return ordenRepository.totalMontoByTipo(TipoOrden.SALIDA);
    }
    
    // Empaquetado para enviar al controlador
    public ReporteTarjetaDTO obtenerDashboard() {
        ReporteTarjetaDTO dto = new ReporteTarjetaDTO();
        
        dto.setProductosActivos(obtenerTotalProductosActivos());
        dto.setOrdenesEntrada(obtenerTotalOrdenesEntrada());
        dto.setOrdenesSalida(obtenerTotalOrdenesSalida());
        dto.setGanancias(obtenerTotalGanancias());
        
        return dto;
    }
}
