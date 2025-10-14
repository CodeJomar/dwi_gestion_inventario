/*package com.multisupply.sgi.usuarios.controllers;

import com.multisupply.sgi.usuarios.entities.dtos.UsuarioDTO;
import com.multisupply.sgi.usuarios.entities.models.Rol;
import com.multisupply.sgi.usuarios.entities.models.Usuario;
import com.multisupply.sgi.usuarios.repositories.RolRepository;
import com.multisupply.sgi.usuarios.services.UsuarioService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/auth/usuarios")
@RequiredArgsConstructor
public class UsuarioController {
    
    private final UsuarioService usuarioService;
    private final RolRepository rolRepository;
    
    // Mostrar listado de usuarios activos (excepto administradores)
    @GetMapping("")
    public String listarUsuarios(Model model) {
        model.addAttribute("usuarios", usuarioService.listarUsuariosActivos());
        model.addAttribute("roles", rolRepository.findAll());
        if (!model.containsAttribute("nuevoUsuario")) {
            model.addAttribute("nuevoUsuario", new UsuarioDTO());
        }
        return "usuarios";
    }
    
    // Crear nuevo usuario desde modal
    @PostMapping("/crear")
    public String crearUsuario(@Valid @ModelAttribute("nuevoUsuario") UsuarioDTO usuarioDTO,
                               BindingResult result,
                               @RequestParam("rolId") Long rolId,
                               Model model,
                               RedirectAttributes redirect) {
        
        if (result.hasErrors()) {
            model.addAttribute("roles", rolRepository.findAll());
            model.addAttribute("mostrarError", true);
            model.addAttribute("abrirModalNuevoUsuario", true);
            return "usuarios";
        }
        
        try {
            Rol rol = rolRepository.findById(rolId)
                .orElseThrow(() -> new IllegalArgumentException("Rol no encontrado."));
            
            usuarioService.crearUsuario(usuarioDTO, rol.getNombre());
            
            redirect.addFlashAttribute("exito", true);
            redirect.addFlashAttribute("mensaje", "Usuario creado exitosamente.");
            return "redirect:/auth/usuarios";
            
        } catch (IllegalArgumentException e) {
            redirect.addFlashAttribute("error", true);
            redirect.addFlashAttribute("mensaje", e.getMessage());
            redirect.addFlashAttribute("abrirModalNuevoUsuario", true);
            return "redirect:/auth/usuarios";
        } catch (Exception e) {
            redirect.addFlashAttribute("error", true);
            redirect.addFlashAttribute("mensaje", "Error al crear usuario: " + e.getMessage());
            redirect.addFlashAttribute("abrirModalNuevoUsuario", true);
            return "redirect:/auth/usuarios";
        }
    }
    
    // Ver detalles (en modal)
    @GetMapping("/detalle/{id}")
    public String verDetalleUsuario(@PathVariable String id, Model model, RedirectAttributes redirect) {
        return usuarioService.obtenerUsuario(id)
            .map(usuarioDTO -> {
                model.addAttribute("usuario", usuarioDTO);
                model.addAttribute("abrirModalDetalle", true);
                return "usuarios";
            })
            .orElseGet(() -> {
                redirect.addFlashAttribute("error", true);
                redirect.addFlashAttribute("mensaje", "Usuario no encontrado.");
                return "redirect:/auth/usuarios";
            });
    }
    
    // 🔹 Desactivar usuario
    @GetMapping("/desactivar/{id}")
    public String desactivarUsuario(@PathVariable String id, RedirectAttributes redirect) {
        if (usuarioService.desactivarUsuario(id)) {
            redirect.addFlashAttribute("mensaje", "Usuario desactivado correctamente.");
            redirect.addFlashAttribute("exito", true);
        } else {
            redirect.addFlashAttribute("mensaje", "No se pudo desactivar el usuario.");
            redirect.addFlashAttribute("error", true);
        }
        return "redirect:/auth/usuarios";
    }
    
    // Exportar usuarios a Excel (incluye todos, incluso administradores)
    @GetMapping("/exportar")
    public void exportarUsuarios(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=usuarios.xlsx");
        
        List<Usuario> usuarios = usuarioService.obtenerTodosUsuarios();
        
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Usuarios");
        
        CellStyle headerStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        font.setColor(IndexedColors.WHITE.getIndex());
        headerStyle.setFont(font);
        headerStyle.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        
        String[] columnas = {"Username", "Email", "Rol", "Activo", "Fecha de Creación"};
        
        Row header = sheet.createRow(0);
        for (int i = 0; i < columnas.length; i++) {
            Cell cell = header.createCell(i);
            cell.setCellValue(columnas[i]);
            cell.setCellStyle(headerStyle);
        }
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        int rowIndex = 1;
        
        for (Usuario u : usuarios) {
            Row row = sheet.createRow(rowIndex++);
            row.createCell(0).setCellValue(u.getUsername());
            row.createCell(1).setCellValue(u.getEmail());
            row.createCell(2).setCellValue(u.getRol().getNombre().name());
            row.createCell(3).setCellValue(u.isActivo() ? "Sí" : "No");
            row.createCell(4).setCellValue(
                u.getFechaCreacion() != null ? u.getFechaCreacion().format(formatter) : ""
            );
        }
        
        for (int i = 0; i < columnas.length; i++) {
            sheet.autoSizeColumn(i);
        }
        
        workbook.write(response.getOutputStream());
        workbook.close();
    }
    
}
*/