package org.example.myapp.Filters;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.io.FileOutputStream;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class PdfExporter {

    public static void saveMoviesToPdf(List<Map<String, String>> movieDetailsList, String filepath) {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(filepath));
            document.open();

            Paragraph title = new Paragraph("List of Movies", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.BLACK));
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph("\n"));

            PdfPTable table = new PdfPTable(7);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{3, 2, 4, 6, 3, 3, 5});
//            addTableHeader(table, "Plakat", "Czas trwania", "Tytuł", "Opis", "Gatunki", "Reżyser", "Aktorzy");
            addTableHeader(table, "Poster", "Runtime", "Title", "Description", "Genres", "Director", "Actors");
            for (Map<String, String> movieDetails : movieDetailsList) {
                table.addCell(getImageCell(movieDetails.getOrDefault("Poster", null)));
                table.addCell(movieDetails.getOrDefault("Runtime", "Brak danych"));
                table.addCell(movieDetails.getOrDefault("Title", "Brak danych"));
                table.addCell(movieDetails.getOrDefault("Overview", "Brak danych"));
                table.addCell(movieDetails.getOrDefault("Genres", "Brak danych"));
                table.addCell(movieDetails.getOrDefault("Director", "Brak danych"));
                table.addCell(movieDetails.getOrDefault("Actors", "Brak danych"));
            }

            document.add(table);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            document.close();
        }
    }

    private static void addTableHeader(PdfPTable table, String... headers) {
        for (String header : headers) {
            PdfPCell headerCell = new PdfPCell();
            headerCell.setPhrase(new Phrase(header, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.WHITE)));
            headerCell.setBackgroundColor(BaseColor.DARK_GRAY);
            headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            headerCell.setPadding(5);
            table.addCell(headerCell);
        }
    }

    private static PdfPCell getImageCell(String imageUrl) {
        if (imageUrl == null || imageUrl.isEmpty()) {
            PdfPCell cell = new PdfPCell(new Phrase("Brak plakatu"));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            return cell;
        }
        try {
            Image img = Image.getInstance(new URL(imageUrl));
            img.scaleToFit(50, 75);
            PdfPCell cell = new PdfPCell(img, true);
            cell.setPadding(5);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            return cell;
        } catch (Exception e) {
            PdfPCell cell = new PdfPCell(new Phrase("Błąd plakatu"));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            return cell;
        }
    }
}
