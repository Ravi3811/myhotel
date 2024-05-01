package com.airbnb.controller;

import com.airbnb.entity.Bookings;
import com.airbnb.entity.PropertyUser;
import com.airbnb.repository.BookingsRepository;
import com.airbnb.service.BookingService;
import com.airbnb.service.PDFService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/bookings")
public class BookingController {
    private BookingService bookingService;

    public BookingController(BookingService bookingService, PDFService pdfService) {
        this.bookingService = bookingService;

    }
    @PostMapping("/createBooking")
    public ResponseEntity<?> createBooking(@RequestBody Bookings bookings, @AuthenticationPrincipal
                                           PropertyUser user) throws IOException {
        Bookings booking = bookingService.createBooking(bookings, user);
        if (booking!=null){
            return new ResponseEntity<>(booking, HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Something went wrong",HttpStatus.BAD_REQUEST);
    }
}
