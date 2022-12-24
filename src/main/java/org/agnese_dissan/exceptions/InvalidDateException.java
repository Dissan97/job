package org.agnese_dissan.exceptions;

public class InvalidDateException extends BookingException{

    public InvalidDateException() {
        super();
    }

    public InvalidDateException(String message) {
        super(message);
    }
}
