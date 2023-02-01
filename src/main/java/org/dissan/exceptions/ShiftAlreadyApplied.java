package org.dissan.exceptions;

public class ShiftAlreadyApplied extends Exception{
    public ShiftAlreadyApplied() {
        super("Shift already taken");
    }

}
