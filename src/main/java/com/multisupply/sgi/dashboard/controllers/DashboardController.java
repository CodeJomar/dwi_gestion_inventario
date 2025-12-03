package com.multisupply.sgi.dashboard.controllers;

import com.multisupply.sgi.dashboard.dtos.DashboardBarrasDTO;
import com.multisupply.sgi.dashboard.dtos.DashboardOrdenesDTO;
import com.multisupply.sgi.dashboard.dtos.DashboardTarjetasDTO;
import com.multisupply.sgi.dashboard.services.DashboardBarrasService;
import com.multisupply.sgi.dashboard.services.DashboardOrdenesService;
import com.multisupply.sgi.dashboard.services.DashboardTarjetasService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/auth/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardBarrasService dashboardService;
    private final DashboardOrdenesService dashboardOrdenesService;
    private final DashboardTarjetasService dashboardTarjetasService;

    @GetMapping()
    public String vistaDashboard() {
        return "dashboard";  // tu vista principal del dashboard
    }

    @GetMapping("/ordenes-recientes")
    @ResponseBody
    public List<DashboardOrdenesDTO> obtenerOrdenesRecientes() {
        return dashboardOrdenesService.obtenerOrdenesRecientes();
    }

    @GetMapping("/productos-stock-bajo")
    @ResponseBody
    public List<DashboardBarrasDTO> productosStockBajo() {
        return dashboardService.obtenerProductosConStockBajo();
    }

    @GetMapping("/tarjetas")
    @ResponseBody
    public DashboardTarjetasDTO obtenerTarjetas() {
        return dashboardTarjetasService.obtenerEstadisticas();
    }
}