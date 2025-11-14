package com.multisupply.sgi.usuarios.controllers;

import com.multisupply.sgi.usuarios.entities.dtos.DetallesUsuarioDTO;
import com.multisupply.sgi.usuarios.entities.dtos.UsuarioDTO;
import com.multisupply.sgi.usuarios.entities.models.Rol;
import com.multisupply.sgi.usuarios.repositories.RolRepository;
import com.multisupply.sgi.usuarios.services.PerfilService;
import com.multisupply.sgi.usuarios.services.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@Controller
@RequestMapping("/auth/usuarios")
@RequiredArgsConstructor
public class UsuarioController {
    
    private final UsuarioService usuarioService;
    private final RolRepository rolRepository;
    
    @GetMapping("")
    public String listarUsuarios(Model model) {
        model.addAttribute("usuarios", usuarioService.listarUsuarios());
        model.addAttribute("roles", rolRepository.findAll());
        
        if (!model.containsAttribute("nuevoUsuario")) {
            model.addAttribute("nuevoUsuario", new UsuarioDTO());
        }
        return "usuarios"; // renderiza la página /auth/usuarios
    }
    
    @PostMapping("/crear")
    public String crearUsuario(@Valid @ModelAttribute("nuevoUsuario") UsuarioDTO usuarioDTO,
                               BindingResult result,
                               @RequestParam("rolId") Long rolId,
                               RedirectAttributes redirect) {
        
        if (result.hasErrors()) {
            redirect.addFlashAttribute("org.springframework.validation.BindingResult.nuevoUsuario", result);
            redirect.addFlashAttribute("nuevoUsuario", usuarioDTO);
            redirect.addFlashAttribute("error", true);
            redirect.addFlashAttribute("mensaje", "Por favor, revisa los errores del formulario.");
            return "redirect:/auth/usuarios";
        }
        
        try {
            Rol rol = rolRepository.findById(rolId)
                .orElseThrow(() -> new IllegalArgumentException("Rol no encontrado."));
            
            usuarioService.crearUsuario(usuarioDTO, rol.getNombre());
            redirect.addFlashAttribute("exito", true);
            redirect.addFlashAttribute("mensaje", "Usuario creado correctamente.");
        } catch (IllegalArgumentException e) {
            redirect.addFlashAttribute("error", true);
            redirect.addFlashAttribute("mensaje", e.getMessage());
        }
        
        return "redirect:/auth/usuarios";
    }
    
    @GetMapping("/detalle/{id}")
    public String verDetalles(@PathVariable String id, Model model, RedirectAttributes redirect) {
        return usuarioService.obtenerUsuario(id)
            .map(usuarioDTO -> {
                model.addAttribute("usuarios", usuarioService.listarUsuarios()); // para la tabla
                model.addAttribute("roles", rolRepository.findAll());
                model.addAttribute("usuario", usuarioDTO); // para rellenar modal
                model.addAttribute("abrirModalDetalle", true);
                if (!model.containsAttribute("nuevoUsuario")) {
                    model.addAttribute("nuevoUsuario", new UsuarioDTO());
                }
                return "usuarios";
            })
            .orElseGet(() -> {
                redirect.addFlashAttribute("error", true);
                redirect.addFlashAttribute("mensaje", "Usuario no encontrado.");
                return "redirect:/auth/usuarios";
            });
    }
    
    @GetMapping("/estado/{id}")
    public String cambiarEstado(@PathVariable String id, RedirectAttributes redirect) {
        boolean actualizado = usuarioService.cambiarEstado(id);
        if (actualizado) {
            redirect.addFlashAttribute("exito", true);
            redirect.addFlashAttribute("mensaje", "Estado de usuario actualizado correctamente.");
        } else {
            redirect.addFlashAttribute("error", true);
            redirect.addFlashAttribute("mensaje", "No se pudo actualizar el estado del usuario.");
        }
        return "redirect:/auth/usuarios";
    }
    
    @GetMapping("/detalle-json/{id}")
    @ResponseBody
    public ResponseEntity<?> obtenerDetalleJson(@PathVariable String id) {
        return usuarioService.obtenerDetallesUsuario(id)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).<DetallesUsuarioDTO>build());
    }
}
