package com.miaplication.crud.export;

import com.miaplication.crud.entity.Proyecto;
import com.miaplication.crud.service.ProyectoService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Service
public class ProyectoExportService {

    private final ProyectoService proyectoService;

    public ProyectoExportService(ProyectoService proyectoService) {
        this.proyectoService = proyectoService;
    }

    // ✅ Exportar a Excel
    public void exportarExcel(HttpServletResponse response) throws IOException {
        List<Proyecto> proyectos = proyectoService.listarTodos();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Proyectos");

        // Encabezado
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("ID");
        header.createCell(1).setCellValue("Nombre");
        header.createCell(2).setCellValue("Descripción");
        header.createCell(3).setCellValue("Empleado ID");

        int rowCount = 1;
        for (Proyecto p : proyectos) {
            Row row = sheet.createRow(rowCount++);
            row.createCell(0).setCellValue(p.getId());
            row.createCell(1).setCellValue(p.getNombre());
            row.createCell(2).setCellValue(p.getDescripcion());
            row.createCell(3).setCellValue(p.getEmpleadoId());
        }

        // Configuración de la respuesta
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=proyectos.xlsx");

        workbook.write(response.getOutputStream());
        workbook.close();
    }

    // ✅ Exportar a PDF
    public void exportarPDF(HttpServletResponse response) throws IOException, DocumentException {
        List<Proyecto> proyectos = proyectoService.listarTodos();

        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();

        // Título
        com.itextpdf.text.Font tituloFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.BLUE);
        Paragraph titulo = new Paragraph("Lista de Proyectos", tituloFont);
        titulo.setAlignment(Element.ALIGN_CENTER);
        document.add(titulo);
        document.add(new Paragraph(" "));

        // Tabla
        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100);
        table.setWidths(new float[] { 1, 3, 4, 2 });

        String[] columnas = { "ID", "Nombre", "Descripción", "Empleado ID" };
        for (String columna : columnas) {
            PdfPCell cell = new PdfPCell(new Phrase(columna));
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(cell);
        }

        for (Proyecto p : proyectos) {
            table.addCell(String.valueOf(p.getId()));
            table.addCell(p.getNombre());
            table.addCell(p.getDescripcion());
            table.addCell(String.valueOf(p.getEmpleadoId()));
        }

        document.add(table);
        document.close();
    }
}
