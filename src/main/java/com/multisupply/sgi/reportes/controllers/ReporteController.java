package com.multisupply.sgi.reportes.controllers;

import com.multisupply.sgi.ordenes.repositories.OrdenRepository;
import com.multisupply.sgi.productos.repositories.ProductoRepository;
import com.multisupply.sgi.reportes.dtos.ReporteTarjetaDTO;
import com.multisupply.sgi.reportes.services.ReporteExcelService;
import com.multisupply.sgi.reportes.services.ReporteMapperService;
import com.multisupply.sgi.reportes.services.ReporteTarjetaService;
import com.multisupply.sgi.usuarios.repositories.PerfilRepository;
import com.multisupply.sgi.usuarios.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Controller
@RequestMapping("/auth/reportes")
@RequiredArgsConstructor
public class ReporteController {
    
    private final ReporteExcelService excelService;
    private final ReporteMapperService mapper;
    private final ProductoRepository productoRepository;
    private final OrdenRepository ordenRepository;
    private final UsuarioRepository usuarioRepository;
    private final PerfilRepository perfilRepository;
    private final ReporteTarjetaService reporteTarjetaService;
    
    private ResponseEntity<byte[]> descargar(XSSFWorkbook wb, String nombre) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        wb.write(out);
        wb.close();
        
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=" + nombre);
        
        return ResponseEntity.ok()
            .headers(headers)
            .body(out.toByteArray());
    }
    
    @GetMapping("")
    public String paginaReportes(Model model) {
        ReporteTarjetaDTO datos = reporteTarjetaService.obtenerDashboard();
        model.addAttribute("tarjeta", datos);
        
        return "reportes";
   }
    
    // PRODUCTOS
    @GetMapping("/productos")
    public ResponseEntity<byte[]> reporteProductos() throws IOException {
        var lista = mapper.mapProductos(productoRepository.findAll());
        return descargar(excelService.generarExcelProductos(lista), "reporte_productos.xlsx");
    }
    
    // ORDENES
    @GetMapping("/ordenes")
    public ResponseEntity<byte[]> reporteOrdenes() throws IOException {
        var lista = mapper.mapOrdenes(ordenRepository.findAll());
        return descargar(excelService.generarExcelOrdenes(lista), "reporte_movimientos.xlsx");
    }
    
    // USUARIOS
    @GetMapping("/usuarios")
    public ResponseEntity<byte[]> reporteUsuarios() throws IOException {
        var lista = mapper.mapUsuarios(
            usuarioRepository.findAll(),
            perfilRepository.findAll()
        );
        return descargar(excelService.generarExcelUsuarios(lista), "reporte_usuarios.xlsx");
    }

}
