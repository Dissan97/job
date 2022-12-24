package org.agnese_dissan.exceptions;

public class RoomNotAvailableException extends BookingException{
    public RoomNotAvailableException() {
        super();
    }

    public RoomNotAvailableException(String message) {
        super(message);
    }
}
