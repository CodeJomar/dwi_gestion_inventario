package com.multisupply.sgi.usuarios.controllers;

import com.multisupply.sgi.usuarios.entities.dtos.CambioPasswordDTO;
import com.multisupply.sgi.usuarios.entities.dtos.UsuarioDTO;
import com.multisupply.sgi.usuarios.services.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class AuthController {
    
    private final UsuarioService usuarioService;
    
    @GetMapping("/")
    public String mostrarWebLogin(Model model) {
        if (!model.containsAttribute("cambioPassword")) {
            model.addAttribute("cambioPassword", new CambioPasswordDTO());
        }
        return "auth_login";
    }
    
    @GetMapping("/registro")
    public String mostrarWebRegistro(Model model) {
        if (!model.containsAttribute("usuario")) {
            model.addAttribute("usuario", new UsuarioDTO());
        }
        return "auth_registro";
    }
    
    @PostMapping("/registro")
    public String registrar(
        @Valid @ModelAttribute("usuario") UsuarioDTO dto,
        BindingResult result,
        Model model) {
        
        if (result.hasErrors()) {
            model.addAttribute("mostrarError", true);
            return "auth_registro";
        }
        
        try {
            usuarioService.registrarUsuario(dto);
            return "redirect:/?registroExitoso=true";
            
        } catch (IllegalArgumentException ex) {
            model.addAttribute("usuario", dto);
            model.addAttribute("mostrarError", true);
            model.addAttribute("mensajeError", ex.getMessage());
            return "auth_registro";
        }
    }
    
    
    @PostMapping("/cambiar-password")
    public String cambiarPassword(
        @Valid @ModelAttribute("cambioPassword") CambioPasswordDTO dto,
        BindingResult result,
        Model model,
        RedirectAttributes redirect) {
        
        // 🔹 Si hay errores de validación en los campos
        if (result.hasErrors()) {
            model.addAttribute("mantenerModalPassword", true);
            model.addAttribute("passwordError", true);
            return "auth_login";
        }
        
        try {
            usuarioService.actualizarPassword(dto.getEmail(), dto.getNewPassword());
            redirect.addAttribute("passwordExito", true);
            redirect.addAttribute("mensaje", "Contraseña actualizada correctamente.");
            return "redirect:/";
            
        } catch (IllegalArgumentException e) {
            redirect.addAttribute("passwordError", true);
            redirect.addAttribute("mensaje", e.getMessage());
            return "redirect:/";
            
        } catch (Exception e) {
            redirect.addAttribute("passwordError", true);
            redirect.addAttribute("mensaje", "Error inesperado al actualizar la contraseña.");
            return "redirect:/";
        }
    }
}
