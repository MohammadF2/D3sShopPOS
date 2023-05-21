package com.example.d3sshoppos.helpers;

import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;

public class TableGenerator {

    public static void generatePDFFile(String[] productNames) {

        // Create document
        Document document = new Document();
        try {
            // Create PDF writer
            PdfWriter.getInstance(document, new FileOutputStream("table.pdf"));

            // Open document
            document.open();

            // Create table
            PdfPTable table = new PdfPTable(3);
            table.setWidthPercentage(100);

            // Define column widths
            float[] columnWidths = {2, 2, 2};
            table.setWidths(columnWidths);

            // Set table header properties
            Font headerFont = FontFactory.getFont(FontFactory.HELVETICA, 16);
            PdfPCell headerCell = new PdfPCell();
            headerCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            headerCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            headerCell.setPadding(5);
            headerCell.setPhrase(new Phrase("Produkt Name", headerFont));
            table.addCell(headerCell);
            headerCell.setPhrase(new Phrase("Menge", headerFont));
            table.addCell(headerCell);
            headerCell.setPhrase(new Phrase("Fehlt", headerFont));
            table.addCell(headerCell);

            // Set table data properties
            Font dataFont = FontFactory.getFont(FontFactory.HELVETICA, 14);
            PdfPCell dataCell = new PdfPCell();
            dataCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            dataCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            dataCell.setPadding(5);
            dataCell.setPhrase(new Phrase("", dataFont)); // Empty cell for the second column

            // Image for checkbox
            Image checkbox = Image.getInstance("C:\\Users\\frajm\\IdeaProjects\\D3sShopPOS\\src\\main\\java\\com\\example\\d3sshoppos\\helpers\\checkbox.png");
            checkbox.scaleAbsolute(10, 10);  // You can adjust the size of the image as needed

            for (String productName : productNames) {
                table.addCell(new Phrase(productName, dataFont));
                table.addCell(dataCell);
                PdfPCell checkboxCell = new PdfPCell(checkbox);
                checkboxCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                checkboxCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                table.addCell(checkboxCell);  // Add checkbox image to the cell
            }

            // Add table to document
            document.add(table);

            // Close document
            document.close();
            Desktop.getDesktop().open(new File("table.pdf"));
            System.out.println("PDF created successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
