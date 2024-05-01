package com.airbnb.payload;

public class BookingDto {
    private Long bookingId;
    private String guestName;
    private String propertyName;
    private int price;
    private int totalPrice;
    public void setPropertyName(String propertyName){
        this.propertyName=propertyName;
    }
    public String getPropertyName(){
        return propertyName;
    }

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }


}
