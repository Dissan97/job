package org.agnese_dissan.exceptions;

public class BookingException extends Exception {

    public BookingException() {
        super("BOOKING ISSUE");
    }

    public BookingException(String message) {
        super(message);
    }
}
