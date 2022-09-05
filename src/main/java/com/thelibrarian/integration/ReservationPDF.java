package com.thelibrarian.integration;

import com.lowagie.text.DocumentException;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.thelibrarian.data.entity.ReservationEntity;
import com.lowagie.text.Font;
import javax.servlet.http.HttpServletResponse;
import java.awt.Color;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.logging.Logger;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;


public class ReservationPDF {


    private List<ReservationEntity> reserves;


    public ReservationPDF(List<ReservationEntity> reserves) {
        this.reserves = reserves;
    }

    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.BLUE);
        cell.setPadding(5);
        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.WHITE);
        cell.setPhrase(new Phrase("User Name", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Title", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Image", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Reserved", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("ReservedDate", font));
        table.addCell(cell);
    }

    private void writeTableData(PdfPTable table) throws IOException {

        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
        for (ReservationEntity reserve : reserves) {

            String image = reserve.getBook().getImageLinks();
            Image img =  Image.getInstance(new URL(image));
            if(reserve.getIs_reservado()==true){

                table.addCell(String.valueOf(reserve.getUsuario().getNombre()));
                table.addCell(String.valueOf(reserve.getBook().getTitle()));
                table.addCell(img);
                table.addCell("reserved");
                table.addCell(currentDateTime);
            }

            else {
                table.addCell(String.valueOf(reserve.getUsuario().getNombre()));
                table.addCell(String.valueOf(reserve.getBook().getTitle()));
                table.addCell(img);
                table.addCell("Not Reserved");
                table.addCell(currentDateTime);
            }


        }
    }


    public void exportUser(HttpServletResponse response) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.BLUE);
        Paragraph p = new Paragraph("List of Reserves", font);
        p.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(p);
        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] {1.5f, 1.5f, 1.5f, 1.5f,1.5f});
        table.setSpacingBefore(10);
        writeTableHeader(table);
        writeTableData(table);
        document.add(table);
        document.close();
    }

}