package com.multisupply.sgi.reportes.services;

import com.multisupply.sgi.reportes.dtos.ReporteOrdenDTO;
import com.multisupply.sgi.reportes.dtos.ReporteProductoDTO;
import com.multisupply.sgi.reportes.dtos.ReporteUsuarioDTO;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReporteExcelService {
    
    // ---------------------- PRODUCTOS ----------------------
    public XSSFWorkbook generarExcelProductos(List<ReporteProductoDTO> lista) {
        
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet("Productos");
        CellStyle header = crearEstiloHeader(wb);
        
        String[] titulos = {
            "ID", "Nombre", "Stock", "Precio", "Categoría", "Estado",
            "Fecha de Creación", "Creado Por", "Fecha de Actualización", "Actualizado Por"
        };
        
        XSSFRow row0 = sheet.createRow(0);
        for (int i = 0; i < titulos.length; i++) {
            XSSFCell c = row0.createCell(i);
            c.setCellValue(titulos[i]);
            c.setCellStyle(header);
        }
        
        int fila = 1;
        for (ReporteProductoDTO p : lista) {
            XSSFRow r = sheet.createRow(fila++);
            
            r.createCell(0).setCellValue(safe(p.getId()));
            r.createCell(1).setCellValue(safe(p.getNombre()));
            r.createCell(2).setCellValue(safe(p.getStock()));
            r.createCell(3).setCellValue(p.getPrecio() != null ? p.getPrecio().doubleValue() : 0);
            r.createCell(4).setCellValue(safe(p.getCategoria().getDisplayName()));
            r.createCell(5).setCellValue(safe(p.getEstado().getDisplayName()));
            r.createCell(6).setCellValue(safe(p.getFechaCreacion()));
            r.createCell(7).setCellValue(safe(p.getCreadoPor() != null ? p.getCreadoPor().getEmail() : null));
            r.createCell(8).setCellValue(safe(p.getFechaActualizacion()));
            r.createCell(9).setCellValue(safe(p.getActualizadoPor() != null ? p.getActualizadoPor().getEmail() : null));
        }
        
        aplicarEstilosExcel(sheet, titulos.length, wb);
        return wb;
    }
    
    // ---------------------- ORDENES ----------------------
    public XSSFWorkbook generarExcelOrdenes(List<ReporteOrdenDTO> lista) {
        
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet("Movimientos");
        CellStyle header = crearEstiloHeader(wb);
        
        String[] titulos = {
            "ID", "Producto", "Cantidad", "Monto", "Motivo", "Tipo",
            "Fecha de Creación", "Creado Por", "Fecha de Actualización", "Actualizado Por"
        };
        
        XSSFRow row0 = sheet.createRow(0);
        for (int i = 0; i < titulos.length; i++) {
            XSSFCell c = row0.createCell(i);
            c.setCellValue(titulos[i]);
            c.setCellStyle(header);
        }
        
        int fila = 1;
        for (ReporteOrdenDTO o : lista) {
            XSSFRow r = sheet.createRow(fila++);
            
            r.createCell(0).setCellValue(safe(o.getId()));
            r.createCell(1).setCellValue(safe(o.getProductoNombre()));
            r.createCell(2).setCellValue(o.getCantidad());
            r.createCell(3).setCellValue(o.getMonto() != null ? o.getMonto().doubleValue() : 0);
            r.createCell(4).setCellValue(safe(o.getMotivo().getDisplayName()));
            r.createCell(5).setCellValue(safe(o.getTipo().getDisplayName()));
            r.createCell(6).setCellValue(safe(o.getFechaCreacion()));
            r.createCell(7).setCellValue(safe(o.getCreadoPor() != null ? o.getCreadoPor().getEmail() : null));
            r.createCell(8).setCellValue(safe(o.getFechaActualizacion()));
            r.createCell(9).setCellValue(safe(o.getActualizadoPor() != null ? o.getActualizadoPor().getEmail() : null));
        }
        
        aplicarEstilosExcel(sheet, titulos.length, wb);
        return wb;
    }
    
    // ---------------------- USUARIOS ----------------------
    public XSSFWorkbook generarExcelUsuarios(List<ReporteUsuarioDTO> lista) {
        
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet("Usuarios");
        CellStyle header = crearEstiloHeader(wb);
        
        String[] titulos = {
            "ID", "Usuario", "Correo", "Rol", "Estado",
            "DNI", "Teléfono", "Fecha de Nacimiento", "País", "Fecha de Creación"
        };
        
        XSSFRow row0 = sheet.createRow(0);
        for (int i = 0; i < titulos.length; i++) {
            XSSFCell c = row0.createCell(i);
            c.setCellValue(titulos[i]);
            c.setCellStyle(header);
        }
        
        int fila = 1;
        for (ReporteUsuarioDTO u : lista) {
            XSSFRow r = sheet.createRow(fila++);
            
            r.createCell(0).setCellValue(safe(u.getId()));
            r.createCell(1).setCellValue(safe(u.getUsername()));
            r.createCell(2).setCellValue(safe(u.getEmail()));
            r.createCell(3).setCellValue(safe(u.getRol()));
            r.createCell(4).setCellValue(safe(u.getActivo()));
            r.createCell(5).setCellValue(safe(u.getDni()));
            r.createCell(6).setCellValue(safe(u.getTelefono()));
            r.createCell(7).setCellValue(safe(u.getFechaNacimiento()));
            r.createCell(8).setCellValue(safe(u.getPais()));
            r.createCell(9).setCellValue(safe(u.getFechaCreacion()));
        }
        
        aplicarEstilosExcel(sheet, titulos.length, wb);
        return wb;
    }
    
    // ---------------------- UTILIDAD GENERAL ----------------------
    
    private String safe(String value) {
        return value == null || value.trim().isEmpty() ? "Sin dato" : value;
    }
    
    private String safe(Object value) {
        return value == null ? "Sin dato" : value.toString();
    }
    
    private CellStyle crearEstiloHeader(XSSFWorkbook wb) {
        CellStyle style = wb.createCellStyle();
        XSSFFont font = wb.createFont();
        font.setBold(true);
        font.setFontHeight(12);
        style.setFont(font);
        return style;
    }
    
    private void autosizeColumns(XSSFSheet sheet, int columnas) {
        for (int i = 0; i < columnas; i++) {
            sheet.autoSizeColumn(i);
        }
    }
    
    private void aplicarEstilosExcel(XSSFSheet sheet, int numColumnas, XSSFWorkbook wb) {
        
        String colorHeader = "1a959f";
        String colorFilaAlterna = "e8f4f6";
        String colorBorde = "D3D3D3";
        
        XSSFFont headerFont = wb.createFont();
        headerFont.setBold(true);
        headerFont.setColor(IndexedColors.WHITE.getIndex());
        headerFont.setFontHeight(11);
        
        XSSFCellStyle headerStyle = wb.createCellStyle();
        headerStyle.setFont(headerFont);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        headerStyle.setFillForegroundColor(new XSSFColor(hexToRgb(colorHeader), null));
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        
        XSSFCellStyle borderStyle = wb.createCellStyle();
        borderStyle.setBorderBottom(BorderStyle.THIN);
        borderStyle.setBorderTop(BorderStyle.THIN);
        borderStyle.setBorderLeft(BorderStyle.THIN);
        borderStyle.setBorderRight(BorderStyle.THIN);
        
        borderStyle.setBottomBorderColor(new XSSFColor(hexToRgb(colorBorde), null));
        borderStyle.setTopBorderColor(new XSSFColor(hexToRgb(colorBorde), null));
        borderStyle.setLeftBorderColor(new XSSFColor(hexToRgb(colorBorde), null));
        borderStyle.setRightBorderColor(new XSSFColor(hexToRgb(colorBorde), null));
        
        XSSFCellStyle alternateStyle = wb.createCellStyle();
        alternateStyle.cloneStyleFrom(borderStyle);
        alternateStyle.setFillForegroundColor(new XSSFColor(hexToRgb(colorFilaAlterna), null));
        alternateStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        
        XSSFCellStyle normalStyle = wb.createCellStyle();
        normalStyle.cloneStyleFrom(borderStyle);
        
        Row headerRow = sheet.getRow(0);
        for (int c = 0; c < numColumnas; c++) {
            Cell cell = headerRow.getCell(c);
            if (cell == null) continue;
            cell.setCellStyle(headerStyle);
        }
        
        int lastRow = sheet.getLastRowNum();
        
        for (int r = 1; r <= lastRow; r++) {
            Row row = sheet.getRow(r);
            if (row == null) continue;
            
            boolean usarAlterna = ((r - 1) % 2 == 0);
            
            for (int c = 0; c < numColumnas; c++) {
                Cell cell = row.getCell(c);
                if (cell == null) continue;
                
                cell.setCellStyle(usarAlterna ? alternateStyle : normalStyle);
            }
        }
        
        for (int c = 0; c < numColumnas; c++) {
            sheet.autoSizeColumn(c);
            int newWidth = Math.min(sheet.getColumnWidth(c), 50 * 256);
            sheet.setColumnWidth(c, newWidth);
        }
        
        sheet.createFreezePane(0, 1);
    }
    
    private byte[] hexToRgb(String colorStr) {
        return new byte[]{
            (byte) Integer.parseInt(colorStr.substring(0, 2), 16),
            (byte) Integer.parseInt(colorStr.substring(2, 4), 16),
            (byte) Integer.parseInt(colorStr.substring(4, 6), 16)
        };
    }
}
