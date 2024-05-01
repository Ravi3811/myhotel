package com.airbnb.service;

import com.airbnb.payload.BookingDto;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;

@Service
public class PDFService {
    private static final String PDF_DIRECTORY="/path/to/your/pdf/directory/";

    public boolean generatePdf(String filename, BookingDto dto){
        try{
            Document document=new Document();
            PdfWriter.getInstance(document,new FileOutputStream(filename));

            document.open();
            Font font= FontFactory.getFont(FontFactory.COURIER,16, BaseColor.BLACK);
            Chunk bookingConfirmation=new Chunk(" Booking Confirmation",font);
            Chunk guestName=new Chunk(" Guest Name: "+dto.getGuestName(),font);
            Chunk bookingId=new Chunk(" Booking Id: "+dto.getBookingId(),font);
            Chunk propertyName=new Chunk(" Property Name: "+dto.getPropertyName(),font);
            Chunk price=new Chunk(" Price per night: "+dto.getPrice(),font);
            Chunk totalPrice=new Chunk(" Total Price: "+dto.getTotalPrice(),font);
            document.add(new Paragraph(bookingConfirmation));
            document.add(new Paragraph(bookingId));
            document.add(new Paragraph(guestName));
            document.add(new Paragraph(propertyName));
            document.add(new Paragraph(price));
            document.add(new Paragraph(totalPrice));
            document.close();
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
