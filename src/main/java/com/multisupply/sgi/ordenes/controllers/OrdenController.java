package com.multisupply.sgi.ordenes.controllers;

import com.multisupply.sgi.ordenes.entities.dtos.OrdenDTO;
import com.multisupply.sgi.ordenes.entities.enums.MotivoOrden;
import com.multisupply.sgi.ordenes.entities.enums.TipoOrden;
import com.multisupply.sgi.ordenes.services.OrdenService;
import com.multisupply.sgi.productos.services.ProductoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.multisupply.sgi.usuarios.entities.models.Usuario;

@Controller
@RequestMapping("/auth/ordenes")
@RequiredArgsConstructor
public class OrdenController {

    private final OrdenService ordenService;
    private final ProductoService productoService;

    @GetMapping
    public String mostrarPaginaOrdenes(Model model) {

        model.addAttribute("ordenes", ordenService.listarOrdenes());

        if (!model.containsAttribute("nuevaOrden")) {
            model.addAttribute("nuevaOrden", new OrdenDTO());
        }
        model.addAttribute("productosActivos", productoService.listarProductos());
        model.addAttribute("tiposOrden", TipoOrden.values());
        model.addAttribute("motivosOrden", MotivoOrden.values());

        return "ordenes";
    }

    @PostMapping("/crear")
    public String crearOrden(
            @Valid @ModelAttribute("nuevaOrden") OrdenDTO dto,
            BindingResult result,
            RedirectAttributes redirect,
            @AuthenticationPrincipal Usuario usuarioLogueado,
            Model model) {

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