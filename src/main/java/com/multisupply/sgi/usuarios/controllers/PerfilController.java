package com.multisupply.sgi.usuarios.controllers;

import com.multisupply.sgi.usuarios.entities.dtos.PerfilDTO;
import com.multisupply.sgi.usuarios.entities.enums.Pais;
import com.multisupply.sgi.usuarios.entities.models.Usuario;
import com.multisupply.sgi.usuarios.services.PerfilService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/auth/perfil")
@RequiredArgsConstructor
public class PerfilController {
    
    private final PerfilService perfilService;
    
    @GetMapping("")
    public String mostrarPerfil(Model model, Authentication auth) {
        
        Usuario usuario = (Usuario) auth.getPrincipal();
        
        PerfilDTO perfil = perfilService.obtenerPerfilPorUsuarioId(usuario.getId())
            .orElseGet(() -> {
                PerfilDTO nuevo = new PerfilDTO();
                nuevo.setUsuarioId(usuario.getId());
                nuevo.setUsername(usuario.getUsername());
                nuevo.setEmail(usuario.getEmail());
                return nuevo;
            });
        
        model.addAttribute("perfil", perfil);
        model.addAttribute("paises", Pais.values());
        
        return "perfil";
    }
    
    @PostMapping("/guardar")
    public String guardarPerfil(
        @Valid @ModelAttribute("perfil") PerfilDTO perfilDTO,
        BindingResult result,
        Model model,
        Authentication auth,
        RedirectAttributes redirect
    ) {
        
        if (result.hasErrors()) {
            
            Usuario usuario = (Usuario) auth.getPrincipal();
            perfilDTO.setUsername(usuario.getUsername());
            perfilDTO.setEmail(usuario.getEmail());
            
            model.addAttribute("paises", Pais.values());
            model.addAttribute("error", true);
            model.addAttribute("mensaje", "Corrige los errores del formulario.");
            
            return "perfil";
        }
        
        perfilService.guardarPerfil(perfilDTO);
        
        redirect.addFlashAttribute("exito", true);
        redirect.addFlashAttribute("mensaje", "Perfil actualizado correctamente.");
        
        return "redirect:/auth/perfil";
    }
    
}
