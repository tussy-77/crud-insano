package com.miaplication.crud.export;

import com.miaplication.crud.entity.Empleado;
import com.miaplication.crud.service.EmpleadoService;
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
public class EmpleadoExportService {

    private final EmpleadoService empleadoService;

    public EmpleadoExportService(EmpleadoService empleadoService) {
        this.empleadoService = empleadoService;
    }

    // ✅ Exportar a Excel
    public void exportarExcel(HttpServletResponse response) throws IOException {
        List<Empleado> empleados = empleadoService.listarTodos();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Empleados");

        // Encabezado
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("ID");
        header.createCell(1).setCellValue("Nombre");
        header.createCell(2).setCellValue("Cargo");
        header.createCell(3).setCellValue("Salario");
        header.createCell(4).setCellValue("Email");

        int rowCount = 1;
        for (Empleado e : empleados) {
            Row row = sheet.createRow(rowCount++);
            row.createCell(0).setCellValue(e.getId());
            row.createCell(1).setCellValue(e.getNombre());
            row.createCell(2).setCellValue(e.getCargo());
            row.createCell(3).setCellValue(e.getSalario());
            row.createCell(4).setCellValue(e.getEmail());
        }

        // Configuración del archivo Excel
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=empleados.xlsx");

        workbook.write(response.getOutputStream());
        workbook.close();
    }

    // ✅ Exportar a PDF
    public void exportarPDF(HttpServletResponse response) throws IOException, DocumentException {
        List<Empleado> empleados = empleadoService.listarTodos();

        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();

        // Título
        com.itextpdf.text.Font tituloFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.BLUE);
        Paragraph titulo = new Paragraph("Lista de Empleados", tituloFont);
        titulo.setAlignment(Element.ALIGN_CENTER);
        document.add(titulo);
        document.add(new Paragraph(" "));

        // Tabla
        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100);
        table.setWidths(new float[] { 1, 3, 3, 2, 4 });

        String[] columnas = { "ID", "Nombre", "Cargo", "Salario", "Email" };
        for (String columna : columnas) {
            PdfPCell cell = new PdfPCell(new Phrase(columna));
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(cell);
        }

        for (Empleado e : empleados) {
            table.addCell(String.valueOf(e.getId()));
            table.addCell(e.getNombre());
            table.addCell(e.getCargo());
            table.addCell(String.valueOf(e.getSalario()));
            table.addCell(e.getEmail());
        }

        document.add(table);
        document.close();
    }
}
