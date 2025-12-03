package com.multisupply.sgi.dashboard.services;

import com.multisupply.sgi.dashboard.dtos.DashboardTarjetasDTO;
import com.multisupply.sgi.ordenes.entities.enums.TipoOrden;
import com.multisupply.sgi.ordenes.repositories.OrdenRepository;
import com.multisupply.sgi.productos.entities.enums.EstadoProducto;
import com.multisupply.sgi.productos.repositories.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class DashboardTarjetasService {

    private final ProductoRepository productoRepository;
    private final OrdenRepository ordenRepository;

    public DashboardTarjetasDTO obtenerEstadisticas() {

        long productosActivos =
                productoRepository.countByEstado(EstadoProducto.ACTIVO);

        int year = LocalDate.now().getYear();
        int month = LocalDate.now().getMonthValue();

        BigDecimal ingresosAnuales =
                ordenRepository.sumMontoSalidaByYear(year);
        if (ingresosAnuales == null) ingresosAnuales = BigDecimal.ZERO;

        BigDecimal ingresosMensuales =
                ordenRepository.sumMontoSalidaByMonth(year, month);
        if (ingresosMensuales == null) ingresosMensuales = BigDecimal.ZERO;

        long stockBajo = productoRepository.countByStockLessThan(10);

        return new DashboardTarjetasDTO(
                productosActivos,
                "S/ " + ingresosAnuales.setScale(2, RoundingMode.HALF_UP),
                "S/ " + ingresosMensuales.setScale(2, RoundingMode.HALF_UP),
                stockBajo
        );
    }
}