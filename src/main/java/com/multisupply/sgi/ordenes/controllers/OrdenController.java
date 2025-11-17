package com.multisupply.sgi.ordenes.controllers;

import com.multisupply.sgi.ordenes.entities.dtos.OrdenDTO;
import com.multisupply.sgi.ordenes.entities.enums.MotivoOrden;
import com.multisupply.sgi.ordenes.entities.enums.TipoOrden;
import com.multisupply.sgi.ordenes.services.OrdenService;
import com.multisupply.sgi.productos.services.ProductoService;
import com.multisupply.sgi.usuarios.entities.models.Usuario;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/auth/ordenes")
@RequiredArgsConstructor
public class OrdenController {

    private final OrdenService ordenService;
    private final ProductoService productoService;

    @GetMapping
    public String mostrarPaginaOrdenes(
            Model model,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(required = false) String busqueda,
            @RequestParam(required = false) String tipo,
            @RequestParam(required = false) String motivo,
            @RequestParam(required = false) String fechaDesde
    ) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("fechaCreacion").descending());
        Page<OrdenDTO> ordenesPage = ordenService.listarOrdenes(busqueda, tipo, motivo, fechaDesde, pageable);
        model.addAttribute("ordenes", ordenesPage);
        model.addAttribute("busqueda", busqueda);
        model.addAttribute("tipo", tipo);
        model.addAttribute("motivo", motivo);
        model.addAttribute("fechaDesde", fechaDesde);

        if (!model.containsAttribute("nuevaOrden")) {
            model.addAttribute("nuevaOrden", new OrdenDTO());
        }
        model.addAttribute("productosActivos", productoService.listarProductosActivos());
        model.addAttribute("tiposOrden", TipoOrden.values());
        model.addAttribute("motivosOrden", MotivoOrden.values());

        return "ordenes";
    }

    @GetMapping("/{id}")
    public String verDetalleOrden(@PathVariable("id") String id, Model model, RedirectAttributes redirect) {
        try {
            OrdenDTO orden = ordenService.obtenerOrdenPorId(id);
            model.addAttribute("orden", orden);
            return "orden_detalle";
        } catch (IllegalArgumentException e) {
            redirect.addFlashAttribute("error", "Error: La orden que buscas no existe.");
            return "redirect:/auth/ordenes";
        }
    }

    @PostMapping("/crear")
    public String crearOrden(
            @Valid @ModelAttribute("nuevaOrden") OrdenDTO dto,
            BindingResult result,
            RedirectAttributes redirect,
            @AuthenticationPrincipal Usuario usuarioLogueado) {

        if (result.hasErrors()) {
            redirect.addFlashAttribute("nuevaOrden", dto);
            redirect.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + "nuevaOrden", result);
            redirect.addFlashAttribute("abrirModal", true);
            return "redirect:/auth/ordenes";
        }

        try {
            ordenService.crearOrden(dto, usuarioLogueado.getEmail());
            redirect.addFlashAttribute("exito", "¡Orden creada exitosamente! El stock ha sido actualizado.");

        } catch (IllegalArgumentException e) {
            redirect.addFlashAttribute("error", e.getMessage());
            redirect.addFlashAttribute("nuevaOrden", dto);
            redirect.addFlashAttribute("abrirModal", true);
        } catch (Exception e) {
            redirect.addFlashAttribute("error", "Error inesperado al crear la orden.");
            redirect.addFlashAttribute("nuevaOrden", dto);
            redirect.addFlashAttribute("abrirModal", true);
        }

        return "redirect:/auth/ordenes";
    }
}