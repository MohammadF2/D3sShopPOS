package com.example.d3sshoppos;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;

public class tester {

    public static void main(String[] args) {
        // Sample product names in Arabic
        String[] productNames = {"منتج1", "منتج2", "منتج3", "منتج4", "منتج5", "منتج6"};

        // Create document
        Document document = new Document();
        try {
            // Create PDF writer
            PdfWriter.getInstance(document, new FileOutputStream("table.pdf"));

            // Open document
            document.open();

            // Add image on top
            Image logoImage = Image.getInstance("C:\\Users\\frajm\\IdeaProjects\\D3sShopPOS\\src\\main\\resources\\Untitled.png");
            logoImage.scaleAbsolute(100, 100);  // Adjust the size of the image as needed
            logoImage.setAlignment(Element.ALIGN_CENTER);
            document.add(logoImage);

            // Add some space before the table
            document.add(new Paragraph(" "));

            // Create table
            PdfPTable table = new PdfPTable(3);
            table.setWidthPercentage(100);

            // Define column widths
            float[] columnWidths = {2, 2, 2};
            table.setWidths(columnWidths);

            // Set table header properties
            Font headerFont = FontFactory.getFont("C:\\Users\\frajm\\IdeaProjects\\D3sShopPOS\\src\\main\\resources\\NotoSansArabic-VariableFont_wdth,wght.ttf", BaseFont.IDENTITY_H, true, 12);
            PdfPCell headerCell = new PdfPCell();
            headerCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            headerCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            headerCell.setPadding(5);
            headerCell.setPhrase(new Phrase("اسم المنتج", headerFont)); // Arabic text for the header
            table.addCell(headerCell);
            headerCell.setPhrase(new Phrase("XXX", headerFont));
            table.addCell(headerCell);
            headerCell.setPhrase(new Phrase("المطلوب", headerFont)); // Arabic text for the header
            table.addCell(headerCell);

            // Set table data properties
            Font dataFont = FontFactory.getFont("C:\\Users\\frajm\\IdeaProjects\\D3sShopPOS\\src\\main\\resources\\NotoSansArabic-VariableFont_wdth,wght.ttf", BaseFont.IDENTITY_H, true, 12);
            PdfPCell dataCell = new PdfPCell();
            dataCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            dataCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            dataCell.setPadding(5);
            dataCell.setPhrase(new Phrase("", dataFont)); // Empty cell for the second column

            // Image for checkbox
            Image checkbox = Image.getInstance("C:\\Users\\frajm\\IdeaProjects\\D3sShopPOS\\src\\main\\resources\\checkbox.png");
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

            // Add some space after the table
            document.add(new Paragraph(" "));

            // Add image at the bottom

            // Close document
            document.close();

            System.out.println("PDF created successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




}