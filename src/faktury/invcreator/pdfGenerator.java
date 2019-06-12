package faktury.invcreator;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;


public class pdfGenerator {
    private static int tableCounter;
    private static ArrayList<String> productsText = new ArrayList<>();
    private static ArrayList<String> priceList = new ArrayList<>();
    private static String FILE = "./faktura.pdf";
    private static Font headerFont = FontFactory.getFont(FontFactory.HELVETICA, 32,
            Font.BOLD);
    private static Font catFont = FontFactory.getFont(FontFactory.HELVETICA, 18,
            Font.BOLD);
    private static Font mainFont = FontFactory.getFont(FontFactory.HELVETICA, 10,
            Font.NORMAL);
    private static Font subFont = FontFactory.getFont(FontFactory.HELVETICA, 16,
            Font.BOLD);
    private static Font smallBold = FontFactory.getFont(FontFactory.HELVETICA, 12,
            Font.BOLD);

    public static void generatePdf(java.util.List<String> fieldTexts, ArrayList<String> productText, int prodCounter, ArrayList<String> priceValues) {
        tableCounter = prodCounter;
        productsText = productText;
        priceList = priceValues;
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(FILE));
            document.open();
            addContent(document, fieldTexts);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void addContent(Document document, java.util.List<String> fieldTexts) throws DocumentException {
//
        Paragraph title = new Paragraph(fieldTexts.get(0), headerFont);
        title.setAlignment(Element.ALIGN_RIGHT);
        document.add(title);

        addEmptyLine(document, 1);

        LocalDate localDate = LocalDate.now();
        String date = DateTimeFormatter.ofPattern("dd-MM-yyyy").format(localDate);
        Paragraph datetext = new Paragraph(date, smallBold);
        datetext.setAlignment(Element.ALIGN_RIGHT);
        document.add(datetext);

        document.add(new Paragraph("Sprzedawca", catFont));
        document.add(new Paragraph("Nazwa: " + fieldTexts.get(1), mainFont));
        document.add(new Paragraph("Email: " + fieldTexts.get(2), mainFont));
        document.add(new Paragraph("Adres: " + fieldTexts.get(3), mainFont));
        document.add(new Paragraph("NIP: " + fieldTexts.get(4), mainFont));
        document.add(new Paragraph("Telefon: " + fieldTexts.get(5), mainFont));

        addEmptyLine(document, 1);
        document.add(new Paragraph("Nabywca", catFont));
        document.add(new Paragraph("Nazwa: " + fieldTexts.get(6), mainFont));
        document.add(new Paragraph("Email: " + fieldTexts.get(7), mainFont));
        document.add(new Paragraph("Adres: " + fieldTexts.get(8), mainFont));
        document.add(new Paragraph("NIP: " + fieldTexts.get(9), mainFont));
        document.add(new Paragraph("Telefon: " + fieldTexts.get(10), mainFont));

        addEmptyLine(document, 2);

        PdfPTable table = new PdfPTable(5);

        PdfPCell c1 = new PdfPCell(new Paragraph("Nazwa", smallBold));
        c1.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(c1);

        c1 = new PdfPCell(new Paragraph("Opis", smallBold));
        c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(c1);

        c1 = new PdfPCell(new Paragraph("Cena", smallBold));
        c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(c1);

        c1 = new PdfPCell(new Paragraph("Ilosc", smallBold));
        c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(c1);

        c1 = new PdfPCell(new Paragraph("Suma", smallBold));
        c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(c1);

        table.completeRow();

        for (int i = 0; i <= tableCounter; i++) {
            c1 = new PdfPCell(new Paragraph(productsText.get(i*4)));
            table.addCell(c1);
            c1 = new PdfPCell(new Paragraph(productsText.get(3+i*4)));
            table.addCell(c1);
            c1 = new PdfPCell(new Paragraph(productsText.get(1+i*4)+" zl"));
            c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(c1);
            c1 = new PdfPCell(new Paragraph(productsText.get(2+i*4)));
            c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(c1);
            c1 = new PdfPCell(new Paragraph(priceList.get(i)+"l"));
            c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(c1);
        }

        document.add(table);

    }


    private static void addEmptyLine(Document document, int number) throws DocumentException {
        for (int i = 0; i < number; i++) {
            document.add(new Paragraph(" "));
        }
    }

}