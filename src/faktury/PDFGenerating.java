package faktury;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class PDFGenerating {
    public void generate() throws FileNotFoundException, DocumentException {
        String dest = String.format("fakturyPDF/Faktura.pdf", 1);

        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(dest));

        document.add(new Paragraph("Henlo"));
    }
}
