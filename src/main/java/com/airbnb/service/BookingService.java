package com.airbnb.service;

import com.airbnb.entity.Bookings;
import com.airbnb.entity.Property;
import com.airbnb.entity.PropertyUser;
import com.airbnb.payload.BookingDto;
import com.airbnb.repository.BookingsRepository;
import com.airbnb.repository.PropertyRepository;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Optional;

@Service
public class BookingService {
    private BookingsRepository bookingsRepository;
    private PropertyRepository propertyRepository;
    private PDFService pdfService;
    private BucketService bucketService;
    private SMSService smsService;

    public BookingService(BookingsRepository bookingsRepository, PropertyRepository propertyRepository, PDFService pdfService, BucketService bucketService, SMSService smsService) {
        this.bookingsRepository = bookingsRepository;
        this.propertyRepository = propertyRepository;
        this.pdfService = pdfService;
        this.bucketService = bucketService;
        this.smsService = smsService;
    }

    public Bookings createBooking(Bookings bookings, PropertyUser user) throws IOException {
        bookings.setPropertyUser(user);
        Long id = bookings.getProperty().getId();
        Optional<Property> byId =propertyRepository.findById(id);
        if(byId.isPresent()) {
            Property property = byId.get();
            Integer nightlyPrice = property.getNightlyPrice();
            int totalPrice = nightlyPrice * bookings.getTotalNights();
           // totalPrice=totalPrice+(18*totalPrice)/100;
            bookings.setProperty(property);
            bookings.setTotalPrice(totalPrice);
            Bookings saved = bookingsRepository.save(bookings);
            BookingDto dto=new BookingDto();
            dto.setBookingId(saved.getId());
            dto.setGuestName(saved.getGuestName());
            dto.setPrice(property.getNightlyPrice());
            dto.setTotalPrice(saved.getTotalPrice());
            dto.setPropertyName(property.getPropertyName());
            boolean b = pdfService.generatePdf("C://feb//" + "booking-confirmation-id" + saved.getId() + ".pdf", dto);
            if(b){
                MultipartFile file=convert("C://feb//" + "booking-confirmation-id" + saved.getId() + ".pdf") ;
                String uploadUrl = bucketService.uploadFile(file, "airbnbproj");
                smsService.sendSMS("+917368044244","Your booking is confirmed. Click for more information:"+uploadUrl);
            }
            else{
                return null;
            }
            return saved;
        }
        return null;
    }

    public static MultipartFile convert(String filePath) throws IOException{
        File file=new File(filePath);
        byte[] fileContent= Files.readAllBytes(file.toPath());
        ByteArrayResource resource = new ByteArrayResource(fileContent);
        MultipartFile multipartFile=new MultipartFile() {
            @Override
            public String getName() {
                return file.getName();
            }

            @Override
            public String getOriginalFilename() {
                return file.getName();
            }

            @Override
            public String getContentType() {
                return null;
            }

            @Override
            public boolean isEmpty() {
                return fileContent.length==0;
            }

            @Override
            public long getSize() {
                return fileContent.length;
            }

            @Override
            public byte[] getBytes() throws IOException {
                return fileContent;
            }

            @Override
            public InputStream getInputStream() throws IOException {
                return resource.getInputStream();
            }

            @Override
            public void transferTo(File dest) throws IOException, IllegalStateException {
                Files.write(dest.toPath(),fileContent);
            }
        };
        return multipartFile;
    }
}
