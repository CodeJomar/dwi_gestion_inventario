package com.multisupply.sgi.dashboard.services;

import com.multisupply.sgi.dashboard.dtos.DashboardOrdenesDTO;
import com.multisupply.sgi.ordenes.entities.enums.TipoOrden;
import com.multisupply.sgi.ordenes.entities.models.Orden;
import com.multisupply.sgi.ordenes.repositories.OrdenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.RoundingMode;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class DashboardOrdenesService {

    private final OrdenRepository ordenRepository;

    public List<DashboardOrdenesDTO> obtenerOrdenesRecientes() {

        List<Orden> lista = ordenRepository
                .findByFechaEliminacionIsNullOrderByFechaCreacionDesc()
                .stream()
                .limit(4)
                .toList();

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("dd MMM yyyy", new Locale("es", "ES"));

        List<DashboardOrdenesDTO> resultado = new ArrayList<>();

        for (Orden o : lista) {

            String css = o.getTipo() == TipoOrden.ENTRADA
                    ? "badge-outline"
                    : "badge-destructive";

            resultado.add(new DashboardOrdenesDTO(
                    o.getId(),
                    o.getProducto().getNombre(),
                    "S/ " + o.getMonto().setScale(2, RoundingMode.HALF_UP),
                    o.getTipo().getDisplayName(),
                    css,
                    o.getFechaCreacion().format(formatter)
            ));
        }

        return resultado;
    }

}
