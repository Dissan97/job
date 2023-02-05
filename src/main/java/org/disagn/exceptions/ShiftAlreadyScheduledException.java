package org.disagn.exceptions;

public class ShiftAlreadyScheduledException extends Exception{
    public ShiftAlreadyScheduledException() {
        super("SHIFT ALREADY SCHEDULED");
    }

    public ShiftAlreadyScheduledException(String shiftNotAccepted) {
        super(shiftNotAccepted);
    }
}
