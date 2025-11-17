package com.multisupply.sgi.productos.controllers;

import com.multisupply.sgi.productos.services.FileStorageService;
import com.multisupply.sgi.productos.entities.dtos.ProductoDTO;
import com.multisupply.sgi.productos.entities.enums.CategoriaProducto;
import com.multisupply.sgi.productos.entities.enums.EstadoProducto;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/auth/productos")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService productoService;
    private final FileStorageService fileStorageService;

    @GetMapping
    public String mostrarPaginaProductos(
            Model model,
            @RequestParam(value = "editarId", required = false) String editarId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size,
            @RequestParam(required = false) String busqueda,
            @RequestParam(required = false) String categoria,
            @RequestParam(required = false) String estado,
            @RequestParam(required = false) Double precioMin,
            @RequestParam(required = false) Double precioMax
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("fechaCreacion").descending());
        Page<ProductoDTO> productosPage = productoService.listarProductosPaginado(
                busqueda, categoria, estado, precioMin, precioMax, pageable
        );

        model.addAttribute("productos", productosPage);
        model.addAttribute("categorias", CategoriaProducto.values());
        model.addAttribute("estados", EstadoProducto.values());
        model.addAttribute("busqueda", busqueda);
        model.addAttribute("categoria", categoria);
        model.addAttribute("estado", estado);
        model.addAttribute("precioMin", precioMin);
        model.addAttribute("precioMax", precioMax);

        if (model.containsAttribute("productoForm")) {
        } else if (editarId != null) {
            try {
                model.addAttribute("productoForm", productoService.obtenerProductoPorId(editarId));
                model.addAttribute("isEditMode", true);
                model.addAttribute("abrirModal", true);
            } catch (Exception e) {
                model.addAttribute("error", "Error al cargar producto: " + e.getMessage());
                model.addAttribute("productoForm", new ProductoDTO());
                model.addAttribute("isEditMode", false);
            }
        } else {
            ProductoDTO nuevoProducto = new ProductoDTO();
            nuevoProducto.setEstado(EstadoProducto.ACTIVO);
            model.addAttribute("productoForm", nuevoProducto);
            model.addAttribute("isEditMode", false);
        }
        return "productos";
    }

    @PostMapping("/crear")
    public String crearProducto(
            @Valid @ModelAttribute("productoForm") ProductoDTO dto,
            BindingResult result,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
            RedirectAttributes redirect,
            @AuthenticationPrincipal Usuario usuarioLogueado) {

        if (result.hasErrors()) {
            redirect.addFlashAttribute("productoForm", dto);
            redirect.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + "productoForm", result);
            redirect.addFlashAttribute("isEditMode", false);
            redirect.addFlashAttribute("abrirModal", true);
            return "redirect:/auth/productos";
        }

        try {
            if (imageFile != null && !imageFile.isEmpty()) {
                String imageUrl = fileStorageService.storeFile(imageFile);
                dto.setImagen(imageUrl);
            } else {
                dto.setImagen(null);
            }

            productoService.crearProducto(dto, usuarioLogueado.getEmail());
            redirect.addFlashAttribute("exito", "¡Producto creado exitosamente!");

        } catch (Exception e) {
            redirect.addFlashAttribute("error", "Error al crear producto: " + e.getMessage());
            redirect.addFlashAttribute("productoForm", dto);
            redirect.addFlashAttribute("isEditMode", false);
            redirect.addFlashAttribute("abrirModal", true);
        }
        return "redirect:/auth/productos";
    }

    @PostMapping("/editar")
    public String editarProducto(
            @Valid @ModelAttribute("productoForm") ProductoDTO dto,
            BindingResult result,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
            RedirectAttributes redirect,
            @AuthenticationPrincipal Usuario usuarioLogueado) {

        if (result.hasErrors()) {
            redirect.addFlashAttribute("productoForm", dto);
            redirect.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + "productoForm", result);
            redirect.addFlashAttribute("isEditMode", true);
            redirect.addFlashAttribute("abrirModal", true);
            return "redirect:/auth/productos";
        }

        try {
            boolean nuevaImagen = false;
            if (imageFile != null && !imageFile.isEmpty()) {
                String imageUrl = fileStorageService.storeFile(imageFile);
                dto.setImagen(imageUrl);
                nuevaImagen = true;
            }

            productoService.actualizarProducto(dto, usuarioLogueado.getEmail(), nuevaImagen);
            redirect.addFlashAttribute("exito", "¡Producto actualizado exitosamente!");

        } catch (Exception e) {
            redirect.addFlashAttribute("error", "Error al actualizar producto: " + e.getMessage());
            redirect.addFlashAttribute("productoForm", dto);
            redirect.addFlashAttribute("isEditMode", true);
            redirect.addFlashAttribute("abrirModal", true);
        }
        return "redirect:/auth/productos";
    }

    @PostMapping("/eliminar")
    public String eliminarProducto(
            @RequestParam("id") String id,
            @AuthenticationPrincipal Usuario usuarioLogueado,
            RedirectAttributes redirect) {

        try {
            productoService.eliminarProducto(id, usuarioLogueado.getEmail());
            redirect.addFlashAttribute("exito", "Producto desactivado correctamente.");
        } catch (Exception e) {
            redirect.addFlashAttribute("error", "Error al desactivar el producto: " + e.getMessage());
        }
        return "redirect:/auth/productos";
    }
}