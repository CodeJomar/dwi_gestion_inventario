package com.multisupply.sgi.dashboard.services;

import com.multisupply.sgi.dashboard.dtos.DashboardBarrasDTO;
import com.multisupply.sgi.productos.entities.enums.EstadoProducto;
import com.multisupply.sgi.productos.entities.models.Producto;
import com.multisupply.sgi.productos.repositories.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardBarrasService {
    private final ProductoRepository productoRepository;

    public List<DashboardBarrasDTO> obtenerProductosConStockBajo() {

        List<Producto> productos = productoRepository.findAll()
                .stream()
                .filter(p -> p.getEstado().equals(EstadoProducto.ACTIVO))
                .collect(Collectors.toList());

        List<DashboardBarrasDTO> lista = new ArrayList<>();

        for (Producto p : productos) {

            int stockMaximo = calcularStockMaximo(p); // puedes ajustar la lógica
            int porcentaje = (int) ((p.getStock() / (double) stockMaximo) * 100);

            String nivel = calcularNivel(porcentaje);
            String cssClass = nivel.equals("Crítico") ? "badge-destructive"
                    : nivel.equals("Bajo") ? "badge-warning"
                    : "badge-secondary";

            DashboardBarrasDTO dto = new DashboardBarrasDTO();
            dto.setId(p.getId());
            dto.setNombre(p.getNombre());
            dto.setStockActual(p.getStock());
            dto.setStockMaximo(stockMaximo);
            dto.setPorcentaje(porcentaje);
            dto.setNivel(nivel);
            dto.setCssClass(cssClass);

            // incluir solo productos con % menor a 40
            if (porcentaje <= 40) {
                lista.add(dto);
            }
        }

        return lista;
    }

    private int calcularStockMaximo(Producto p) {
        // Puedes basarlo en reglas de negocio:
        // Ejemplo simple: stock máximo = 100
        return 100;
    }

    private String calcularNivel(int porcentaje) {
        if (porcentaje <= 20) return "Crítico";
        if (porcentaje <= 40) return "Bajo";
        if (porcentaje <= 60) return "Moderado";
        return"Normal";
}

}