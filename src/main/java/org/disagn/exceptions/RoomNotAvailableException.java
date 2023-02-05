package org.disagn.exceptions;

public class RoomNotAvailableException extends Exception {
    public RoomNotAvailableException() {
        super();
    }

    public RoomNotAvailableException(String message) {
        super(message);
    }
}
